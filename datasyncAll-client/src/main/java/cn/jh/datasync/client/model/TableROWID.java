package cn.jh.datasync.client.model;
/**
 * 同步数据记录,与数据库中的 表名_ROWID表对应
 * @author wanlongfei
 *
 */

import java.util.Date;

public class TableROWID {
	//rid主键
	private String rid;
	//客户端一次发送消息的编号
	private Long msgsn;
	//同步时间
	private Date syncdate = new Date();
	//服务器同步确认 0,1  0为服务器未同步,1服务已同步,默认为0
	private String serverComplete = "0";
	
	public TableROWID() {
	}
	public TableROWID(String rid, Long msgsn) {
		this.rid = rid;
		this.msgsn = msgsn;
	}

	public String getRowid() {
		return rid;
	}
	public void setRowid(String rid) {
		this.rid = rid;
	}
	public Long getMsgsn() {
		return msgsn;
	}
	public void setMsgsn(Long msgsn) {
		this.msgsn = msgsn;
	}
	public Date getSyncdate() {
		return syncdate;
	}
	public void setSyncdate(Date syncdate) {
		this.syncdate = syncdate;
	}
	public String getServerComplete() {
		return serverComplete;
	}
	public void setServerComplete(String serverComplete) {
		this.serverComplete = serverComplete;
	}
	@Override
	public String toString() {
		return "TableROWID [rid=" + rid + ", msgsn=" + msgsn + ", syncdate=" + syncdate + ", serverComplete="
				+ serverComplete + "]";
	}
}
