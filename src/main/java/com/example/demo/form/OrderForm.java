package com.example.demo.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OrderForm {

	/** 注文ID */
	private Integer id;
	/** ユーザーID */
	private Integer userId;
	/** 状態 */
	private Integer status;
	/** 合計金額 */
	private Integer totalPrice;
	/** 注文日 */
	private Date orderDate;
	/** 宛名氏名 */
	private String destinationName;
	/** 宛名Eメール */
	private String destinationEmail;
	/** 宛名郵便番号 */
	private String destinationZipcode;
	/** 宛名住所 */
	private String destinationAddress;
	/** 宛名TEL */
	private String destinationTel;
	/** 配達時間 */
	private String deliveryTime;
	private Integer deliveryHour;
	/** 支払方法 */
	private Integer paymentMethod;

	public LocalDate convertLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate convertDate = LocalDate.parse(date, formatter);
		return convertDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getDeliveryHour() {
		return deliveryHour;
	}

	public void setDeliveryHour(Integer deliveryHour) {
		this.deliveryHour = deliveryHour;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
