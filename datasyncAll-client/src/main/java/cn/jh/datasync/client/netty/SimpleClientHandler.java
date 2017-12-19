package cn.jh.datasync.client.netty;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.jh.datasync.client.LinkClientScheduled;
import cn.jh.datasync.client.listener.QueueListener;
import cn.jh.datasync.client.utils.ResponseMsgMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;  
 
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleClientHandler.class);

	//表名列表
	private  List<String> tableNameArray;
	
	//定时任务
	ScheduledExecutorService scheduledExecutor;
	//连接是否活动的
	boolean isActive=false;
	
	Connection connection;
	
	public SimpleClientHandler() {
	}
	public SimpleClientHandler(List<String> tableNameArray) {
		this.tableNameArray = tableNameArray;
	}
	//读取信息
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { 
    	byte[] byteData=(byte[])msg;
    	String content= new String(byteData,"UTF-8");
		logger.info("收到服务器的信息:"+content);
		Map<String,Object> map = JSONObject.parseObject(content,Map.class);
		if(map.get("msgsnMapList")!=null) {
			ResponseMsgMap.msgsnMap.put((String)map.get("tableName"), (List<Long>)map.get("msgsnMapList"));
		}else{
			ResponseMsgMap.map.put((String)map.get("msgsn"), (Boolean)map.get("result"));
		}
		ReferenceCountUtil.release(msg);
    }  
    //异常处理
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        // 当出现异常就关闭连接  
    	logger.error("连接出现异常");
    	cause.printStackTrace();
        //关闭所有定时任务
        LinkClientScheduled.shutDown();
        isActive=false;
    }  
    // 连接成功后  
    @Override    
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	logger.info("连接成功");
    	isActive=true;
		//开启定时任务
		LinkClientScheduled.scheduleWithFixedDelay(tableNameArray,ctx);
		ctx.writeAndFlush("@客户端:"+tableNameArray.toString());
    }
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //关闭所有定时任务
		if(isActive) {
			LinkClientScheduled.shutDown();
			isActive=false;
		}
	}
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }
}