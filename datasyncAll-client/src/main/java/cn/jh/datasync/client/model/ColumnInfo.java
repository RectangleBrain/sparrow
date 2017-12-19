package cn.jh.datasync.client.model;

import java.io.Serializable;

/**
 * 数据库表字段部分信息
 * @author wanlongfei
 *
 */
public class ColumnInfo implements Serializable {
	private String column_name; 	//字段名
	private String data_type;		//字段类型
	private String data_length;		//字段长度
	private String nullable;		//字段是否为空
	private String data_precision;
	private String data_scale;      
	
	//构造方法
	public ColumnInfo() {
	}
	public ColumnInfo(String column_name, String data_type, String data_length, String nullable, String data_precision,
			String data_scale) {
		this.column_name = column_name;
		this.data_type = data_type;
		this.data_length = data_length;
		this.nullable = nullable;
		this.data_precision = data_precision;
		this.data_scale = data_scale;
	}

	//set get 方法
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_length() {
		return data_length;
	}
	public void setData_length(String data_length) {
		this.data_length = data_length;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getData_precision() {
		return data_precision;
	}
	public void setData_precision(String data_precision) {
		this.data_precision = data_precision;
	}
	public String getData_scale() {
		return data_scale;
	}
	public void setData_scale(String data_scale) {
		this.data_scale = data_scale;
	}
	
	@Override
	public String toString() {
		return "ColumnInfo [column_name=" + column_name + ", data_type=" + data_type + ", data_length=" + data_length
				+ ", nullable=" + nullable + ", data_precision=" + data_precision + ", data_scale=" + data_scale + "]";
	}
}
