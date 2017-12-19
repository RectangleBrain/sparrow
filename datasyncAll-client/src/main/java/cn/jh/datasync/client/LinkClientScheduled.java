package cn.jh.datasync.client;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jh.datasync.client.utils.Configuration;
import io.netty.channel.ChannelHandlerContext;

/**
 *定时连接服务任务
 */
public class LinkClientScheduled {  
	private static final Logger logger = LoggerFactory.getLogger(LinkClientScheduled.class);
	
	private static ScheduledExecutorService schedule;
	
	public static void scheduleWithFixedDelay(List<String> tableNameArray,ChannelHandlerContext ctx) {
		//线程池默认为5
		schedule = Executors.newScheduledThreadPool(5);
		for (String tableName : tableNameArray) {
			//延迟0秒，以后每隔分钟执行一次 .   
			if(StringUtils.isNotBlank(tableName)) {
				schedule.scheduleWithFixedDelay(new DataTask(tableName,ctx), 0, Configuration.data_sync_seconds, TimeUnit.SECONDS);
				logger.info(tableName+"定时任务开启");
			} 
		}
	}  
	
	public static void shutDown() throws InterruptedException{
		if(schedule!=null){
			while(!schedule.awaitTermination(10,TimeUnit.MILLISECONDS)) {
				schedule.shutdownNow();
			}
			logger.info("定时任务已经关闭");
		}else {
			logger.info("定时任务没有开启");
		}
	}
}