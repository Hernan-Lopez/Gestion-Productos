package com.hernan.gestionproductos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hernan.gestionproductos.domain.ProductRequest;
import com.hernan.gestionproductos.domain.ProductResponse;
import com.hernan.gestionproductos.service.ProductService;

class ProductControllerTest {

	@InjectMocks
	private ProductController productController;

	@Mock
	private ProductService productService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testProductsGet() throws Exception {
		List<ProductResponse> mockResponse = Arrays.asList(new ProductResponse(1L, "Product1", "Category1", 100.0f),
				new ProductResponse(2L, "Product2", "Category2", 200.0f));

		when(productService.getAllProducts(any(UUID.class))).thenReturn(mockResponse);

		ResponseEntity<List<ProductResponse>> response = productController.productsGet();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		verify(productService, times(1)).getAllProducts(any(UUID.class));
	}

	@Test
	void testProductsGet_ExceptionHandling() {
		RuntimeException mockException = new RuntimeException("Error simulado");

		when(productService.getAllProducts(any(UUID.class))).thenThrow(mockException);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			productController.productsGet();
		});

		assertEquals("Error simulado", exception.getMessage());
		verify(productService, times(1)).getAllProducts(any(UUID.class));
	}

	@Test
	void testProductsIdDelete_Success() throws Exception {
		Long productId = 1L;
		when(productService.deleteProduct(eq(productId), any(UUID.class))).thenReturn(true);

		ResponseEntity<Void> response = productController.productsIdDelete(productId);

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(productService, times(1)).deleteProduct(eq(productId), any(UUID.class));
	}

	@Test
	void testProductsIdDelete_NotFound() throws Exception {
		Long productId = 1L;
		when(productService.deleteProduct(eq(productId), any(UUID.class))).thenReturn(false);

		Exception exception = assertThrows(Exception.class, () -> {
			productController.productsIdDelete(productId);
		});

		assertEquals("Producto no encontrado", exception.getMessage());
		verify(productService, times(1)).deleteProduct(eq(productId), any(UUID.class));
	}

	@Test
	void testProductsIdGet_Success() throws Exception {
		Long productId = 1L;
		ProductResponse mockResponse = new ProductResponse(productId, "Product1", "Category1", 100.0f);

		when(productService.getProductById(eq(productId), any(UUID.class))).thenReturn(mockResponse);

		ResponseEntity<ProductResponse> response = productController.productsIdGet(productId);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockResponse, response.getBody());
		verify(productService, times(1)).getProductById(eq(productId), any(UUID.class));
	}

	@Test
	void testProductsIdPut_Success() throws Exception {
		Long productId = 1L;
		ProductRequest mockRequest = new ProductRequest("Updated Product", "Category1", 150.0f);
		ProductResponse mockResponse = new ProductResponse(productId, "Updated Product", "Category1", 150.0f);

		when(productService.updateProductById(eq(productId), eq(mockRequest), any(UUID.class))).thenReturn(true);
		when(productService.getProductById(eq(productId), any(UUID.class))).thenReturn(mockResponse);

		ResponseEntity<ProductResponse> response = productController.productsIdPut(productId, mockRequest);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockResponse, response.getBody());
		verify(productService, times(1)).updateProductById(eq(productId), eq(mockRequest), any(UUID.class));
		verify(productService, times(1)).getProductById(eq(productId), any(UUID.class));
	}
	
	@Test
	void testProductsIdPut_ExceptionHandling() {
		ProductRequest mockRequest = new ProductRequest("New Product", "Category1", 120.0f);
		Long productId = 1L;
		
		RuntimeException mockException = new RuntimeException("Error simulado");

		when(productService.updateProductById(eq(productId), eq(mockRequest), any(UUID.class))).thenThrow(mockException);


		Exception exception = assertThrows(RuntimeException.class, () -> {
			productController.productsIdPut(productId, mockRequest);
		});

		assertEquals("Error simulado", exception.getMessage());
		verify(productService, times(1)).updateProductById(eq(productId), eq(mockRequest), any(UUID.class));
	}

	@Test
	void testProductsPost_Success() throws Exception {
		ProductRequest mockRequest = new ProductRequest("New Product", "Category1", 120.0f);
		ProductResponse mockResponse = new ProductResponse(1L, "New Product", "Category1", 120.0f);

		when(productService.saveProduct(eq(mockRequest), any(UUID.class))).thenReturn(mockResponse);

		ResponseEntity<ProductResponse> response = productController.productsPost(mockRequest);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(mockResponse, response.getBody());
		verify(productService, times(1)).saveProduct(eq(mockRequest), any(UUID.class));
	}

	@Test
	void testProductsPost_ExceptionHandling() {
		ProductRequest mockRequest = new ProductRequest("New Product", "Category1", 120.0f);

		RuntimeException mockException = new RuntimeException("Error simulado");

		when(productService.saveProduct(eq(mockRequest), any(UUID.class))).thenThrow(mockException);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			productController.productsPost(mockRequest);
		});

		assertEquals("Error simulado", exception.getMessage());
		verify(productService, times(1)).saveProduct(eq(mockRequest), any(UUID.class));
	}

	@Test
	void testProductsSearchGet() throws Exception {
		String searchName = "Product";
		List<ProductResponse> mockResponse = Arrays.asList(new ProductResponse(1L, "Product1", "Category1", 100.0f),
				new ProductResponse(2L, "Product2", "Category2", 200.0f));

		when(productService.searchProduct(eq(searchName), any(UUID.class))).thenReturn(mockResponse);

		ResponseEntity<List<ProductResponse>> response = productController.productsSearchGet(searchName);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockResponse, response.getBody());
		verify(productService, times(1)).searchProduct(eq(searchName), any(UUID.class));
	}

	@Test
	void testProductsSearchGet_ExceptionHandling() {
		String searchName = "Product";
		RuntimeException mockException = new RuntimeException("Error simulado");

		when(productService.searchProduct(eq(searchName), any(UUID.class))).thenThrow(mockException);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			productController.productsSearchGet(searchName);
		});

		assertEquals("Error simulado", exception.getMessage());
		verify(productService, times(1)).searchProduct(eq(searchName), any(UUID.class));
	}

}
