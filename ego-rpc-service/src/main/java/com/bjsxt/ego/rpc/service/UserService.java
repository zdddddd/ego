package com.bjsxt.ego.rpc.service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbUser;

public interface UserService {
	
	/**
	 * 判断用户数据是否可用
	 * @param content 要判断的内容
	 * @param type 1：用户名；2：电话号码；3：邮箱
	 * @return EgoResult的data：true表示数据可用，false表示不可用
	 */
	EgoResult checkData(String content, Integer type);
	
	/**
	 * 保存用户的数据
	 * @param user
	 * @return
	 */
	EgoResult createUser(TbUser user);
	
	/**
	 * 用户登录的方法，用户登录成功后，需要将用户信息放到redis中同时设置redis中键值对的过期时间
	 * 
	 * @param username
	 * @param password
	 * @return redis中键值对的key，也就是生成的UUID类型的token
	 */
	EgoResult userLogin(String username, String password);
	
	/**
	 * 根据用户的token查询用户信息
	 * @param token
	 * @return
	 */
	EgoResult getUserByToken(String token);
	
}
