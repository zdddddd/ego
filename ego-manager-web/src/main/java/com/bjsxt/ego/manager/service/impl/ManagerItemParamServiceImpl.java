package com.bjsxt.ego.manager.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.manager.service.ManagerItemParamService;
import com.bjsxt.ego.rpc.pojo.TbItemParam;
import com.bjsxt.ego.rpc.service.ItemParamService;

@Service
public class ManagerItemParamServiceImpl implements ManagerItemParamService {
	
	@Autowired
	private ItemParamService itemParamServiceProxy;
	
	@Override
	public EUDataGridResult getItemParamList(int page, int rows) {
		return this.itemParamServiceProxy.getItemParamList(page, rows);
	}

	@Override
	public EgoResult getItemParamByCid(Long cid) {
		
		TbItemParam itemParam = this.itemParamServiceProxy.getItemParamByCid(cid);
		
		if (itemParam == null) {
			return EgoResult.ok(null);
		} else {
			return EgoResult.ok(itemParam);
		}
	}

	@Override
	public EgoResult saveItemParam(String paramData, Long cid) {
		
		Date date = new Date();
		
		//调用远程服务保存商品规格参数模板信息
		//补全商品模板信息
		TbItemParam itemParam = new TbItemParam();
		
		itemParam.setCreated(date);
		itemParam.setUpdated(date);
//		itemParam.setId(id);
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		//这里调用了远程服务，不是说一定是保存成功的，保存的结果信息让ego-rpc自己封装传到这里比较合适。
		EgoResult result = this.itemParamServiceProxy.saveItemParam(itemParam);
		return result;
	}

}
