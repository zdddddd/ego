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
import com.bjsxt.ego.rpc.mapper.TbContentMapper;
import com.bjsxt.ego.rpc.pojo.TbContent;
import com.bjsxt.ego.rpc.pojo.TbContentExample;
import com.bjsxt.ego.rpc.pojo.TbContentExample.Criteria;
import com.bjsxt.ego.rpc.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private JedisClient jedisClient;

	@Autowired
	private TbContentMapper contentMapper;

	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public EUDataGridResult getContentList(int page, int rows, long categoryId) {

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		// 让内容的分类ID是指定的分类ID，查询该分类下的所有内容信息
		criteria.andCategoryIdEqualTo(categoryId);
		// 设置分页
		PageHelper.startPage(page, rows);

		List<TbContent> contents = this.contentMapper.selectByExample(example);
		// 获取分页信息
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contents);

		EUDataGridResult result = new EUDataGridResult();
		result.setRows(contents);
		// 设置总条目数
		result.setTotal(pageInfo.getTotal());

		return result;
	}

	@Override
	public EgoResult saveContent(TbContent content) {
		// 返回受影响的行数
		int rowCount = this.contentMapper.insert(content);
		if (rowCount == 1) {
			return EgoResult.ok();
		}
		return EgoResult.build(500, "保存失败");
	}

	@Override
	public EgoResult removeContents(List<Long> ids) {

		int rowCount = 0;
		// 需要发送多条sql
		// for (Long id : ids) {
		// rowCount += this.contentMapper.deleteByPrimaryKey(id);
		// }

		// 执行一条sql
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		rowCount = this.contentMapper.deleteByExample(example);
		if (rowCount == ids.size()) {
			return EgoResult.ok();
		}
		return EgoResult.build(500, "删除失败");
	}

	@Override
	public List<TbContent> getContentList(long categoryId) {

		// 先判断缓存中有没有缓存内容？
		// 如果有缓存内容，将缓存内容获取到，返回给调用者
		// HASH结构存储到redis中
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, categoryId + "");
			if (!StringUtils.isBlank(result)) {
				// 把字符串转换成list
				List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果缓存中没有缓存内容，则到数据库查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contents = this.contentMapper.selectByExampleWithBLOBs(example);

		// 首先将查询到的内容放到缓存中，然后再返回给调用者
		try {
			// 把list转换成字符串
			String cacheString = JsonUtils.objectToJson(contents);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, categoryId + "", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contents;
	}

}
