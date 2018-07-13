package com.bjsxt.ego.item.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.item.pojo.CartItem;

public interface ItemCartService {

	/**
	 * 将商品添加到购物车的方法
	 * 
	 * @param itemId
	 *            商品的ID
	 * @param num
	 *            商品的数量
	 * @param request
	 * @param response
	 * @return
	 */
	EgoResult addCartItem(long itemId, int num, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 获取购物车列表信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	List<CartItem> getCartItemList(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 更改购物车中商品的数量
	 * 
	 * @param req
	 * @param resp
	 * @param itemId
	 *            商品ID
	 * @param num
	 *            新的商品数量
	 * @return
	 */
	EgoResult updateCartNumber(HttpServletRequest req,
			HttpServletResponse resp, long itemId, int num);

	/**
	 * 删除购物车商品信息的方法，需要将新的购物车商品信息放到cookie中
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	EgoResult deleteCartItem(long itemId, HttpServletRequest request,
			HttpServletResponse response);

}
