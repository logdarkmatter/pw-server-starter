package com.ulht.pw.domain.audit;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persistent_audit_event")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersistentAuditEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long id;

	@NotNull
	@Column(nullable = false)
	private String principal;

	@Column(name = "event_date")
	private Instant auditEventDate;

	@Column(name = "event_type")
	private String auditEventType;

	@ElementCollection
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@CollectionTable(name = "persistent_audit_evt_data", joinColumns = @JoinColumn(name = "event_id"))
	private Map<String, String> data = new HashMap<>();

}
