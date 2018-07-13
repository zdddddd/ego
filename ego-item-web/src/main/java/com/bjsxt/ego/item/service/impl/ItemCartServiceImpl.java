package com.bjsxt.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.CookieUtils;
import com.bjsxt.ego.common.util.JsonUtils;
import com.bjsxt.ego.item.pojo.CartItem;
import com.bjsxt.ego.item.service.ItemCartService;
import com.bjsxt.ego.rpc.pojo.TbItem;
import com.bjsxt.ego.rpc.service.ItemService;

@Service
public class ItemCartServiceImpl implements ItemCartService {

	@Autowired
	private ItemService itemServiceProxy;

	@Override
	public EgoResult addCartItem(long itemId, int num,
			HttpServletRequest request, HttpServletResponse response) {

		// 取商品信息
		CartItem cartItem = null;
		// 取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 判断购物车商品列表中是否存在此商品
		for (CartItem cItem : itemList) {
			// 如果存在此商品
			if (cItem.getId() == itemId) {
				// 增加商品数量
				cItem.setNum(cItem.getNum() + num);
				cartItem = cItem;
				break;
			}
		}
		// 如果cartItem==null表示在购物车中没有找到要添加的商品信息
		if (cartItem == null) {
			cartItem = new CartItem();
			// 根据商品id调用远程dubbo服务，查询商品基本信息。
			TbItem item = (TbItem) this.itemServiceProxy
					.getItemBaseInfo(itemId).getData();

			cartItem.setId(item.getId());
			cartItem.setTitle(item.getTitle());
			cartItem.setImage(item.getImage() == null ? "" : item.getImage()
					.split(",")[0]);
			cartItem.setNum(num);
			cartItem.setPrice(item.getPrice());
			// 添加到购物车列表
			itemList.add(cartItem);
		}
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "EGO_CART",
				JsonUtils.objectToJson(itemList), true);

		return EgoResult.ok();

	}

	/**
	 * 从cookie中取商品列表
	 * <p>
	 * Title: getCartItemList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {

		// Cookie[] cks = request.getCookies();
		//
		// Cookie ck0 = null;
		//
		// for (Cookie ck : cks) {
		// if ("EGO_CART".equals(ck.getName())) {
		// ck0 = ck;
		// break;
		// }
		// }
		//
		// String cartJson = ck0.getValue();
		// cartJson = new String(cartJson.getBytes(), "utf-8");

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
	public List<CartItem> getCartItemList(HttpServletRequest request,
			HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}

	@Override
	public EgoResult updateCartNumber(HttpServletRequest req,
			HttpServletResponse resp, long itemId, int num) {
		// 获取cookie中的购物车列表信息
		List<CartItem> items = getCartItemList(req);

		for (CartItem ci : items) {
			if (ci.getId() == itemId) {
				// 将该记录的数量设置为新的数量
				ci.setNum(num);
				break;
			}
		}
		// 将更新后的数据放到cookie中
		CookieUtils.setCookie(req, resp, "EGO_CART",
				JsonUtils.objectToJson(items), true);

		return EgoResult.ok();
	}

	@Override
	public EgoResult deleteCartItem(long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		// /从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 从列表中找到此商品
		for (CartItem cartItem : itemList) {
			if (cartItem.getId() == itemId) {
				itemList.remove(cartItem);
				//在迭代List集合的时候，不要随便删除元素，因为会改变迭代器的状态
				//但是如果删除之后直接退出迭代的话是没有问题的。
				break;
			}
		}
		// 把购物车列表重新写入cookie
		CookieUtils.setCookie(request, response, "EGO_CART", JsonUtils.objectToJson(itemList), true);

		return EgoResult.ok();
	}
}
