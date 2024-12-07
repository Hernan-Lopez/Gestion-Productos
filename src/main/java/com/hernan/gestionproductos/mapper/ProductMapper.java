package com.hernan.gestionproductos.mapper;

import com.hernan.gestionproductos.domain.ProductRequest;
import com.hernan.gestionproductos.domain.ProductResponse;
import com.hernan.gestionproductos.entity.ProductEntity;

public class ProductMapper {

	public static ProductEntity productRequestToEntity(ProductRequest product) {
		ProductEntity entity = new ProductEntity();
		entity.setCategory(product.getCategory());
		entity.setName(product.getName());
		entity.setPrice(product.getPrice());
		return entity;
	}

	public static ProductResponse entityToProductResponse(ProductEntity entity) {
		ProductResponse product = new ProductResponse();
		product.setCategory(entity.getCategory());
		product.setId(entity.getId());
		product.setName(entity.getName());
		product.setPrice(entity.getPrice());
		return product;
	}

}
