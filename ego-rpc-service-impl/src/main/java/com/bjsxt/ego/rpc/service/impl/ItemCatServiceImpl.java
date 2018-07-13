package com.bjsxt.ego.rpc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.CatNode;
import com.bjsxt.ego.common.pojo.CatResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.ExceptionUtil;
import com.bjsxt.ego.common.util.JsonUtils;
import com.bjsxt.ego.rpc.dao.JedisClient;
import com.bjsxt.ego.rpc.mapper.TbItemCatMapper;
import com.bjsxt.ego.rpc.pojo.TbItemCat;
import com.bjsxt.ego.rpc.pojo.TbItemCatExample;
import com.bjsxt.ego.rpc.pojo.TbItemCatExample.Criteria;
import com.bjsxt.ego.rpc.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_ITEM_CATEGORY_REDIS_KEY}")
	private String INDEX_ITEM_CATEGORY_REDIS_KEY;

	@Override
	public List<TbItemCat> getItemCatList(Long parentId) {
		// 实例化Example对象
		TbItemCatExample example = new TbItemCatExample();
		// 获取Criteria，用于设置查询条件
		Criteria criteria = example.createCriteria();
		// 让父分类ID等于传过来的ID的值
		criteria.andParentIdEqualTo(parentId);

		List<TbItemCat> itemCatList = this.itemCatMapper
				.selectByExample(example);

		return itemCatList;
	}

	@Override
	public EgoResult getItemCatAll() {
		
		//首先检查缓存中是否有该商品分类的内容
		try {
			String itemCategoryJsonStr = jedisClient.get(this.INDEX_ITEM_CATEGORY_REDIS_KEY);
			if (!StringUtils.isBlank(itemCategoryJsonStr)) {
				//如果缓存中有内容，则直接将缓存信息转换为EgoResult对象，返回给调用者
				EgoResult result = JsonUtils.jsonToPojo(itemCategoryJsonStr, EgoResult.class);
				return result;
			}
		} catch (Exception e) {
//			Thread.currentThread().getName();
//			e.getClass().getCanonicalName();
//			String message = e.getMessage();
//			StackTraceElement[] elements = e.getStackTrace();
//			elements[0].getClassName()   类名
//			elements[0].getFileName()    文件名
//			elements[0].getLineNumber()  行号
//			elements[0].getMethodName()  方法名
			return EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		CatResult catResult = new CatResult();
		// 查询分类列表
		catResult.setData(getCatList(0));
		
		EgoResult result = EgoResult.ok(catResult);
		
		//将结果放到缓存中：
		try {
			//将java对象转换为json字符串
			String cacheString = JsonUtils.objectToJson(result);
			//放到redis缓存中，此处使用的是redis的string数据结构
			jedisClient.set(INDEX_ITEM_CATEGORY_REDIS_KEY, cacheString);
		} catch (Exception e) {
			return EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return result;
	}

	private List<?> getCatList(long parentId) {
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 返回值list
		List resultList = new ArrayList<>();
		// 向list中添加节点
		for (TbItemCat tbItemCat : list) {
			// 判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName("<a href='/products/" + tbItemCat.getId()
							+ ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				catNode.setItem(getCatList(tbItemCat.getId()));

				resultList.add(catNode);
				// 如果是叶子节点
			} else {
				resultList.add("/products/" + tbItemCat.getId() + ".html|"
						+ tbItemCat.getName());
			}
		}
		return resultList;
	}

}
