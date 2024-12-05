package com.hernan.gestionproductos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hernan.gestionproductos.domain.Product;
import com.hernan.gestionproductos.entity.ProductEntity;
import com.hernan.gestionproductos.repository.ProductRepository;
import com.hernan.gestionproductos.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public Product getProductById(Long id) {
		productRepository.findById(id);
		return null;
	}

	@Override
	public Product updateProductById(Long id, Product updatedProduct) {

		ProductEntity ProductEntityToUpdate = new ProductEntity();

		Optional<ProductEntity> existingProduct = productRepository.findById(id);

		if (existingProduct.isPresent()) {
			ProductEntity product = existingProduct.get();
			product.setName(ProductEntityToUpdate.getName());
			product.setCategory(ProductEntityToUpdate.getCategory());
			product.setPrice(ProductEntityToUpdate.getPrice());
			productRepository.save(product);
		} else {
			throw new RuntimeException("Product not found");
		}
		return null;
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> getAllProducts() {
		productRepository.findAll();
		return null;
	}

	@Override
	public Product saveProduct(Product product) {
		ProductEntity ProductEntityToSave = new ProductEntity();
		productRepository.save(ProductEntityToSave);
		return null;
	}

	@Override
	public List<Product> searchProduct(String productName) {
		productRepository.findByName(productName);
		return null;
	}

}
