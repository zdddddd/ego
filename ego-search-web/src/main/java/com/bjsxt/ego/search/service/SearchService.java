package com.bjsxt.ego.search.service;

import com.bjsxt.ego.search.pojo.SearchResult;

public interface SearchService {
	
	/**
	 * 使用solr查询
	 * @param queryString 查询字符串（用户输入的）
	 * @param page 页码
	 * @param rows 每页多少条记录
	 * @return
	 * @throws Exception
	 */
	SearchResult search(String queryString, int page, int rows) throws Exception;
	
}
