package com.hernan.gestionproductos.service;

import java.util.List;
import java.util.UUID;

import com.hernan.gestionproductos.domain.ProductRequest;
import com.hernan.gestionproductos.domain.ProductResponse;

public interface ProductService {

	public ProductResponse getProductById(Long id, UUID uuid);

	public Boolean updateProductById(Long id, ProductRequest updatedProduct, UUID uuid);

	public boolean deleteProduct(Long id, UUID uuid);

	public List<ProductResponse> getAllProducts(UUID uuid);

	public ProductResponse saveProduct(ProductRequest product, UUID uuid);

	public List<ProductResponse> searchProduct(String productName, UUID uuid);

}
