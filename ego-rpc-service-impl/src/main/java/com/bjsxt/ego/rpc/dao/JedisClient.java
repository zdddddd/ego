package com.bjsxt.ego.rpc.dao;

public interface JedisClient {
	
	String get(String key);

	String set(String key, String value);

	String hget(String hkey, String key);

	long hset(String hkey, String key, String value);

	long incr(String key);

	long expire(String key, int second);

	long ttl(String key);
	
	long hdel(String key, String field);
}
