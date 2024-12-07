package com.hernan.gestionproductos.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hernan.gestionproductos.domain.ErrorResponse;
import com.hernan.gestionproductos.domain.ProductRequest;
import com.hernan.gestionproductos.domain.ProductResponse;
import com.hernan.gestionproductos.execption.ProductException;
import com.hernan.gestionproductos.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos recuperada con éxito.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@GetMapping(value = "/products", produces = { "application/json" })
	public ResponseEntity<List<ProductResponse>> productsGet() throws ProductException {
		UUID uuid = UUID.randomUUID();
		LOGGER.info("INIT productsGet [] [{}]", uuid);
		try {
			List<ProductResponse> response = productService.getAllProducts(uuid);
			LOGGER.info("END productsGet [{}]", response.size());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("EXCEPTION productsGet [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Operation(summary = "Eliminar un producto", description = "Elimina un producto existente por su ID.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Producto eliminado con éxito."),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@DeleteMapping(value = "/products/{id}", produces = { "application/json" })
	public ResponseEntity<Void> productsIdDelete(
			@Parameter(in = ParameterIn.PATH, description = "ID del producto a eliminar.", required = true, schema = @Schema()) @PathVariable("id") Long id)
			throws ProductException {
		UUID uuid = UUID.randomUUID();
		LOGGER.info("INIT productsIdDelete [id={}] [{}]", id, uuid);
		try {
			Boolean deleted = productService.deleteProduct(id, uuid);
			LOGGER.info("END productsIdDelete [deleted={}] [{}]", deleted, uuid);
			if (deleted) {
				return ResponseEntity.noContent().build();
			} else {
				throw new ProductException("Producto no encontrado", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("EXCEPTION productsIdDelete [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Operation(summary = "Listar un producto por ID", description = "Obtiene los detalles de un producto específico por su ID.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto recuperado con éxito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@GetMapping(value = "/products/{id}", produces = { "application/json" })
	public ResponseEntity<ProductResponse> productsIdGet(
			@Parameter(in = ParameterIn.PATH, description = "ID del producto.", required = true, schema = @Schema()) @PathVariable("id") Long id)
			throws ProductException {
		UUID uuid = UUID.randomUUID();
		LOGGER.info("INIT productsIdGet [id={}] [{}]", id, uuid);
		try {
			ProductResponse response = productService.getProductById(id, uuid);
			LOGGER.info("END productsIdGet [response={}] [{}]", response, uuid);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("EXCEPTION productsIdGet [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Operation(summary = "Actualizar un producto", description = "Actualiza la información de un producto existente.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto actualizado con éxito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@PutMapping(value = "/products/{id}", produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<ProductResponse> productsIdPut(
			@Parameter(in = ParameterIn.PATH, description = "ID del producto a actualizar.", required = true, schema = @Schema()) @PathVariable("id") Long id,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @RequestBody ProductRequest updatedProduct)
			throws ProductException {
		UUID uuid = UUID.randomUUID();
		LOGGER.info("INIT productsIdPut [id={}, product={}] [{}]", id, updatedProduct, uuid);
		try {
			Boolean resultUpdate = productService.updateProductById(id, updatedProduct, uuid);
			if (resultUpdate) {
				ProductResponse response = productService.getProductById(id, uuid);
				LOGGER.info("END productsIdPut [response={}] [{}]", response, uuid);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				throw new ProductException("Error al actualizar producto", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			LOGGER.error("EXCEPTION productsIdPut [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Operation(summary = "Crear un producto", description = "Crea un nuevo producto.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Producto creado con éxito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@PostMapping(value = "/products", produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<ProductResponse> productsPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @RequestBody ProductRequest productRequest)
			throws ProductException {
		UUID uuid = UUID.randomUUID();
		LOGGER.info("INIT productsPost [product={}] [{}]", productRequest, uuid);
		try {
			ProductResponse response = productService.saveProduct(productRequest, uuid);
			LOGGER.info("END productsPost [response={}] [{}]", response, uuid);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.error("EXCEPTION productsPost [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

	@Operation(summary = "Buscar productos por nombre", description = "Busca productos cuyo nombre contenga un texto específico.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Productos encontrados.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@GetMapping(value = "/products/search", produces = { "application/json" })
	public ResponseEntity<List<ProductResponse>> productsSearchGet(
			@Parameter(in = ParameterIn.QUERY, description = "Nombre o parte del nombre del producto a buscar.", required = true, schema = @Schema()) @RequestParam(value = "name") String name)
			throws ProductException {
		UUID uuid = UUID.randomUUID();
		LOGGER.info("INIT productsSearchGet [name={}] [{}]", name, uuid);
		try {
			List<ProductResponse> response = productService.searchProduct(name, uuid);
			LOGGER.info("END productsSearchGet [size={}] [{}]", response.size(), uuid);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("EXCEPTION productsSearchGet [{}] [{}]", e.getMessage(), uuid);
			throw e;
		}
	}

}
