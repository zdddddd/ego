package com.bjsxt.ego.item.service;

public interface ItemItemCatService {
	
	/**
	 * 通过调用远程dubbo服务，获取商品分类信息
	 * @return
	 */
	String getItemCategoryAll();
	
}
