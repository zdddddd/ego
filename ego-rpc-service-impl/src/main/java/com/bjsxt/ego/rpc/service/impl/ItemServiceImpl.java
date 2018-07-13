package com.bjsxt.ego.rpc.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.JsonUtils;
import com.bjsxt.ego.rpc.dao.JedisClient;
import com.bjsxt.ego.rpc.mapper.TbItemDescMapper;
import com.bjsxt.ego.rpc.mapper.TbItemMapper;
import com.bjsxt.ego.rpc.mapper.TbItemParamItemMapper;
import com.bjsxt.ego.rpc.pojo.TbItem;
import com.bjsxt.ego.rpc.pojo.TbItemDesc;
import com.bjsxt.ego.rpc.pojo.TbItemExample;
import com.bjsxt.ego.rpc.pojo.TbItemParamItem;
import com.bjsxt.ego.rpc.pojo.TbItemParamItemExample;
import com.bjsxt.ego.rpc.pojo.TbItemParamItemExample.Criteria;
import com.bjsxt.ego.rpc.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public TbItem getItem(Long itemId) {
		// 根据主键查询商品信息
		return this.itemMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		// 实例化Example用于设置查询条件，只是此处没有设置任何查询条件
		TbItemExample example = new TbItemExample();
		// 使用分页插件做分页
		PageHelper.startPage(page, rows);
		// 分页查询
		List<TbItem> items = this.itemMapper.selectByExample(example);
		// 获取分页信息的工具类
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(items);
		// 获取一共多少条记录
		long total = pageInfo.getTotal();

		EUDataGridResult result = new EUDataGridResult();
		result.setRows(items);
		result.setTotal(total);

		return result;
	}

	@Override
	public EgoResult saveItem(TbItem item, TbItemDesc desc,
			TbItemParamItem itemParamItem) {
		this.itemMapper.insert(item);
		this.itemDescMapper.insert(desc);
		this.itemParamItemMapper.insert(itemParamItem);
		return EgoResult.ok();
	}

	@Override
	public EgoResult getItemBaseInfo(long itemId) {

		try {
			// 根据命名规则，从redis中查询有没有对应的信息
			String jsonStr = this.jedisClient.get(REDIS_ITEM_KEY + ":" + itemId
					+ ":base");
			// 如果获取到的字符串不是空
			if (!StringUtils.isBlank(jsonStr)) {
				TbItem item = JsonUtils.jsonToPojo(jsonStr, TbItem.class);
//				result.getData() 是一个LinkedHashMap
//				result.getMsg()  OK
//				result.getStatus() 200
				return EgoResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果缓存中没有信息，则需要查询数据库

		TbItem item = this.itemMapper.selectByPrimaryKey(itemId);

		try {
			// 查询结束后，将结果添加到缓存
			if (item != null) {
				// 添加到redis中
				this.jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base",
						JsonUtils.objectToJson(item));
				// 设置过期时间
				this.jedisClient.expire(
						REDIS_ITEM_KEY + ":" + itemId + ":base",
						this.REDIS_ITEM_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EgoResult.ok(item);
	}

	@Override
	public EgoResult getItemDesc(long itemId) {
		// 添加缓存
		try {
			// 添加缓存逻辑
			// 从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId
					+ ":desc");
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return EgoResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 创建查询条件
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

		try {
			// 把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			// 设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc",
					REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EgoResult.ok(itemDesc);

	}

	@Override
	public EgoResult getItemParam(long itemId) {
//		添加缓存
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return EgoResult.ok(itemParamItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询规格参数
		//设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size()>0) {
			TbItemParamItem paramItem = list.get(0);
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return EgoResult.ok(paramItem);
		}
		return EgoResult.build(400, "无此商品规格");

	}
}
