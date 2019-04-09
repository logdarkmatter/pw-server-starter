package com.ulht.pw.controller.rest.errors;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldErrorVM implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String objectName;

	private final String field;

	private final String message;
}
