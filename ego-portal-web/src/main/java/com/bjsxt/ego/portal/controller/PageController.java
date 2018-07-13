package com.bjsxt.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bjsxt.ego.portal.service.PortalContentService;

@Controller
public class PageController {
	@Autowired
	private PortalContentService portalContentService;
	
	@RequestMapping(value="{page}")
	public String showPage(@PathVariable String page, Model model) {
		if ("index".equals(page)) {
			
			
			String jsonStr = portalContentService.getContentListByCategoryId(89);
			//将打广告位信息写到页面上 request作用域
			model.addAttribute("ad1", jsonStr);
		}
		return page;
	}
	
}
