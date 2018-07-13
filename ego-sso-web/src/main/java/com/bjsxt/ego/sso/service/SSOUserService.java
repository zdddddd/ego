package com.bjsxt.ego.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbUser;

public interface SSOUserService {
	/**
	 * 调用远程服务判断用户数据是否有效
	 * @param content
	 * @param type
	 * @return
	 */
	EgoResult checkData(String content, Integer type);
	
	/**
	 * 调用dubbo远程服务代理对象保存用户信息
	 * @param user
	 * @return
	 */
	EgoResult createUser(TbUser user);
	
	/**
	 * 用户登录的方法，调用远程服务
	 * @param username
	 * @param password
	 * @return
	 */
	EgoResult userLogin(HttpServletRequest req, HttpServletResponse resp, String username, String password);
	
	/**
	 * 根据token获取用户信息：调用远程服务
	 * @param token
	 * @return
	 */
	EgoResult getUserByToken(String token);
	
}
