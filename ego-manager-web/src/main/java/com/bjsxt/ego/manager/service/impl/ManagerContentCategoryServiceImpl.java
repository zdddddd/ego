package com.bjsxt.ego.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.pojo.TreeNode;
import com.bjsxt.ego.manager.service.ManagerContentCategoryService;
import com.bjsxt.ego.rpc.pojo.TbContentCategory;
import com.bjsxt.ego.rpc.service.ContentCategoryService;

@Service
public class ManagerContentCategoryServiceImpl implements
		ManagerContentCategoryService {
	
	@Autowired
	private ContentCategoryService contentCategoryServiceProxy;
	
	@Override
	public List<TreeNode> getContentCategoryList(Long parentId) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = null;
		
		//调用dubbo的远程服务，获取指定分类下的子分类集合
		List<TbContentCategory> contentCategories = this.contentCategoryServiceProxy.getContentCategoryList(parentId);
		//将TbContentCategory的集合转换为TreeNode的集合
		if (contentCategories != null && contentCategories.size() > 0) {
			for (TbContentCategory cc : contentCategories) {
				//如果cc是一个父节点，则要设置node的state为closed，否则是open
				node = new TreeNode(cc.getId(), cc.getName(), cc.getIsParent() ? "closed" : "open");
				nodes.add(node);
			}
		}
		return nodes;
	}

	@Override
	public EgoResult saveContentCategory(TbContentCategory category) {
		
		Date date = new Date();
		
		//补全值
		category.setCreated(date);
		category.setUpdated(date);
//		category.setId(id);
		//新增节点不是父节点
		category.setIsParent(false);
//		category.setName(name);
//		category.setParentId(parentId);
		category.setSortOrder(1);
		category.setStatus(1);
		
		EgoResult result = this.contentCategoryServiceProxy.saveContentCategory(category);
		
		return result;
	}

}
