package com.bjsxt.ego.item.service;

import com.bjsxt.ego.common.pojo.EgoResult;

public interface ItemItemService {
	
	/**
	 * 需要调用ego-rpc发布的服务，根据商品的ID查询商品的基本信息
	 * @param itemId
	 * @return
	 */
	EgoResult getItemBaseInfo(Long itemId);
	/**
	 * 根据商品的ID查询商品的描述信息，以字符串返回，因为本身就是一个HTML片段
	 * @param itemId
	 * @return
	 */
	String getItemDescById(Long itemId);
	
	/**
	 * 根据商品的id获取商品的规格参数信息
	 * @param itemId
	 * @return 返回值是一个html片段
	 */
	String getItemParam(Long itemId);
}
