package com.bjsxt.ego.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.common.pojo.TreeNode;
import com.bjsxt.ego.manager.service.ManagerItemCatService;

@Controller
public class ItemCatController {
	
	@Autowired
	private ManagerItemCatService managerItemCatService;
	
	@RequestMapping(value = "/item/cat/list")
	@ResponseBody
	public List<TreeNode> getItemCatList(
			@RequestParam(required = false, defaultValue = "0") Long id) {
		List<TreeNode> nodeList = this.managerItemCatService.getItemCatList(id);
		return nodeList;
	}

}
