package com.ulht.pw.dto;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.ReadOnlyProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@ReadOnlyProperty
	private String createdBy;
	private Instant createdDate = Instant.now();
	@ReadOnlyProperty
	private String lastModifiedBy;
	private Instant lastModifiedDate = Instant.now();

	public boolean isNew() {
		return id == null;
	}

}
