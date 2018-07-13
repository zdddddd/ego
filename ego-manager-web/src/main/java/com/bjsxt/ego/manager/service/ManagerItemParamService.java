package com.bjsxt.ego.manager.service;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;

public interface ManagerItemParamService {
	
	/**
	 * 分页查询商品规格项列表，调用远程的dubbo服务
	 * @param page
	 * @param rows
	 * @return
	 */
	EUDataGridResult getItemParamList(int page, int rows);
	
	/**
	 * 根据商品分类ID查询商品规格参数模板信息
	 * @param cid
	 * @return
	 */
	EgoResult getItemParamByCid(Long cid);
	
	/**
	 * 保存商品规格参数模板信息
	 * @param paramData
	 * @param cid 
	 * @return
	 */
	EgoResult saveItemParam(String paramData, Long cid);
}
