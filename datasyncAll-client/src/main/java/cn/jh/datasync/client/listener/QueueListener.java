package cn.jh.datasync.client.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.jh.datasync.client.utils.ResponseMsgMap;
import io.netty.channel.ChannelHandlerContext;
/**
 * 队列监听
 * @author wanlongfei
 *
 */
public class QueueListener implements Runnable {
	
	public static LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<String>();
	
	private static final Logger logger = LoggerFactory.getLogger(QueueListener.class);
	
	int count=0;
	
	ChannelHandlerContext ctx;

	
	public QueueListener() {
	}
	public QueueListener(ChannelHandlerContext ctx, LinkedBlockingDeque<String> queue) {
		this.ctx=ctx;
		this.queue=queue;
	}

	@Override
	public void run() {
		while(true){
			String content;
			try {
				content = queue.take();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}
