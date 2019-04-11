package com.ulht.pw.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
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
	private Instant createdDate = Instant.now();

	@LastModifiedBy
	@Column(name = "last_modified_by", length = 50)
	@JsonIgnore
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	@JsonIgnore
	private Instant lastModifiedDate = Instant.now();
}
