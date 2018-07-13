package com.bjsxt.ego.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.ExceptionUtil;
import com.bjsxt.ego.rpc.pojo.TbUser;
import com.bjsxt.ego.sso.service.SSOUserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private SSOUserService ssoUserService;

	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,
			@PathVariable Integer type, String callback) {
		EgoResult result = null;
		if (type != 1 && type != 2 && type != 3) {
			result = EgoResult.build(400, "校验内容类型错误");
		}
		// 校验出错
		if (null != result) {
			if (null != callback) {// jsonp请求 callback就是js函数的名称
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
						result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
				/*
				 * callbackName({"keya":"valuea","keyb":"valueb"});
				 */
			} else {
				return result;
			}
		}
		// 调用服务
		try {
			result = this.ssoUserService.checkData(param, type);
		} catch (Exception e) {
			result = EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		if (null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

	// 创建用户
	@RequestMapping("/register")
	@ResponseBody
	public EgoResult createUser(TbUser user) {

		try {
			EgoResult result = this.ssoUserService.createUser(user);
			return result;
		} catch (Exception e) {
			return EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	// 用户登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public EgoResult userLogin(
			HttpServletRequest req,
			HttpServletResponse resp,
			String username, String password) {
		try {
			EgoResult result = this.ssoUserService.userLogin(
					req, resp, username, password);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		EgoResult result = null;
		try {
			result = this.ssoUserService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		// 判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}

	}

}
