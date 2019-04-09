package com.ulht.pw.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "authority")
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 50)
	@Id
	@Column(length = 50)
	private String name;
}
