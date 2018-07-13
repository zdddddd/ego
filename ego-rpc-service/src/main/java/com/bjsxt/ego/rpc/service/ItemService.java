package com.bjsxt.ego.rpc.service;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbItem;
import com.bjsxt.ego.rpc.pojo.TbItemDesc;
import com.bjsxt.ego.rpc.pojo.TbItemParamItem;


public interface ItemService {
	
	/**
	 * 根据商品ID查询商品信息
	 * @param itemId
	 * @return
	 */
	TbItem getItem(Long itemId);
	
	EUDataGridResult getItemList(int page, int rows);
	
	/**
	 * 保存商品信息
	 * @param item 商品基本信息封装对象
	 * @param desc 商品描述信息封装对象
	 * @return 别忘了ego-common中的EgoResult类要实现序列化接口
	 */
	EgoResult saveItem(TbItem item, TbItemDesc desc, TbItemParamItem itemParamItem);
	
	/**
	 * 获取商品基本信息的方法
	 * @param itemId
	 * @return
	 */
	EgoResult getItemBaseInfo(long itemId);
	
	/**
	 * 查询商品的描述信息
	 * @param itemId
	 * @return
	 */
	EgoResult getItemDesc(long itemId);
	
	/**
	 * 根据商品的ID查询商品的规格参数信息
	 * @param itemId
	 * @return
	 */
	EgoResult getItemParam(long itemId);
	
}
