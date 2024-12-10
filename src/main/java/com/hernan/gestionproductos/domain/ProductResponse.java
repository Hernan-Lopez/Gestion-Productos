package com.hernan.gestionproductos.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

public class ProductResponse {

	@JsonProperty("id")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	@JsonSetter(nulls = Nulls.FAIL)
	private Long id;

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

	public ProductResponse() {
		super();
	}

	public ProductResponse(Long id, String name, String category, Float price) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + "]";
	}

}
