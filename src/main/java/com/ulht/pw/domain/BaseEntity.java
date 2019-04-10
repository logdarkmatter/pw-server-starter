package com.ulht.pw.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulht.pw.PWApplicationConstants;
import com.ulht.pw.security.SecurityUtils;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedBy
	@Column(name = "created_by", nullable = false, length = 50, updatable = false)
	@JsonIgnore
	private String createdBy;

	@CreatedDate
	@Column(name = "created_date", updatable = false)
	@JsonIgnore
	private Instant createdDate;

	@LastModifiedBy
	@Column(name = "last_modified_by", length = 50)
	@JsonIgnore
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	@JsonIgnore
	private Instant lastModifiedDate;

	@PrePersist
	void atCreate() {
		createdBy = SecurityUtils.getCurrentUserLogin().orElse(PWApplicationConstants.ANONYMOUS_USER);
		createdDate = Instant.now();
	}

	@PreUpdate
	void atUpdate() {
		lastModifiedBy = SecurityUtils.getCurrentUserLogin().orElse(PWApplicationConstants.ANONYMOUS_USER);
		lastModifiedDate = Instant.now();
	}

}
