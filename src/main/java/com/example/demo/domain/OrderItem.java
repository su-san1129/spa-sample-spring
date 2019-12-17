package com.example.demo.domain;

import java.util.List;

/**
 * 注文商品情報を格納するクラス.
 * 
 * @author takahiro.suzuki
 *
 */
public class OrderItem {

	/** 注文商品ID */
	private Integer id;
	/** 商品ID */
	private Integer itemId;
	/** 注文ID */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** アイテム */
	private Item item;
	/** オーダートッピングリスト */
	private List<OrderTopping> orderToppingList;

	private Integer subTotal;

	public Integer getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Integer subTotal) {
		this.subTotal = subTotal;
	}

	public OrderItem() {
	}

	public OrderItem(Integer id, Integer itemId, Integer orderId, Integer quantity, Character size, Item item,
			List<OrderTopping> orderToppingList) {
		this.id = id;
		this.itemId = itemId;
		this.orderId = orderId;
		this.quantity = quantity;
		this.size = size;
		this.item = item;
		this.orderToppingList = orderToppingList;
	}

	public int subTotal() {
		int count = 0;
		if (this.orderToppingList != null) {
			count = this.orderToppingList.size();
		}
		if (this.size == 'M') {
			return this.item.getPriceM() * this.quantity + (200 * count);
		} else {
			return this.item.getPriceL() * this.quantity + (300 * count);
		}
	}

	public int tax() {
		return (int) (this.subTotal() * 0.08);
	}

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

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}

}
