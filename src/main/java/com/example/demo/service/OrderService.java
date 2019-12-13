package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.example.demo.domain.LoginUser;
import com.example.demo.domain.Order;
import com.example.demo.form.OrderForm;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public Order showOrderList(Integer userId, Integer status) {
		return orderRepository.findByUserIdAndStatus(userId, status);
	}

	public void order(OrderForm form, @AuthenticationPrincipal LoginUser loginUser) {
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		Order insertOrder = showOrderList(loginUser.getUser().getId(), 0);
		order.setTotalPrice(insertOrder.getTotalPrice());
		LocalDate localDate = form.convertLocalDate(form.getDeliveryTime());
		LocalDateTime localDateTime = LocalDateTime.of(
				localDate.getYear()
				, localDate.getMonthValue()
				, localDate.getDayOfMonth()
				, form.getDeliveryHour()
				, 0
				);
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		order.setDeliveryTime(timestamp);

		if (order.getPaymentMethod() == 1) {
			order.setStatus(1);
		} else {
			order.setStatus(2);
		}
		order.setOrderDate(new Date());
		order.setUserId(loginUser.getUser().getId());
		order.setId(insertOrder.getId());
		orderRepository.save(order);
	}

	public OrderForm setOrderForm(@AuthenticationPrincipal LoginUser loginUser) {
		OrderForm form = new OrderForm();
		form.setDestinationName(loginUser.getUser().getName());
		form.setDestinationEmail(loginUser.getUser().getEmail());
		form.setDestinationAddress(loginUser.getUser().getAddress());
		form.setDestinationTel(loginUser.getUser().getTelephone());
		form.setDestinationZipcode(loginUser.getUser().getZipcode());
		return form;
	}

}
