package com.bjsxt.ego.rpc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.rpc.dao.JedisClient;
import com.bjsxt.ego.rpc.mapper.TbOrderItemMapper;
import com.bjsxt.ego.rpc.mapper.TbOrderMapper;
import com.bjsxt.ego.rpc.mapper.TbOrderShippingMapper;
import com.bjsxt.ego.rpc.pojo.TbOrder;
import com.bjsxt.ego.rpc.pojo.TbOrderItem;
import com.bjsxt.ego.rpc.pojo.TbOrderShipping;
import com.bjsxt.ego.rpc.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_ID}")
	private Long ORDER_INIT_ID;
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;
	@Value("${ORDER_DETAIL_INIT_ID}")
	private Long ORDER_DETAIL_INIT_ID;

	@Override
	public EgoResult createOrder(TbOrder order, List<TbOrderItem> items,
			TbOrderShipping orderShipping) {
		// 获取键值对的值
		String string = jedisClient.get(ORDER_GEN_KEY);
		// 如果键值对的值为空，则初始化键值对
		if (StringUtils.isBlank(string)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID + "");
		}
		long orderId = jedisClient.incr(ORDER_GEN_KEY);

		// 补全pojo的属性
		order.setOrderId(orderId + "");
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		// 0：未评价 1：已评价
		order.setBuyerRate(0);
		// 向订单表插入数据
		orderMapper.insert(order);
		// 插入订单明细
		for (TbOrderItem tbOrderItem : items) {
			// 补全订单明细
			// 取订单明细id
			String detailIdStr = this.jedisClient.get(ORDER_DETAIL_GEN_KEY);
			// 如果键值对的值为空，则初始化键值对
			if (StringUtils.isBlank(detailIdStr)) {
				jedisClient.set(ORDER_DETAIL_GEN_KEY, ORDER_DETAIL_INIT_ID + "");
			}
			
			long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			tbOrderItem.setId(orderDetailId + "");
			tbOrderItem.setOrderId(orderId + "");
			// 向订单明细插入记录
			orderItemMapper.insert(tbOrderItem);
		}
		// 插入物流表
		// 补全物流表的属性
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);

		return EgoResult.ok(orderId);
	}
}
