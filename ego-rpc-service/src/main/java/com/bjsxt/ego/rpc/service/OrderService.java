package com.bjsxt.ego.rpc.service;

import java.util.List;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.pojo.TbOrder;
import com.bjsxt.ego.rpc.pojo.TbOrderItem;
import com.bjsxt.ego.rpc.pojo.TbOrderShipping;

public interface OrderService {

	/**
	 * 创建订单的方法
	 * 
	 * @param order
	 *            订单信息
	 * @param items
	 *            订单中购买的商品信息列表
	 * @param orderShipping
	 *            物流信息
	 * @return EgoResult的data属性返回订单编号
	 */
	EgoResult createOrder(TbOrder order, List<TbOrderItem> items,
			TbOrderShipping orderShipping);

}
