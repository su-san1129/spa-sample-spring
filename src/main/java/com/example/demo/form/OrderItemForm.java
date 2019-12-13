package com.example.demo.form;

import java.util.List;

import com.example.demo.domain.Item;

public class OrderItemForm {

	private Integer id;
	private Integer itemId;
	private Integer orderId;
	private Integer quantity;
	private Character size;
	private Item item;
	private List<Integer> orderToppingList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Integer> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<Integer> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItemFrom [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}

}
