package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.LoginUser;
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
	public String toOrder() {
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
