package com.ulht.pw.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "address")
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String address;
	private String postalCode;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
	private ClientEntity client;
}
