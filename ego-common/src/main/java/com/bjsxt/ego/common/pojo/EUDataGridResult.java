package com.bjsxt.ego.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 用于封装分页数据，不限于Easyui Datagrid
 * 只要是分页的，都可以使用该bean来封装。
 * @author Administrator
 *
 */
public class EUDataGridResult implements Serializable {

	private long total;
	private List<?> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
