package com.bjsxt.ego.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.JsonUtils;
import com.bjsxt.ego.item.service.ItemItemCatService;
import com.bjsxt.ego.rpc.service.ItemCatService;

@Service
public class ItemItemCatServiceImpl implements ItemItemCatService {

	@Autowired //dubbo服务的远程代理对象
	private ItemCatService itemCatService;
	
	@Override
	public String getItemCategoryAll() {
		String data = null;
		//使用dubbo的远程代理对象获取商品分类列表信息
		EgoResult egoResult = this.itemCatService.getItemCatAll();
		//如果访问成功，并且确实获取到了数据，则使用JsonUtils工具类将其中的data数据转换为String的json字符串
		if (egoResult.getStatus() == 200 && egoResult.getData() != null) {
			data = JsonUtils.objectToJson(egoResult.getData());
		}
		return data;
	}

}
