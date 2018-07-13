package com.bjsxt.ego.portal.service;

public interface PortalContentService {
	
	/**
	 * 根据内容分类ID查询该分类下的所有内容列表
	 * @param categoryId
	 * @return
	 */
	String getContentListByCategoryId(long categoryId);
	
}
