package com.ulht.pw.dto;

import java.io.Serializable;
import java.time.Instant;

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
	private String createdBy;
	private Instant createdDate;
	private String lastModifiedBy;
	private Instant lastModifiedDate;

	public boolean isNew() {
		return id == null;
	}

}
