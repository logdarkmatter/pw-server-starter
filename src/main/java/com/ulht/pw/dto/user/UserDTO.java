package com.ulht.pw.dto.user;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ulht.pw.PWApplicationConstants;
import com.ulht.pw.domain.AuthorityEntity;
import com.ulht.pw.domain.UserEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO {

	private Long id;

	@NotBlank
	@Pattern(regexp = PWApplicationConstants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Email
	@Size(min = 5, max = 254)
	private String email;

	private String createdBy;

	private Instant createdDate;

	private String lastModifiedBy;

	private Instant lastModifiedDate;

	private Set<String> authorities;

	public UserDTO(UserEntity user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.createdBy = user.getCreatedBy();
		this.createdDate = user.getCreatedDate();
		this.lastModifiedBy = user.getLastModifiedBy();
		this.lastModifiedDate = user.getLastModifiedDate();
		this.authorities = user.getAuthorities().stream()
				.map(AuthorityEntity::getName)
				.collect(Collectors.toSet());
	}

}
