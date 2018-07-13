package com.bjsxt.ego.manager.service;

import java.util.List;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbContent;

public interface ManagerContentService {
	
	/**
	 * 根据分类ID获取该分类下的内容列表信息，分页查询
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	EUDataGridResult getContentList(int page, int rows, long categoryId);
	
	/**
	 * 保存内容的方法
	 * @param content
	 * @return
	 */
	EgoResult saveContent(TbContent content);
	
	/**
	 * 删除多条记录
	 * @param ids
	 * @return
	 */
	EgoResult removeContents(Long[] ids, Long categoryId);
	
}
