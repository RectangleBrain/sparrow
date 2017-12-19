package cn.jh.datasync.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.jh.datasync.client.model.ColumnInfo;
import cn.jh.datasync.client.service.IDataSyncClientService;
import cn.jh.datasync.client.utils.ResponseMsgMap;
import cn.jh.datasync.client.utils.SpringContextUtils;
import io.netty.channel.ChannelHandlerContext;

public class DataTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(DataTask.class);
	
	private String tableName;
	private ChannelHandlerContext ctx;
	//表的字段信息
	private List<ColumnInfo> columnInfos = new ArrayList<>();
	//数据列表
	private List<Map<String,Object>> dataList = new ArrayList<>();
	//
	private List<String> tempRowidList = new ArrayList<String>();
	// spring获得的数据同步服务
	private IDataSyncClientService dataSyncClientService = (IDataSyncClientService) SpringContextUtils.getContext()
			.getBean("dataSyncClientServiceImpl");
//	private DruidDataSource dataSource=(DruidDataSource) SpringContextUtils.getContext().getBean("dataSource");	
	// 新增数据大小
	private int newNum = 0;
	//发送数据计数
	private int sendDataCount=0;
	// 计数
	private int count = 1;
	
	public DataTask() {
	}

	public DataTask(String tableName, ChannelHandlerContext ctx) {
		this.tableName = tableName;
		this.ctx = ctx;
	}
	@Override
	public void run() {
		// 判断tableName是否存在
		try {
			columnInfos = dataSyncClientService.getColumnInfo(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(columnInfos !=null && columnInfos.size()>0){
				sendData();
		}else {
			logger.info(tableName+"不存在");
		}
		logger.info(tableName+"任务结束");
	}
	
	/**
	 * 发送数据
	 * @throws InterruptedException 
	 */
	private void sendData(){
		logger.info("获取"+tableName+"数据");
		// 新增数据rowid列表
		try {
			dataList = dataSyncClientService.getData(tableName,columnInfos);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		newNum = dataList.size();
		logger.info(tableName + "数据量" + newNum);
		long timeMillis = System.currentTimeMillis();
	/*	int temp = newNum;
		for (int i = 0; i < temp; ) {
			List<Map<String,Object>> arrayList = new ArrayList<>();
			int k=Configuration.datas_num;
			if(newNum<k) {
				k=newNum;
			}
			for(int j=1;j<=k;j++){
				arrayList.add(dataList.get(i));
				i++;
				newNum--;
			}*/
		Map<String,Object> map = new HashMap();
		map.put("tableName", tableName);
		map.put("dataList",dataList);
		map.put("columnInfos", columnInfos);
		String msg = JSON.toJSONString(map);
			// 发送
		ctx.writeAndFlush(msg);
		msg=null;
		map=null;
		dataList.clear();
		logger.info(tableName+"总发送:"+newNum+"条记录");
//		}
		//等待服务端消息
		Boolean value=null;
		while(true){
			value=ResponseMsgMap.map.get("msgsn");
			if(value !=null) {
				ResponseMsgMap.map.remove("msgsn");
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		double time=(System.currentTimeMillis()-timeMillis)/1000;
		logger.info(tableName+"同步完成,耗时:"+time+"s");
	}
}
