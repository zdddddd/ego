package com.bjsxt.ego.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.pojo.TreeNode;
import com.bjsxt.ego.manager.service.ManagerContentCategoryService;
import com.bjsxt.ego.rpc.pojo.TbContentCategory;

@Controller
public class ContentCategoryController {
	@Autowired
	private ManagerContentCategoryService managerContentCategoryService;
	
	@RequestMapping(value = "/content/category/list", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public List<TreeNode> getContentCategoryList(
			@RequestParam(required = false, defaultValue = "0") long id) {
		List<TreeNode> treeNodes = this.managerContentCategoryService.getContentCategoryList(id);
		return treeNodes;
	}
	
	@RequestMapping(value="/content/category/create", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public EgoResult saveContentCategory(TbContentCategory contentCategory) {
		//controller方法使用pojo接收传过来的参数
		//调用本地服务保存信息，在保存信息之前，要补全contentCategory的值。
		return this.managerContentCategoryService.saveContentCategory(contentCategory);
	}

}
