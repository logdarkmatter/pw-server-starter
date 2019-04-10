package com.ulht.pw.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String lastName;
	private String firstName;
	private LocalDate dateOfBirth;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<AddressEntity> addresses = new HashSet<>();

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ContactEntity> contacts = new HashSet<>();

}
