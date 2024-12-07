package com.hernan.gestionproductos.domain;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

public class ProductRequest   {

	@JsonProperty("name")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	@JsonSetter(nulls = Nulls.FAIL)
	private String name;

	@JsonProperty("category")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	@JsonSetter(nulls = Nulls.FAIL)
	private String category;

	@JsonProperty("price")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	@JsonSetter(nulls = Nulls.FAIL)
	private Float price;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", category=" + category + ", price=" + price + "]";
	}

}
