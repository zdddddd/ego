package com.bjsxt.ego.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bjsxt.ego.common.pojo.PictureResult;
import com.bjsxt.ego.manager.service.ManagerItemService;

@Controller
public class ItemImageUploadController {
	
	@Autowired
	private ManagerItemService managerItemService;
	
	@RequestMapping(value = "/pic/upload", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public PictureResult uploadFile(MultipartFile uploadFile) {
		return this.managerItemService.uploadItemImage(uploadFile);
	}
	
}
