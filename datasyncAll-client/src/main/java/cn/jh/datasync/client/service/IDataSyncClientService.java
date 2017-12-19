package cn.jh.datasync.client.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.jh.datasync.client.model.ColumnInfo;

/**
 * 数据同步客户端服务
 * @author wanlongfei
 *
 */
public interface IDataSyncClientService {
	
	
	/**
	 * 获取表的结构
	 * @param tableName 表名
	 */
	List<ColumnInfo> getColumnInfo(String tableName);
	/**
	 * 获取表数据
	 * @param tableName
	 * @param columnInfos 
	 */
	List<Map<String,Object>> getData(String tableName, List<ColumnInfo> columnInfos);
	/**
	 * 获取增量的rowid的数量
	 * @param tableName
	 */
//	int getNewNum(String tableName);
//	/**
//	 * 同步某张表数据增量
//	 * @param tableName 表名
//	 * @param newRowid 新增数据的rowid列表
//	 * @throws SQLException 
//	 */
////	Map<String, Object> getIncreData(String tableName,String rid) throws SQLException;
//	/**
//	 * 批量同步某张表数据增量
//	 * @param tableName 表名
//	 * @param columnInfo 表字段信息
//	 * @param newRowid 新增数据的rowid列表
//	 * @throws SQLException 
//	 */
//	Map<String, Object> getIncreData(String tableName,List<String> newRowidList, List<ColumnInfo> columnInfo,Long msgsn) throws SQLException;
//	
//	/**
//	 * 创建表
//	 * @param tableName 表名
//	 * @return 成功返回 true ,失败返回 false
//	 */
//	void createTable(String tableName);
//	
//	List<String> getTablesName();
//	
//	/**
//	 * 清除同步数据 测试使用
//	 * @param tableName_ROWID 
//	 */
//	void clearData(String tableName_ROWID);
//	/**
//	 * 数据同步服务器同步确认
//	 * @param tableName 表名
//	 * @param msgsn	消息编号
//	 */
//	void syncConfirm(String tableName, Long msgsn);
//	/**
//	 * 
//	 * @param tableName_ROWID 
//	 * @return tableName_ROWID 表中serverComplete为0的msgsn列表
//	 */
//	List<Long> getNoSyncMsgsn(String tableName_ROWID);

}
