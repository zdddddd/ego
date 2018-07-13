package com.bjsxt.ego.rpc.service;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbItemParam;

public interface ItemParamService {
	
	/**
	 * 分页查询商品规格项列表信息
	 * @param page
	 * @param rows
	 * @return
	 */
	EUDataGridResult getItemParamList(int page, int rows);
	/**
	 * 根据商品分类ID查询商品规格参数模板
	 * @param cid
	 * @return
	 */
	TbItemParam getItemParamByCid(Long cid);
	
	/**
	 * 保存商品规格参数模板信息
	 * @param itemParam
	 * @return
	 */
	EgoResult saveItemParam(TbItemParam itemParam);
	
}
