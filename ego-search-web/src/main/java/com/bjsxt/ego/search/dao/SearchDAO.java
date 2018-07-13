package com.bjsxt.ego.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.bjsxt.ego.search.pojo.SearchResult;

public interface SearchDAO {
	
	/**
	 * 获取查询结果
	 * @param query 使用SolrQuery封装查询参数
	 * @return SearchResult中封装了查询结果：记录集合、当前页码、查询到的总页数、查询到的总记录数
	 * @throws Exception
	 */
	SearchResult search(SolrQuery query) throws Exception;
	
}
