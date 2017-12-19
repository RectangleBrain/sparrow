package cn.jh.datasync.client.netty;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;  
/**
 * 数据同步简单客户端 
 * @author wanlongfei
 *
 */
public class SimpleClient {  
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleClient.class);
	
    public void connect(String host, int port,List<String> tableNameList) throws Exception {  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(workerGroup);  
            b.channel(NioSocketChannel.class);  
            b.option(ChannelOption.SO_KEEPALIVE, true)
            	.option(ChannelOption.TCP_NODELAY, true);
            b.handler(new ChannelInitializer<SocketChannel>() {  
                @Override  
                public void initChannel(SocketChannel ch) throws Exception {
                	//配置编解码器,处理器
                	ch.pipeline().addLast(new ByteDecoder());  
                	ch.pipeline().addLast(new ByteEncoder());  
                    ch.pipeline().addLast(new SimpleClientHandler(tableNameList));  
                }  
            });  
//            //连接服务器
            ChannelFuture f = b.connect(host, port).sync();  
//            //等待直到连接关闭  
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            logger.error("连接已断开");
        }  
    }  
      
}  