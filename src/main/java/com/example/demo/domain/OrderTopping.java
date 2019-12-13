package com.example.demo.domain;

/**
 * 注文商品情報を格納するクラス.
 * 
 * @author takahiro.suzuki
 *
 */
public class OrderTopping {

	/** 注文トッピングID */
	private Integer id;
	/** トッピングID */
	private Integer toppingId;
	/** オーダーアイテムID */
	private Integer orderItemId;
	/** トッピングオブジェクト */
	private Topping topping;

	public OrderTopping() {
	}

	public OrderTopping(Integer id, Integer toppingId, Integer orderItemId, Topping topping) {
		this.id = id;
		this.toppingId = toppingId;
		this.orderItemId = orderItemId;
		this.topping = topping;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getToppingId() {
		return toppingId;
	}

	public void setToppingId(Integer toppingId) {
		this.toppingId = toppingId;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Topping getTopping() {
		return topping;
	}

	public void setTopping(Topping topping) {
		this.topping = topping;
	}

	@Override
	public String toString() {
		return "OrderTopping [id=" + id + ", toppingId=" + toppingId + ", orderItemId=" + orderItemId + ", topping="
				+ topping + "]";
	}

}
