package com.bjsxt.ego.sso.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.CookieUtils;
import com.bjsxt.ego.rpc.pojo.TbUser;
import com.bjsxt.ego.rpc.service.UserService;
import com.bjsxt.ego.sso.service.SSOUserService;

@Service
public class SSOUserServiceImpl implements SSOUserService {
	@Autowired
	private UserService userServiceProxy;

	@Override
	public EgoResult checkData(String content, Integer type) {
		// 调用远程dubbo服务代理对象的方法
		return this.userServiceProxy.checkData(content, type);
	}

	@Override
	public EgoResult createUser(TbUser user) {
		return this.userServiceProxy.createUser(user);
	}

	@Override
	public EgoResult userLogin(HttpServletRequest req,
			HttpServletResponse resp, String username, String password) {
		EgoResult result = this.userServiceProxy.userLogin(username, password);
		// 从EgoResult中获取token数据
		String token = (String) result.getData();
		//将token写入到cookie，并且让cookie在系统之间共享
		CookieUtils.setCookie(req, resp, "EGO_TOKEN", token);

		return result;
	}

	@Override
	public EgoResult getUserByToken(String token) {
		return this.userServiceProxy.getUserByToken(token);
	}

}
