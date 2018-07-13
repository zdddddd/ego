package com.bjsxt.ego.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.TreeNode;
import com.bjsxt.ego.manager.service.ManagerItemCatService;
import com.bjsxt.ego.rpc.pojo.TbItemCat;
import com.bjsxt.ego.rpc.service.ItemCatService;

@Service
public class ManagerItemCatServiceImpl implements ManagerItemCatService {
	
	//远程代理对象
	@Autowired
	private ItemCatService itemCatServiceProxy;
	
	@Override
	public List<TreeNode> getItemCatList(Long parentId) {
		//远程服务接口应该尽量通用，最好不要直接返回List<TreeNode>类型的数据
		List<TbItemCat> itemCatList = itemCatServiceProxy.getItemCatList(parentId);
		
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		
		TreeNode node = null;
		for (TbItemCat itemCat : itemCatList) {
			node = new TreeNode(itemCat.getId(), itemCat.getName(), itemCat.getIsParent() ? "closed" : "open");
			nodeList.add(node);
		}
		
		return nodeList;
	}

}
