package com.bjsxt.ego.manager.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.manager.service.ManagerContentService;
import com.bjsxt.ego.rpc.pojo.TbContent;

@Controller
public class ContentController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ManagerContentService managerContentService;

	@RequestMapping(value = "/content/query/list", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public EUDataGridResult getContentList(int rows, int page, long categoryId) {
		return this.managerContentService
				.getContentList(page, rows, categoryId);
	}

	@RequestMapping(value = "/content/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public EgoResult saveContent(TbContent content) {
		//需要补全created和updated两个属性的值
		return this.managerContentService.saveContent(content);
	}
	
	@RequestMapping(value="/content/delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public EgoResult removeContent(Long[] ids, Long categoryId) {
		for (Long id : ids) {
			logger.error(id);
		}
		return this.managerContentService.removeContents(ids, categoryId);
	}

}
