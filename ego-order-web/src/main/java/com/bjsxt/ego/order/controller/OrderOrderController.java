package com.bjsxt.ego.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.order.pojo.CartItem;
import com.bjsxt.ego.order.pojo.Order;
import com.bjsxt.ego.order.service.OrderOrderService;
import com.bjsxt.ego.rpc.pojo.TbUser;

@Controller
public class OrderOrderController {

	@Autowired
	private OrderOrderService orderOrderService;

	@RequestMapping(value = "order-cart")
	public String showOrderCart(Model model, HttpServletRequest req) {
		// 从cookie中获取购物车商品列表信息，放到页面上
		List<CartItem> cartItems = this.orderOrderService.getCartItemList(req);
		model.addAttribute("cartList", cartItems);
		return "order-cart";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createOrder(Order order, Model model, HttpServletRequest req) {
		//获取拦截器放到req中的当前登录的用户
		TbUser user = (TbUser) req.getAttribute("currentUser");
		//将当前登录的用户ID设置给order
		order.setUserId(user.getId());
		
		EgoResult result = this.orderOrderService.createOrder(order,
				order.getOrderItems(), order.getOrderShipping());
		model.addAttribute("orderId", result.getData());
		return "success";
	}

}
