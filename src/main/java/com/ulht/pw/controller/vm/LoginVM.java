package com.ulht.pw.controller.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * View Model object for storing a user's credentials.
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginVM {

	@NotNull
	@Size(min = 1, max = 50)
	private String username;

	@NotNull
	@Size(min = ManagedUserVM.PASSWORD_MIN_LENGTH, max = ManagedUserVM.PASSWORD_MAX_LENGTH)
	private String password;

}
