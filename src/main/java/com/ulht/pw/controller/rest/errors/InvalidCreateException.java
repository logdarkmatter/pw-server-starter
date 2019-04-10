package com.ulht.pw.controller.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidCreateException extends AbstractThrowableProblem {

	private static final long serialVersionUID = 1L;

	public InvalidCreateException(String entityName) {
		super(ErrorConstants.ENTITY_FOUND_TYPE, String.format("Invalid id - : '%s'", entityName), Status.BAD_REQUEST);
	}
}
