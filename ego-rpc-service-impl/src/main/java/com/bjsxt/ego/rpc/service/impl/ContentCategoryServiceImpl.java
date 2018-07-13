package com.bjsxt.ego.rpc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.mapper.TbContentCategoryMapper;
import com.bjsxt.ego.rpc.pojo.TbContentCategory;
import com.bjsxt.ego.rpc.pojo.TbContentCategoryExample;
import com.bjsxt.ego.rpc.pojo.TbContentCategoryExample.Criteria;
import com.bjsxt.ego.rpc.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<TbContentCategory> getContentCategoryList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件，让parentId等于给定的值，就可以查询出给定值的子分类信息
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> contentCategories = this.contentCategoryMapper.selectByExample(example);
		return contentCategories;
	}

	@Override
	public EgoResult saveContentCategory(TbContentCategory category) {
		//返回的是受影响的行数，该insert方法也修改了mapper文件，可以返回生成的主键值
		int rowCount = this.contentCategoryMapper.insert(category);
		int rowCount1 = -1;
		//查询出被添加子节点的父节点
		TbContentCategory parent = this.contentCategoryMapper.selectByPrimaryKey(category.getParentId());
		
		if (parent != null) {
			parent.setIsParent(true);
			//在更新的时候，where id=给定的id
			//update tb_content_category set ... .. . where id=给定的id
			rowCount1 = this.contentCategoryMapper.updateByPrimaryKey(parent);
		}
		
		if (rowCount == 1 && rowCount1 == 1) {
			return EgoResult.ok(category);
		} else {
			return EgoResult.build(500, "添加失败");
		}
	}

}
