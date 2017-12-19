package cn.jh.datasync.client.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jh.datasync.client.model.ColumnInfo;
import cn.jh.datasync.client.service.IDataSyncClientService;

@Service
public class DataSyncClientServiceImpl implements IDataSyncClientService {
	
//	@Resource
//	private JdbcTemplate template;
    
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	/**
	 * 获取表的结构
	 * @param tableName 表名
	 */
	@Override
	public List<ColumnInfo> getColumnInfo(String tableName){
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("tableName", tableName);
		return sqlSessionTemplate.selectList("DataSyncClientMapper.getColumnInfo",paramMap);
//		RowMapper<ColumnInfo> rowMapper = new BeanPropertyRowMapper<ColumnInfo>(ColumnInfo.class);
//		return template.query("select * from all_tab_columns where Table_Name=UPPER('"+tableName+"')",rowMapper);
	}
	
	/**
	 * 获取增量的rowid
	 * @param tableName 表名
	 */
//	@Override
//	@Transactional
//	public List<Map<String,Object>> getData(String tableName,List<ColumnInfo> columnInfos) {
////		Map<String, Object> paramMap = new HashMap<String,Object>();
////		//查看表的字段中有无ROWID_的字段
////		paramMap.put("tableName", tableName);
////		return 	sqlSessionTemplate.selectList("DataSyncClientMapper.getData",paramMap);
//		return template.queryForList("select * from ykt_cur."+tableName, new MyRowMapper(columnInfos));
//	}
//	
//	class MyRowMapper implements RowMapper{
//		
//		private List<ColumnInfo> columnInfos;
//		
//		public MyRowMapper(){
//		};
//		public MyRowMapper(List<ColumnInfo> columnInfos) {
//			this.columnInfos=columnInfos;
//		}
//		@Override
//		public List<Map<String,Object>> mapRow(ResultSet rs, int rowNum) throws SQLException {
//			List<Map<String,Object>> arrayList = new ArrayList<>();
//			while(rs.next()){
//				Map<String,Object> map = new HashMap<String,Object>();
//				for(int i=1;i<=columnInfos.size();i++) {
//					String Column_name = columnInfos.get(i-1).getColumn_name();
//					Object value = rs.getObject(Column_name);
//					if(value instanceof Integer) {
//						map.put(Column_name, (Integer)value);
//					}else if(value instanceof BigDecimal) {
//						map.put(Column_name, String.valueOf(value));
//					}else if(value instanceof String) {
//						map.put(Column_name, (String)value);
//					}
//				}
//				arrayList.add(map);
//			}
//			return arrayList;
//		}
//		
//	}
	
	
	/**
	 * 获取增量的rowid
	 * @param tableName 表名
	 */
	@Override
	@Transactional
	public List<Map<String,Object>> getData(String tableName,List<ColumnInfo> columnInfos) {
//		Map<String, Object> paramMap = new HashMap<String,Object>();
//		//查看表的字段中有无ROWID_的字段
//		paramMap.put("tableName", tableName);
//		return 	sqlSessionTemplate.selectList("DataSyncClientMapper.getData",paramMap);
		Connection cn = sqlSessionTemplate.getConnection();
		ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String,Object>>();
		PreparedStatement pst = null;
		ResultSet rs =null;
		try {
			pst = cn.prepareStatement("select * from "+tableName);
			pst.setFetchSize(10000);
			rs = pst.executeQuery();
			while(rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				for(int i=1;i<=columnInfos.size();i++) {
					String Column_name = columnInfos.get(i-1).getColumn_name();
					Object value = rs.getObject(Column_name);
					if(value instanceof Integer) {
						map.put(Column_name, (Integer)value);
					}else if(value instanceof BigDecimal) {
						map.put(Column_name, String.valueOf(value));
					}else if(value instanceof String) {
						map.put(Column_name, (String)value);
					}
				}
				arrayList.add(map);
				map=null;
			}
			sqlSessionTemplate.clearCache();
			return arrayList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pst !=null) {
					pst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * 某表的数据增量信息
	 * @throws SQLException 
	 * @tableName 表名
	 */
/*	@Override
	public Map<String, Object> getIncreData(String tableName,String rid) throws SQLException {
//		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
//		//设置事务隔离级别
//		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//		PlatformTransactionManager ptm = new DataSourceTransactionManager(dataSource);
//		//获得事务状态
//		TransactionStatus transactionStatus = ptm.getTransaction(dtd);
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("tableName", tableName);
		if(StringUtils.isNoneBlank(rid)) {
			paramMap.put("rid", rid);
			//表结构
			List<ColumnInfo> columnInfoList = sqlSessionTemplate.selectList("DataSyncClientMapper.getColumnInfo",paramMap);
			//查询出未同步的一条记录
			Map<String,Object> oneRecord = sqlSessionTemplate.selectOne("DataSyncClientMapper.getIncreData",paramMap);
			//保存新rowid到tableName_ROWID表中
			sqlSessionTemplate.insert("DataSyncClientMapper.saveRowid",paramMap);
			paramMap.put("columnInfo", columnInfoList);
			paramMap.put("data", oneRecord);
		}
//		paramMap.put("transactionStatus",transactionStatus);
//		paramMap.put("ptm",ptm);
		return paramMap;
	}*/
	
//	/**
//	 * 某表的数据增量信息
//	 * @throws SQLException 
//	 * @tableName 表名
//	 */
//	@Override
//	public Map<String, Object> getIncreData(String tableName,List<String> newRowidList,List<ColumnInfo> columnInfos,Long msgsn) throws SQLException {
//		Map<String, Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("tableName", tableName);
//		//查看表的字段中有无ROWID_的字段(同步视图时)
//		paramMap.put("rowid","rowid");
//		for (ColumnInfo columnInfo : columnInfos) {
//			if("ROWID_".equals(columnInfo.getColumn_name())){
//				paramMap.put("rowid","ROWID_");
//			}
//		}		
//		if(newRowidList !=null && newRowidList.size()>0 ) {
//			paramMap.put("newRowidList", newRowidList);
//			List<Map<String,Object>> list = new ArrayList<>();
//			//表结构
//			List<ColumnInfo> columnInfoList = sqlSessionTemplate.selectList("DataSyncClientMapper.getColumnInfo",paramMap);
//			//查询出表未同步的数据
//			for (String rid : newRowidList) {
//				paramMap.put("rid", rid);
//				list.add(sqlSessionTemplate.selectOne("DataSyncClientMapper.getIncreData",paramMap));
//				//保存新rowid到tableName_ROWID表中
//				TableROWID tableROWID = new TableROWID(rid,msgsn);
//				paramMap.put("tableROWID", tableROWID);
//				sqlSessionTemplate.insert("DataSyncClientMapper.saveRowid",paramMap);
//			}
//			paramMap.remove("newRowidList");
//			paramMap.remove("tableROWID");
//			paramMap.remove("rid");
//			paramMap.put("columnInfo", columnInfoList);
//			paramMap.put("datas", list);
//		}
//		paramMap.remove("rowid");
//		return paramMap;
//	}
//	
//	
//	
//	/**
//	 * 获取增量的rowid的数量
//	 * @param tableName 表名
//	 */
//	@Override
//	public int getNewNum(String tableName) {
//		HashMap<String, String> map = new HashMap<String,String>();
//		map.put("tableName", tableName);
//		return sqlSessionTemplate.selectOne("DataSyncClientMapper.getNewNum",map);
//	}
//	/**
//	 * 创建表
//	 * @param tableName 表名
//	 * @return 成功返回 true ,失败返回 false
//	 */
//	@Override
//	public void createTable(String tableName_ROWID) {
//		Map<String, Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("tableName_ROWID", tableName_ROWID);
//		sqlSessionTemplate.update("DataSyncClientMapper.createTable",paramMap);
//	}
//	/**
//	 * 获得数据库中的表名
//	 */
//	@Override
//	public List<String> getTablesName() {
//		return sqlSessionTemplate.selectList("DataSyncClientMapper.getTablesName");
//	}
//	/**
//	 * 清除数据,测试时用
//	 */
//	@Override
//	public void clearData(String tableName_ROWID) {
//		Map<String, Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("tableName_ROWID", tableName_ROWID);
//		sqlSessionTemplate.delete("DataSyncClientMapper.clearData",paramMap);
//	}
//	
//	/**
//	 * 数据同步服务器同步确认
//	 * @param tableName 表名
//	 * @param msgsn	消息编号
//	 */
//	@Override
//	public void syncConfirm(String tableName, Long msgsn) {
//		Map<String, Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("tableName", tableName);
//		paramMap.put("msgsn", msgsn);
//		sqlSessionTemplate.delete("DataSyncClientMapper.syncConfirm",paramMap);
//	}
//	/**
//	 *  @return tableName_ROWID 表中serverComplete为0的msgsn列表
//	 */
//	@Override
//	public List<Long> getNoSyncMsgsn(String tableName_ROWID) {
//		Map<String, Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("tableName_ROWID", tableName_ROWID);
//		return sqlSessionTemplate.selectList("DataSyncClientMapper.getNoSyncMsgsn",paramMap);
//	}

}
