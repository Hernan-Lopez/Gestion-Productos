package com.hernan.gestionproductos.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.hernan.gestionproductos.domain.Product;

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

	@Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos recuperada con éxito.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<List<Product>> productsGet() {
		List<Product> response = new ArrayList<>();
		Product p = new Product();
		p.setCategory("w");
		p.setId(1);
		p.setName("s");
		p.setPrice(2f);
		response.add(p);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Eliminar un producto", description = "Elimina un producto existente por su ID.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Producto eliminado con éxito."),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@DeleteMapping(value = "/{id}", produces = { "application/json" })
	public ResponseEntity<Void> productsIdDelete(
			@Parameter(in = ParameterIn.PATH, description = "ID del producto a eliminar.", required = true, schema = @Schema()) @PathVariable("id") Integer id) {
		return null;
	}

	@Operation(summary = "Listar un producto por ID", description = "Obtiene los detalles de un producto específico por su ID.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto recuperado con éxito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@GetMapping(value = "/{id}", produces = { "application/json" })
	public ResponseEntity<Product> productsIdGet(
			@Parameter(in = ParameterIn.PATH, description = "ID del producto.", required = true, schema = @Schema()) @PathVariable("id") Integer id) {
		List<Product> response = new ArrayList<>();
		Product p = new Product();
		p.setCategory("w");
		p.setId(1);
		p.setName("s");
		p.setPrice(2f);
		response.add(p);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@Operation(summary = "Actualizar un producto", description = "Actualiza la información de un producto existente.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto actualizado con éxito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@PutMapping(value = "/{id}", produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<Product> productsIdPut(
			@Parameter(in = ParameterIn.PATH, description = "ID del producto a actualizar.", required = true, schema = @Schema()) @PathVariable("id") Integer id,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @RequestBody Product body) {
		return null;
	}

	@Operation(summary = "Crear un producto", description = "Crea un nuevo producto.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Producto creado con éxito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@PostMapping(produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<Product> productsPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @RequestBody Product body) {
		return null;
	}

	@Operation(summary = "Buscar productos por nombre", description = "Busca productos cuyo nombre contenga un texto específico.", security = {
			@SecurityRequirement(name = "basicAuth") }, tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Productos encontrados.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
			@ApiResponse(responseCode = "400", description = "La solicitud no es válida o contiene errores.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "401", description = "El cliente no está autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "403", description = "El cliente no tiene permisos para acceder al recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no fue encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "500", description = "Error interno en el servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@GetMapping(value = "/search", produces = { "application/json" })
	public ResponseEntity<List<Product>> productsSearchGet(
			@Parameter(in = ParameterIn.QUERY, description = "Nombre o parte del nombre del producto a buscar.", required = true, schema = @Schema()) @RequestParam(value = "name") String name) {
		return null;
	}

}
