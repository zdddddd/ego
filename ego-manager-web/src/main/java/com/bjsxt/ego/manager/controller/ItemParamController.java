package com.bjsxt.ego.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.manager.service.ManagerItemParamService;

@Controller
public class ItemParamController {
	
	@Autowired
	private ManagerItemParamService managerItemParamService;
	
	@RequestMapping(value="/item/param/list", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public Object getItemParamList(
			@RequestParam(required=false, defaultValue="1") int page,
			@RequestParam(required=false, defaultValue="10") int rows) {
		
		//分页查询商品的规格项
		return this.managerItemParamService.getItemParamList(page, rows);
	}
	
	@RequestMapping(value="/item/param/query/itemcatid/{itemCatId}", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public EgoResult getItemParamByCid(@PathVariable Long itemCatId) {
		
		//调用service，查询该分类ID对应的商品规格参数模板数据
		return this.managerItemParamService.getItemParamByCid(itemCatId);
	}
	
	@RequestMapping(value="/item/param/save/{cid}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public EgoResult saveItemParam(String paramData, @PathVariable Long cid) {
		//调用service保存商品规格参数模板
		return this.managerItemParamService.saveItemParam(paramData, cid);
	}
	
}
