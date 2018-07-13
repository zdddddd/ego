package com.bjsxt.ego.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.CookieUtils;
import com.bjsxt.ego.common.util.JsonUtils;
import com.bjsxt.ego.order.pojo.CartItem;
import com.bjsxt.ego.order.pojo.Order;
import com.bjsxt.ego.order.service.OrderOrderService;
import com.bjsxt.ego.rpc.pojo.TbOrder;
import com.bjsxt.ego.rpc.pojo.TbOrderItem;
import com.bjsxt.ego.rpc.pojo.TbOrderShipping;
import com.bjsxt.ego.rpc.service.OrderService;

@Service
public class OrderOrderServiceImpl implements OrderOrderService {
	
	@Autowired
	private OrderService orderServiceProxy;
	
	/**
	 * 从cookie中取商品列表
	 * @return
	 */
	public List<CartItem> getCartItemList(HttpServletRequest request) {

		// 从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "EGO_CART", true);
		// 如果cookie中没有存放购物车信息
		if (cartJson == null) {
			return new ArrayList<>();
		}
		// 把json转换成商品列表
		try {
			List<CartItem> list = JsonUtils
					.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public EgoResult createOrder(Order order, List<TbOrderItem> orderItems,
			TbOrderShipping orderShipping) {
		
		TbOrder tbOrder = new TbOrder();
		tbOrder.setBuyerMessage(order.getBuyerMessage());
		tbOrder.setBuyerNick(order.getBuyerNick());
		tbOrder.setBuyerRate(order.getBuyerRate());
		tbOrder.setCloseTime(order.getCloseTime());
		tbOrder.setConsignTime(order.getConsignTime());
		tbOrder.setCreateTime(order.getCreateTime());
		tbOrder.setEndTime(order.getEndTime());
		tbOrder.setOrderId(order.getOrderId());
		tbOrder.setPayment(order.getPayment());
		tbOrder.setPaymentTime(order.getPaymentTime());
		tbOrder.setPaymentType(order.getPaymentType());
		tbOrder.setPostFee(order.getPostFee());
		tbOrder.setShippingCode(order.getShippingCode());
		tbOrder.setShippingName(order.getShippingName());
		tbOrder.setStatus(order.getStatus());
		tbOrder.setUpdateTime(order.getUpdateTime());
		tbOrder.setUserId(order.getUserId());
		
		return this.orderServiceProxy.createOrder(tbOrder, orderItems, orderShipping);
	}

}
