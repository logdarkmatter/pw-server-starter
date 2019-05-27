package com.ulht.pw.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulht.pw.controller.rest.errors.InvalidCreateException;
import com.ulht.pw.controller.rest.errors.InvalidUpdateException;
import com.ulht.pw.dto.product.ProductDTO;
import com.ulht.pw.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private static final String ENTITY_NAME = "productEntity";

	private final ProductService productService;

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> searchProductById(@PathVariable(value = "id") Long productId) {
		log.debug("REST request to get Product with Id : {}", productId);
		return ResponseEntity.ok().body(productService.searchProductById(productId));
	}

	@GetMapping("/list")
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		log.debug("REST request to get all Products");
		return ResponseEntity.ok().body(productService.findAllProducts());
	}

	@PostMapping("/save")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product) throws URISyntaxException {
		log.debug("REST request to save Product : {}", product);
		if (!product.isNew()) {
			throw new InvalidCreateException(ENTITY_NAME);
		}

		ProductDTO result = productService.createProduct(product);
		return ResponseEntity.created(new URI("/api/product/" + result.getId())).body(result);
	}

	@PutMapping("/update")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO product) throws URISyntaxException {
		log.debug("REST request to save ProductEntity : {}", product);
		if (product.isNew()) {
			throw new InvalidUpdateException(ENTITY_NAME);
		}

		ProductDTO result = productService.updateProduct(product);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Long id) {
		log.debug("REST request to delete Product : {}", id);
		productService.deleteProductById(id);
		return ResponseEntity.ok().build();
	}

}
