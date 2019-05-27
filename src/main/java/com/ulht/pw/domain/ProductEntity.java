package com.ulht.pw.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String productName;
	private String productCode;
	private String desription;
	private LocalDate expireDate;
	private String brand;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductPrecautionsEntity> productPrecautions = new HashSet<>();

}