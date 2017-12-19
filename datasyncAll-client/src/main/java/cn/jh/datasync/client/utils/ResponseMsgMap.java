package cn.jh.datasync.client.utils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存服务器回复信息
 * @author wanlongfei
 *
 */
public class ResponseMsgMap {
	//Long 为 msgsn 消息编号,Boolean为 服务器事务处理信息
	public  static ConcurrentHashMap<String, Boolean>  map = new ConcurrentHashMap<>();
	
	//检查同步数据服务器返回的信息
	public static ConcurrentHashMap<String,List<Long>> msgsnMap = new ConcurrentHashMap<>();
}
