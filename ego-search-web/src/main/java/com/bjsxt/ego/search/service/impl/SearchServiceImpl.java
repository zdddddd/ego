package com.bjsxt.ego.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.search.dao.SearchDAO;
import com.bjsxt.ego.search.pojo.SearchResult;
import com.bjsxt.ego.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDAO searchDAO;

	@Override
	public SearchResult search(String queryString, int page, int rows)
			throws Exception {

		// 创建一个查询对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 设置开始条目数字
		query.setStart((page - 1) * rows);
		// 设置每页显示多少条记录
		query.setRows(rows);
		// 设置是否使用高亮
		query.setHighlight(true);
		// 高亮的前置字符串
		query.setHighlightSimplePre("<span style='color:red; font-weight:bold'>");
		// 高亮的后置字符串
		query.setHighlightSimplePost("</span>");
		// 设置默认字段用于搜索
		query.set("df", "item_keywords");
		// 设置添加高亮的字段
		query.addHighlightField("item_title");

		SearchResult result = this.searchDAO.search(query);
		//设置当前页
		result.setCurPage(page);
		
		long total = result.getRecordCount();
		
		double pageCount = Math.ceil(total * 1.0 / rows);
		//设置一共多少页
		result.setPageCount((long) pageCount);
		
		return result;
	}

}
