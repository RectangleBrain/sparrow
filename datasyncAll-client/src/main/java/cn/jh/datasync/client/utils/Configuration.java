package cn.jh.datasync.client.utils;

import java.util.List;

/**
 * 配置文件
 * @author wanlongfei
 *
 */
public class Configuration {
	//数据同步间隔时间
	public static int data_sync_seconds;
	//一次同步间隔时间内,总共同步的数据总量
	public static int data_sync_num;
	//数据同步服务器地址 
	public static String server_host;
	//数据同步服务器端口
	public static int server_port;
	//数据同步的表名
//	public static String tableName;
	//匹配的表名列表
	public static List<String> tableNameArray;
	//一次发送的数据量
	public static int datas_num;

}
