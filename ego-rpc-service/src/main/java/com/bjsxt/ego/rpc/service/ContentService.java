package com.bjsxt.ego.rpc.service;

import java.util.List;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbContent;

public interface ContentService {
	
	/**
	 * 根据分类ID获取内容列表
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	EUDataGridResult getContentList(int page, int rows, long categoryId);
	
	/**
	 * 获取指定内容分类下的所有内容信息
	 * @param categoryId
	 * @return
	 */
	List<TbContent> getContentList(long categoryId);
	
	/**
	 * 保存内容信息
	 * @param content
	 * @return
	 */
	EgoResult saveContent(TbContent content);
	
	/**
	 * 一次性删除多条记录
	 * @param ids
	 * @return
	 */
	EgoResult removeContents(List<Long> ids);
}
