package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.LoginUser;
import com.example.demo.domain.Order;
import com.example.demo.form.OrderForm;
import com.example.demo.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@ModelAttribute
	private OrderForm setUpOrderForm() {
		return new OrderForm();
	}

	/**
	 * 注文確認画面を表示.
	 * 
	 * @return 注文確認画面
	 */
	@RequestMapping("/order_confirm")
	public String toOrder(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = loginUser.getUser().getId();
		Order order = orderService.showOrderList(userId, 0);
		int[] deliveryHour = new int[11];
		for (int i = 0; i < 11; i++) {
			deliveryHour[i] += (i + 10);
		}
		model.addAttribute("deliveryHour", deliveryHour);
		if (order != null && !order.getOrderItemList().isEmpty()) {
			model.addAttribute("order", order);
		}
		model.addAttribute("orderForm", orderService.setOrderForm(loginUser));
		return "order_confirm";
	}

	/**
	 * 注文完了画面を表示.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/order_finished")
	public String toOrderFinished() {

		return "order_finished";
	}

	@RequestMapping("/order")
	public String order(OrderForm form, @AuthenticationPrincipal LoginUser loginUser) {
		orderService.order(form, loginUser);
		return "order_finished";
	}

}
