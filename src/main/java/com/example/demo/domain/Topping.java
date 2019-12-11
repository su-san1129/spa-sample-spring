package com.example.demo.domain;

public class Topping {

	/** トッピングID */
	private Integer id;
	/** トッピング名 */
	private String name;
	/** Mサイズのトッピング価格 */
	private Integer priceM;
	/** Lサイズのトッピング価格 */
	private Integer priceL;

	public Topping(Integer id, String name, Integer priceM, Integer priceL) {
		super();
		this.id = id;
		this.name = name;
		this.priceM = priceM;
		this.priceL = priceL;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}

}
