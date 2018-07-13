package com.bjsxt.ego.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.CookieUtils;
import com.bjsxt.ego.rpc.pojo.TbUser;
import com.bjsxt.ego.rpc.service.UserService;

/**
 * 当用户访问某个路径的时候，判断该用户是否登录
 * 如果登录了，就直接访问
 * 否则，跳转到SSO的登录页面，在登录成功之后，再跳转到要访问的页面
 * @author Administrator
 *
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userServiceProxy;
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_PAGE_LOGIN}")
	private String SSO_PAGE_LOGIN;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//从cookie中获取token
		String token = CookieUtils.getCookieValue(request, "EGO_TOKEN");
		
		EgoResult result = this.userServiceProxy.getUserByToken(token);
		TbUser user = (TbUser) result.getData();
		
		if (user != null) {
			request.setAttribute("currentUser", user);
			return true;
		}
		//跳转到登录页面
		response.sendRedirect(this.SSO_BASE_URL + this.SSO_PAGE_LOGIN 
				+ "?redirect=" + request.getRequestURL());
		return false;
	}
	
}
