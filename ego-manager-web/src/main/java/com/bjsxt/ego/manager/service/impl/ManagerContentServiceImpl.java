package com.bjsxt.ego.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbContent;
import com.bjsxt.ego.rpc.service.ContentService;
import com.bjsxt.ego.rpc.service.RedisService;
import com.bjsxt.ego.manager.service.ManagerContentService;

@Service
public class ManagerContentServiceImpl implements ManagerContentService {
	@Autowired
	private ContentService contentServiceProxy;
	@Autowired
	private RedisService redisServiceProxy;

	@Override
	public EUDataGridResult getContentList(int page, int rows, long categoryId) {
		// 调用远程服务
		// 按说应该让远程服务返回一个更普遍的数据格式，现在局限于easyui的datagrid数据格式
		return this.contentServiceProxy.getContentList(page, rows, categoryId);
	}

	@Override
	public EgoResult saveContent(TbContent content) {
		Date date = new Date();
		// 补全值
		content.setCreated(date);
		content.setUpdated(date);

		// return this.contentServiceProxy.saveContent(content);

		EgoResult result = this.contentServiceProxy.saveContent(content);
		// 同步缓存
		this.redisServiceProxy.syncContent(content.getCategoryId());

		return result;
	}

	@Override
	public EgoResult removeContents(Long[] ids, Long categoryId) {
		List<Long> idList = new ArrayList<Long>();
		for (Long id : ids) {
			idList.add(id);
		}
		EgoResult result = this.contentServiceProxy.removeContents(idList);
		// 同步缓存
		this.redisServiceProxy.syncContent(categoryId);
		return result;
	}

}
