package com.bjsxt.ego.rpc.service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService {
	
	/**
	 * 根据父分类ID查询子分类列表
	 * @param parentId
	 * @return
	 */
	List<TbItemCat> getItemCatList(Long parentId);
	
	/**
	 * 获取商品分类信息列表数据
	 * @return
	 */
	EgoResult getItemCatAll();
	
}
