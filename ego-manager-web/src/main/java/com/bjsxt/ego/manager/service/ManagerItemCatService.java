package com.bjsxt.ego.manager.service;

import java.util.List;

import com.bjsxt.ego.common.pojo.TreeNode;

public interface ManagerItemCatService {
	
	/**
	 * 根据父节点ID查询子节点集合信息
	 * @param parentId
	 * @return
	 */
	List<TreeNode> getItemCatList(Long parentId);
	
}
