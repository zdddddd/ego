package com.bjsxt.ego.item.pojo;

import com.bjsxt.ego.rpc.pojo.TbItem;

public class MyItem extends TbItem {
	
	public String[] getImages() {
		if (this.getImage() != null) {
			return this.getImage().split(",");
		}
		return null;
	}
	
}
