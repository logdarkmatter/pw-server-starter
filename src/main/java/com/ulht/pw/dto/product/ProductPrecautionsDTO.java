package com.ulht.pw.dto.product;

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
public class ProductPrecautionsDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	private String description;
	private String precautionType;
}
