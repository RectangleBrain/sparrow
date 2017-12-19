package cn.jh.datasync.client;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import cn.jh.datasync.client.netty.SimpleClient;
import cn.jh.datasync.client.utils.Configuration;
import cn.jh.datasync.client.utils.SpringContextUtils;
@SpringBootApplication
public class DSClientApplication {

	private static final Logger logger = LoggerFactory.getLogger(DSClientApplication.class);
	
	//配置文件注入,数据同步时间间隔
	@Value("${data_sync_seconds}")
	public void setData_sync_minutes(int data_sync_seconds) {
		Configuration.data_sync_seconds = data_sync_seconds;
	}
	//配置文件注入,数据同步服务器地址
	@Value("${server_host}")
	public  void setServer_host(String server_host) {
		Configuration.server_host = server_host;
	}
	//配置文件注入,数据服务器端口
	@Value("${server_port}")
	public  void setServer_port(int server_port) {
		Configuration.server_port = server_port;
	}
/*	//配置文件注入需要同步的表名
	@Value("${tableName}")
	public  void setTableNameList(String tableName) {
		Configuration.tableName = tableName;
	}*/
	
	//配置文件注入,匹配的表名
	@Value("${tableName}")
	public  void setTableName(String tableName) {
		if(StringUtils.isNoneBlank(tableName)) {
			//存储客户端服务端表名对
			List<String> tableNameArray=new ArrayList<String>();
			//截取字符串
			String[] split = tableName.split(",");
			if(split !=null && split.length>0) {
				for (String string : split) {
					if(StringUtils.isNoneBlank(string)) {
						tableNameArray.add(string);
					}
				}
			}
			Configuration.tableNameArray = tableNameArray;
		}
	}
	//配置文件注入,一次发送的数据量
	@Value("${datas_num}")
	public  void setDatas_num(int datas_num) {
		Configuration.datas_num = datas_num;
	}
	
	//配置文件注入,同步间隔时间内的
	@Value("${data_sync_num}")
	public  void setData_sync_num(int data_sync_num) {
		Configuration.data_sync_num = data_sync_num;
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = SpringApplication.run(DSClientApplication.class, args);
		SpringContextUtils.setApplicationContext(applicationContext);
        SimpleClient client=new SimpleClient();  
        try {
        	//服务器重连数
        	int reconnectNum=1;
    		while(true) {
//    			if(reconnectNum==6) {
//    				break;
//    			}
    			try {
					logger.info("连接服务器"+Configuration.server_host+":"+Configuration.server_port);
					client.connect(Configuration.server_host, Configuration.server_port,Configuration.tableNameArray);
				} catch (Exception e) {
					logger.error(e.getMessage());
					logger.info("尝试"+reconnectNum+"重连服务器,等待5s");
				}
    			reconnectNum++;
    			Thread.sleep(5000);
    		}
        } catch (Exception e) {
			logger.error(e.getMessage());
			logger.info("服务器断开,重启客户端");
		}
	}
	
}
