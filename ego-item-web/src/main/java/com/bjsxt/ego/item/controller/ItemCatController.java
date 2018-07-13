package com.bjsxt.ego.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.item.service.ItemItemCatService;

@Controller
public class ItemCatController {
	
	@Autowired
	private ItemItemCatService itemItemCatService;
	
	@RequestMapping(value="/itemcat/all", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public Object getItemCategoryAll(String callback) {
		
		//调用dubbo发布的服务，从ego-rpc获取到商品分类的json串
		//使用callback封装为一个js片段，发送给ego-portal-web
		String json = this.itemItemCatService.getItemCategoryAll();
		String result = callback + "(" + json +");";
		return result;
	}
	
}
