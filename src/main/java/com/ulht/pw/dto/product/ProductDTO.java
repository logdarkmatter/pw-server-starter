package com.ulht.pw.dto.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ulht.pw.dto.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	private String productName;
	private String productCode;
	private String desription;
	private LocalDate expireDate;
	private String brand;
	private List<ProductPrecautionsDTO> productPrecautions = new ArrayList<>();
}
