package com.bjsxt.ego.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.search.service.ItemService;

@Controller
@RequestMapping("/search/manager")
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 导入商品数据到索引库
	 * 
	 * http://localhost:8083/search/manager/importall
	 * 
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public EgoResult importAllItems() {
		EgoResult result = itemService.importAllItems();
		return result;
	}

}
