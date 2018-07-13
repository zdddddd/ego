package com.bjsxt.ego.item.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.item.pojo.MyItem;
import com.bjsxt.ego.item.service.ItemItemService;
import com.bjsxt.ego.rpc.pojo.TbItem;

@Controller
public class ItemController {
	@Autowired
	private ItemItemService itemItemService;

	@RequestMapping(value = "/item/{itemId}")
	public String getItemBaseInfo(@PathVariable Long itemId, Model model) {
		EgoResult result = itemItemService.getItemBaseInfo(itemId);
		
		TbItem item = (TbItem) result.getData();
		
		MyItem myItem = new MyItem();
		myItem.setBarcode(item.getBarcode());
		myItem.setCid(item.getCid());
		myItem.setCreated(item.getCreated());
		myItem.setId(item.getId());
		myItem.setImage(item.getImage());
		myItem.setNum(item.getNum());
		myItem.setPrice(item.getPrice());
		myItem.setSellPoint(item.getSellPoint());
		myItem.setStatus(item.getStatus());
		myItem.setTitle(item.getTitle());
		myItem.setUpdated(item.getUpdated());
		
		model.addAttribute("item", myItem);
		return "item";
	}

	@RequestMapping(value = "/item/desc/{itemId}", produces = MediaType.TEXT_HTML_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId) {
		String string = this.itemItemService.getItemDescById(itemId);
		return string;
	}

	@RequestMapping(value = "/item/param/{itemId}", produces = MediaType.TEXT_HTML_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId) {
		String string = this.itemItemService.getItemParam(itemId);
		return string;
	}

}
