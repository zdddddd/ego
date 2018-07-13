package com.bjsxt.ego.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 该类和CatNode共同组织页面上需要的json格式
 * @author Administrator
 *
 */
public class CatResult implements Serializable {

	private List<?> data;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

}
