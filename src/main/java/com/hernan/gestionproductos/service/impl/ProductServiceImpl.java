package com.hernan.gestionproductos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hernan.gestionproductos.domain.ErrorResponse;
import com.hernan.gestionproductos.domain.ProductRequest;
import com.hernan.gestionproductos.domain.ProductResponse;
import com.hernan.gestionproductos.entity.ProductEntity;
import com.hernan.gestionproductos.execption.ProductException;
import com.hernan.gestionproductos.mapper.ProductMapper;
import com.hernan.gestionproductos.repository.ProductRepository;
import com.hernan.gestionproductos.service.ProductService;
import com.hernan.gestionproductos.service.StatisticService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	StatisticService statisticService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ProductResponse getProductById(Long id, UUID uuid) {
		LOGGER.info("INIT getProductById [id={}] [{}]", id, uuid);
		try {
			ProductEntity entity = findProductOrThrow(id);
			ProductResponse response = ProductMapper.entityToProductResponse(entity);
			LOGGER.info("END getProductById [response={}] [{}]", response, uuid);
			
			statisticService.updateStatistics(entity.getCategory(), 1);
			
			return response;
		} catch (Exception e) {
			LOGGER.error("EXCEPTION getProductById [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Override
	@Transactional
	public Boolean updateProductById(Long id, ProductRequest updatedProduct, UUID uuid) {
		LOGGER.info("INIT updateProductById [id={}, product={}] [{}]", id, updatedProduct, uuid);
		try {
			ProductEntity entityBeforeUpdating = findProductOrThrow(id);

			ProductEntity productEntityToUpdate = ProductMapper.productRequestToEntity(updatedProduct);
			productEntityToUpdate.setId(id);
			int updateRows = productRepository.update(productEntityToUpdate.getId(), productEntityToUpdate.getName(),
					productEntityToUpdate.getCategory(), productEntityToUpdate.getPrice());

			if (updateRows > 0) {
				entityManager.flush();
				entityManager.clear();
				LOGGER.info("END updateProductById [updated=true] [{}]", uuid);
				
				if(productEntityToUpdate.getCategory()!=null) {
					statisticService.updateStatistics(entityBeforeUpdating.getCategory(), -1);
			        statisticService.updateStatistics(productEntityToUpdate.getCategory(), 1);
				}
				
				return true;
			} else {
				throw new ProductException(
						new ErrorResponse(400, "Bad Request", "Warning", "Error al actualizar producto."),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			LOGGER.error("EXCEPTION updateProductById [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Override
	public boolean deleteProduct(Long id, UUID uuid) {
		LOGGER.info("INIT deleteProduct [id={}] [{}]", id, uuid);
		try {
			ProductEntity toDelete = findProductOrThrow(id);
			productRepository.deleteById(id);
			Optional<ProductEntity> entity = productRepository.findById(id);
			boolean isDeleted = !entity.isPresent();
			LOGGER.info("END deleteProduct [deleted={}] [{}]", isDeleted, uuid);
			
			if(isDeleted) {
				statisticService.updateStatistics(toDelete.getCategory(), -1);
			}
			
			return isDeleted;
		} catch (Exception e) {
			LOGGER.error("EXCEPTION deleteProduct [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Override
	public List<ProductResponse> getAllProducts(UUID uuid) {
		LOGGER.info("INIT getAllProducts [] [{}]", uuid);
		try {
			List<ProductEntity> productList = productRepository.findAll();
			List<ProductResponse> result = new ArrayList<>();
			for (ProductEntity productEntity : productList) {
				result.add(ProductMapper.entityToProductResponse(productEntity));
			}
			LOGGER.info("END getAllProducts [size={}] [{}]", result.size(), uuid);
			return result;
		} catch (Exception e) {
			LOGGER.error("EXCEPTION getAllProducts [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Override
	public ProductResponse saveProduct(ProductRequest product, UUID uuid) {
		LOGGER.info("INIT saveProduct [product={}] [{}]", product, uuid);
		try {
			ProductEntity productEntityToSave = ProductMapper.productRequestToEntity(product);
			ProductEntity productSaved = productRepository.save(productEntityToSave);
			if (productSaved != null) {
				ProductResponse response = ProductMapper.entityToProductResponse(productSaved);
				LOGGER.info("END saveProduct [response={}] [{}]", response, uuid);
				return response;
			} else {
				throw new ProductException(new ErrorResponse(400, "Bad Request", "Warning", "Error al crear producto"),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			LOGGER.error("EXCEPTION saveProduct [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Override
	public List<ProductResponse> searchProduct(String productName, UUID uuid) {
		LOGGER.info("INIT searchProduct [productName={}] [{}]", productName, uuid);
		try {
			List<ProductEntity> list = productRepository.findByName(productName);
			List<ProductResponse> result = new ArrayList<>();
			for (ProductEntity productEntity : list) {
				result.add(ProductMapper.entityToProductResponse(productEntity));
			}
			LOGGER.info("END searchProduct [size={}] [{}]", result.size(), uuid);
			return result;
		} catch (Exception e) {
			LOGGER.error("EXCEPTION searchProduct [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	private ProductEntity findProductOrThrow(Long id) {
		return productRepository.findById(id).orElseThrow(() -> {
			ErrorResponse error = new ErrorResponse(404, "Not Found", "Error", "Producto no encontrado.");
			LOGGER.error("EXCEPTION findProductOrThrow [{}]", error);
			return new ProductException(error, HttpStatus.NOT_FOUND);
		});
	}

}
