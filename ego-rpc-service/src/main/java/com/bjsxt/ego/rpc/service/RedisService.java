package com.bjsxt.ego.rpc.service;

import com.bjsxt.ego.common.pojo.EgoResult;

public interface RedisService {
	/**
	 * 内容缓存同步的方法
	 * 
	 * 如果修改了一个内容分类的信息，则直接调用该方法
	 * 删除该内容分类在键值对INDEX_CONTENT_REDIS_KEY中的
	 * value中的field的值就行了
	 * 
	 * @param contentCid 内容分类ID
	 * @return
	 */
	EgoResult syncContent(long contentCid);
}
