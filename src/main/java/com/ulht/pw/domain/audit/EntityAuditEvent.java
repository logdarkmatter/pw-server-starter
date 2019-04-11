package com.ulht.pw.domain.audit;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entity_audit_event")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntityAuditEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "entity_id", nullable = false)
	private Long entityId;

	@NotNull
	@Size(max = 255)
	@Column(name = "entity_type", length = 255, nullable = false)
	private String entityType;

	@NotNull
	@Size(max = 20)
	@Column(name = "action", length = 20, nullable = false)
	private String action;

	@Lob
	@Column(name = "entity_value")
	private String entityValue;

	@Column(name = "commit_version")
	private Integer commitVersion;

	@Size(max = 100)
	@Column(name = "last_modified_by", length = 100)
	private String lastModifiedBy;

	@NotNull
	@Column(name = "last_modified_date", nullable = false)
	private Instant lastModifiedDate;
}
