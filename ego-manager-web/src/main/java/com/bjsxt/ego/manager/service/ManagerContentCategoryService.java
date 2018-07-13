package com.bjsxt.ego.manager.service;

import java.util.List;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.pojo.TreeNode;
import com.bjsxt.ego.rpc.pojo.TbContentCategory;

public interface ManagerContentCategoryService {
	
	/**
	 * 根据父分类ID获取内容子分类集合信息
	 * @param parentId
	 * @return 符合easyui异步树的TreeNode集合
	 */
	List<TreeNode> getContentCategoryList(Long parentId);
	
	/**
	 * 调用远程服务保存内容分类
	 * @param category
	 * @return
	 */
	EgoResult saveContentCategory(TbContentCategory category);
	
}
