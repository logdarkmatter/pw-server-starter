package com.ulht.pw.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulht.pw.domain.enums.ContactType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "contact")
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ContactType contactType;
	private String contact;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
	private ClientEntity client;
}