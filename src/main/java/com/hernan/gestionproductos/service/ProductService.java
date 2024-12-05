package com.hernan.gestionproductos.service;

import java.util.List;

import com.hernan.gestionproductos.domain.Product;

public interface ProductService {

	public Product getProductById(Long id);

	public Product updateProductById(Long id, Product updatedProduct);

	public void deleteProduct(Long id);

	public List<Product> getAllProducts();

	public Product saveProduct(Product product);

	public List<Product> searchProduct(String productName);

}
