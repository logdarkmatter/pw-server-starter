package com.ulht.pw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ulht.pw.controller.rest.errors.ResourceNotFoundException;
import com.ulht.pw.domain.ProductEntity;
import com.ulht.pw.dto.product.ProductDTO;
import com.ulht.pw.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

	private static final String DOMAIN_NAME = "ProductEntity";

	private final ProductRepository productRepository;

	private final MapperFacade mapper;

	public ProductDTO searchProductById(Long productId) {
		ProductEntity product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(DOMAIN_NAME, "id", productId));
		return mapper.map(product, ProductDTO.class);
	}

	public List<ProductDTO> findAllProducts() {
		return mapper.mapAsList(productRepository.findAll(), ProductDTO.class);
	}

	@Transactional
	public ProductDTO createProduct(ProductDTO product) {
		ProductEntity productEntity = handleProductSave(product);
		return mapper.map(productRepository.save(productEntity), ProductDTO.class);
	}

	@Transactional
	public ProductDTO updateProduct(ProductDTO product) {
		ProductEntity productEntity = handleProductSave(product);
		return mapper.map(productRepository.save(productEntity), ProductDTO.class);
	}

	private ProductEntity handleProductSave(ProductDTO product) {
		ProductEntity productEntity = mapper.map(product, ProductEntity.class);
		productEntity.getProductPrecautions().forEach(productPrecautions -> productPrecautions.setProduct(productEntity));
		return productEntity;
	}

	@Transactional
	public void deleteProductById(Long productId) {
		productRepository.findById(productId).ifPresent(product -> {
			productRepository.delete(product);
			log.debug("Deleted Product: {}", product);
		});
	}
}
