package com.example.demo.form;

import java.util.List;

/**
 * クライアントサイドからJSON形式のデータを受け取るオーダートッピングクラス.
 * 
 * @author takahiro.suzuki
 *
 */
public class RequestOrderTopping {

	private List<Integer> orderToppings;

	private Integer orderItemId;

	@Override
	public String toString() {
		return "RequestOrderTopping [orderToppings=" + orderToppings + ", orderItemId=" + orderItemId + "]";
	}

	public List<Integer> getOrderToppings() {
		return orderToppings;
	}

	public void setOrderToppings(List<Integer> orderToppings) {
		this.orderToppings = orderToppings;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

}
