package com.bjsxt.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.order.pojo.CartItem;
import com.bjsxt.ego.order.pojo.Order;
import com.bjsxt.ego.rpc.pojo.TbOrderItem;
import com.bjsxt.ego.rpc.pojo.TbOrderShipping;

public interface OrderOrderService {

	/**
	 * 从cookie中获取购物车商品列表信息
	 * 
	 * @return
	 */
	List<CartItem> getCartItemList(HttpServletRequest request);

	/**
	 * 本地下订单的方法，需要调用dubbo发布的远程服务代理对象来完成
	 * @param order 订单信息
	 * @param orderItems 订单中的商品信息列表
	 * @param orderShipping 物流信息
	 * @return 返回值中包含了订单ID
	 */
	EgoResult createOrder(Order order, List<TbOrderItem> orderItems,
			TbOrderShipping orderShipping);

}
