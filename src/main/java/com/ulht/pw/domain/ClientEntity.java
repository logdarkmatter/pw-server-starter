package com.ulht.pw.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	private LocalDate dateOfBirth;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AddressEntity> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ContactEntity> contacts = new ArrayList<>();

}
