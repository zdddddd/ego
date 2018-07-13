package com.bjsxt.ego.rpc.service;

import java.util.List;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbContentCategory;

public interface ContentCategoryService {
	
	/**
	 * 根据内容分类父节点ID查询内容分类节点列表信息
	 * @param parentId
	 * @return
	 */
	List<TbContentCategory> getContentCategoryList(Long parentId);
	
	/**
	 * 保存内容分类信息，同时返回数据库生成的主键值
	 * @param category
	 * @return
	 */
	EgoResult saveContentCategory(TbContentCategory category);
	
}
