package com.ulht.pw.controller.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResourceNotFoundException extends AbstractThrowableProblem {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(ErrorConstants.ENTITY_NOT_FOUND_TYPE, String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue),
				Status.NOT_FOUND);
	}
}
