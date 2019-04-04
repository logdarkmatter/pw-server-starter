package com.ulht.pw.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "client")
@Data
@EqualsAndHashCode(callSuper = false)
public class ClientEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String lastName;
	private String firstName;

}
