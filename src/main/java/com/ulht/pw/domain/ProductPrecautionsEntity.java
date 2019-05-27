package com.ulht.pw.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_precautions")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = "product")
public class ProductPrecautionsEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String description;
	private String precautionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;
}