package com.bjsxt.ego.item.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.item.pojo.CartItem;
import com.bjsxt.ego.item.service.ItemCartService;

@Controller
@RequestMapping("/cart")
public class ItemCartController {

	@Autowired
	private ItemCartService itemCartService;

	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId,
			@RequestParam(defaultValue = "1", required = false) Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		itemCartService.addCartItem(itemId, num, request, response);
		return "cartSuccess";
	}

	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<CartItem> list = this.itemCartService.getCartItemList(request,
				response);
		model.addAttribute("cartList", list);
		return "cart";
	}

	@RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateCartNumber(@PathVariable Long itemId,
			@PathVariable Integer num, HttpServletRequest req,
			HttpServletResponse resp) {

		EgoResult result = this.itemCartService.updateCartNumber(req, resp,
				itemId, num);

		return "OK";
	}

	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
			HttpServletRequest request, HttpServletResponse response) {
		this.itemCartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}

}
