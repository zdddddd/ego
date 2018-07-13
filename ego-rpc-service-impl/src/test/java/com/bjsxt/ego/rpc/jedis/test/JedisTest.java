package com.bjsxt.ego.rpc.jedis.test;

import java.util.HashSet;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJedisSingle() {
		// 创建一个jedis的对象。
		Jedis jedis = new Jedis("192.168.10.12", 6379);
		// 调用jedis对象的方法，方法名称和redis的命令一致。
		jedis.set("key1", "jedis test");
		String string = jedis.get("key1");
		System.out.println(string);
		// 关闭jedis。
		jedis.close();
	}

	/**
	 * 使用连接池
	 */
	@Test
	public void testJedisPool() {
		// 创建jedis连接池
		JedisPool pool = new JedisPool("192.168.10.12", 6379);
		// 从连接池中获得Jedis对象
		Jedis jedis = pool.getResource();
		String string = jedis.get("hello");
		System.out.println(string);
		// 关闭jedis对象
		jedis.close();
		pool.close();
	}

	@Test
	public void testJedisCluster() {
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.10.12", 7001));
		nodes.add(new HostAndPort("192.168.10.12", 7002));
		nodes.add(new HostAndPort("192.168.10.12", 7003));
		nodes.add(new HostAndPort("192.168.10.12", 7004));
		nodes.add(new HostAndPort("192.168.10.12", 7005));
		nodes.add(new HostAndPort("192.168.10.12", 7006));
		//创建集群连接客户端         使用HashSet集合指定集群中所有的节点，包括主备节点
		JedisCluster cluster = new JedisCluster(nodes);

		cluster.set("key1", "1000");
		String string = cluster.get("key1");
		System.out.println(string);
		//关闭集群客户端连接
		cluster.close();
	}

}
