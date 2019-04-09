package com.ulht.pw.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulht.pw.PWApplicationConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "pw_user", schema = "public")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Pattern(regexp = PWApplicationConstants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String login;

	@JsonIgnore
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password_hash", length = 60, nullable = false)
	private String password;

	@Email
	@Size(min = 5, max = 254)
	@Column(length = 254, unique = true)
	private String email;

	@Size(max = 50)
	@Column(name = "first_name", length = 50)
	private String firstName;

	@Size(max = 255)
	@Column(name = "last_name", length = 255)
	private String lastName;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "user_authority", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_name", referencedColumnName = "name") })

	@BatchSize(size = 20)
	private Set<AuthorityEntity> authorities = new HashSet<>();
}
