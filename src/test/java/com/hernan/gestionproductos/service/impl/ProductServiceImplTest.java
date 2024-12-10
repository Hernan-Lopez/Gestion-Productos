package com.hernan.gestionproductos.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.hernan.gestionproductos.domain.ProductRequest;
import com.hernan.gestionproductos.domain.ProductResponse;
import com.hernan.gestionproductos.entity.ProductEntity;
import com.hernan.gestionproductos.execption.ProductException;
import com.hernan.gestionproductos.mapper.ProductMapper;
import com.hernan.gestionproductos.repository.ProductRepository;
import com.hernan.gestionproductos.service.StatisticService;

import jakarta.persistence.EntityManager;

class ProductServiceImplTest {

	@InjectMocks
	private ProductServiceImpl productService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private StatisticService statisticService;

	@Mock
	private EntityManager entityManager;

	private UUID testUUID;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		testUUID = UUID.randomUUID();
	}

	@Test
	void testGetProductById_Success() {
		Long productId = 1L;
		ProductEntity entity = new ProductEntity();
		entity.setId(productId);
		entity.setName("Test Product");
		when(productRepository.findById(productId)).thenReturn(Optional.of(entity));
		doNothing().when(statisticService).updateStatistics(anyString(), anyInt());

		ProductResponse response = productService.getProductById(productId, testUUID);

		assertNotNull(response);
		assertEquals(productId, response.getId());
		verify(productRepository).findById(productId);
		verify(statisticService).updateStatistics(entity.getCategory(), 1);
	}

	@Test
	void testGetProductById_NotFound() {
		Long productId = 2L;
		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		ProductException exception = assertThrows(ProductException.class, () -> {
			productService.getProductById(productId, testUUID);
		});

		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
	}

	@Test
	void testSaveProduct_Success() {
		ProductRequest request = new ProductRequest();
		request.setName("New Product");

		ProductEntity entity = new ProductEntity();
		entity.setId(1L);
		entity.setName("New Product");

		when(productRepository.save(any(ProductEntity.class))).thenReturn(entity);

		ProductResponse response = productService.saveProduct(request, testUUID);

		assertNotNull(response);
		assertEquals(entity.getId(), response.getId());
		verify(productRepository).save(any(ProductEntity.class));
	}

	@Test
	void testGetAllProducts_Success() {
		ProductEntity product1 = new ProductEntity();
		product1.setId(1L);
		product1.setName("Product 1");

		ProductEntity product2 = new ProductEntity();
		product2.setId(2L);
		product2.setName("Product 2");

		when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

		List<ProductResponse> response = productService.getAllProducts(testUUID);

		assertNotNull(response);
		assertEquals(2, response.size());
		verify(productRepository).findAll();
	}

	@Test
	void testDeleteProduct_Success() {
		Long productId = 1L;
		ProductEntity entity = new ProductEntity();
		entity.setId(productId);

		when(productRepository.findById(productId)).thenReturn(Optional.of(entity));
		doNothing().when(productRepository).deleteById(productId);

		boolean isDeleted = productService.deleteProduct(productId, testUUID);

		assertFalse(isDeleted);
//        verify(productRepository).deleteById(productId);
//        verify(statisticService).updateStatistics(entity.getCategory(), -1);
	}

	@Test
	void testUpdateProductById_Success() {
		Long productId = 1L;
		UUID uuid = UUID.randomUUID();
		ProductRequest updatedProduct = new ProductRequest("Updated Name", "Updated Category", 150.0f);

		ProductEntity existingProduct = new ProductEntity();
		existingProduct.setId(productId);
		existingProduct.setName("Old Name");
		existingProduct.setCategory("Old Category");
		existingProduct.setPrice(100.0f);

		ProductEntity productToUpdate = ProductMapper.productRequestToEntity(updatedProduct);
		productToUpdate.setId(productId);

		when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(existingProduct));
		when(productRepository.update(productToUpdate.getId(), productToUpdate.getName(), productToUpdate.getCategory(),
				productToUpdate.getPrice())).thenReturn(1);

		Boolean result = productService.updateProductById(productId, updatedProduct, uuid);

		assertTrue(result);
		verify(entityManager).flush();
		verify(entityManager).clear();
		verify(statisticService).updateStatistics("Old Category", -1);
		verify(statisticService).updateStatistics("Updated Category", 1);
	}

	@Test
	void testUpdateProductById_NoRowsUpdated() {
		Long productId = 1L;
		UUID uuid = UUID.randomUUID();
		ProductRequest updatedProduct = new ProductRequest("Updated Name", "Updated Category", 150.0f);

		ProductEntity existingProduct = new ProductEntity();
		existingProduct.setId(productId);

		when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(existingProduct));
		when(productRepository.update(anyLong(), anyString(), anyString(), anyFloat())).thenReturn(0);

		ProductException exception = assertThrows(ProductException.class, () -> {
			productService.updateProductById(productId, updatedProduct, uuid);
		});

		assertEquals("Bad Request", exception.getErrorResponse().getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
	}

	@Test
	void testUpdateProductById_EntityNotFound() {
		Long productId = 1L;
		UUID uuid = UUID.randomUUID();
		ProductRequest updatedProduct = new ProductRequest("Updated Name", "Updated Category", 150.0f);

		when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

		ProductException exception = assertThrows(ProductException.class, () -> {
			productService.updateProductById(productId, updatedProduct, uuid);
		});

		assertEquals("Not Found", exception.getErrorResponse().getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
	}
	
	@Test
    void testSearchProduct_Success() {
        // Datos de prueba
        String productName = "Test Product";
        UUID uuid = UUID.randomUUID();

        ProductEntity product1 = new ProductEntity();
        product1.setId(1L);
        product1.setName("Test Product");
        product1.setCategory("Category1");
        product1.setPrice(100.0f);

        ProductEntity product2 = new ProductEntity();
        product2.setId(2L);
        product2.setName("Test Product");
        product2.setCategory("Category2");
        product2.setPrice(200.0f);

        List<ProductEntity> mockProductEntities = Arrays.asList(product1, product2);

        when(productRepository.findByName(productName)).thenReturn(mockProductEntities);

        List<ProductResponse> result = productService.searchProduct(productName, uuid);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Product", result.get(0).getName());
        assertEquals("Category1", result.get(0).getCategory());
        assertEquals(100.0f, result.get(0).getPrice());

        verify(productRepository, times(1)).findByName(productName);
    }

    @Test
    void testSearchProduct_EmptyResult() {
        String productName = "NonExistentProduct";
        UUID uuid = UUID.randomUUID();

        when(productRepository.findByName(productName)).thenReturn(new ArrayList<>());

        List<ProductResponse> result = productService.searchProduct(productName, uuid);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findByName(productName);
    }

    @Test
    void testSearchProduct_ExceptionHandling() {
        String productName = "ErrorProduct";
        UUID uuid = UUID.randomUUID();

        when(productRepository.findByName(productName)).thenThrow(new RuntimeException("Simulated Error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.searchProduct(productName, uuid);
        });

        assertEquals("Simulated Error", exception.getMessage());
        verify(productRepository, times(1)).findByName(productName);
    }
}
