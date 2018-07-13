package com.bjsxt.ego.rpc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.mapper.TbItemParamMapper;
import com.bjsxt.ego.rpc.pojo.TbItemParam;
import com.bjsxt.ego.rpc.pojo.TbItemParamExample;
import com.bjsxt.ego.rpc.pojo.TbItemParamExample.Criteria;
import com.bjsxt.ego.rpc.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ItemParamServiceImpl implements ItemParamService {
	
	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Override
	public EUDataGridResult getItemParamList(int page, int rows) {
		//实例化Example对象
		TbItemParamExample example = new TbItemParamExample();
		
		//设置分页
		PageHelper.startPage(page, rows);
		//该方法查询了param_data字段的值
		List<TbItemParam> itemParams = this.itemParamMapper.selectByExampleWithBLOBs(example);
		
		PageInfo<TbItemParam> info = new PageInfo<TbItemParam>(itemParams);
		//获取数据库中一共多少条记录
		long total = info.getTotal();
		
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(itemParams);
		result.setTotal(total);
		
		return result;
	}

	@Override
	public TbItemParam getItemParamByCid(Long cid) {
		//实例化Example用于设置查询条件
		TbItemParamExample example = new TbItemParamExample();
		//获取Criteria设置查询条件
		Criteria criteria = example.createCriteria();
		//让tb_item_param表中的item_cat_id字段的值等于传过来的cid的值
//		select *  from tb_item_param  where item_cat_id=#{value}
		criteria.andItemCatIdEqualTo(cid);
		// query by example   QBE    query by criteria QBC
//		List<TbItemParam> itemParams = this.itemParamMapper.selectByExample(example);
		List<TbItemParam> itemParams = this.itemParamMapper.selectByExampleWithBLOBs(example);
		if (itemParams == null || itemParams.size() == 0) {
			return null;
		}
		
		return itemParams.get(0);
	}

	@Override
	public EgoResult saveItemParam(TbItemParam itemParam) {
		//接收受影响的行数
		int rowNumber = this.itemParamMapper.insert(itemParam);
		//如果受影响的行数为1，表示数据添加成功
		if (rowNumber == 1) {
			return EgoResult.ok();
		}
		return EgoResult.build(500, "添加失败");
	}

}
