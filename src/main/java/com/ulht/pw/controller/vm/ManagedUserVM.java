package com.ulht.pw.controller.vm;

import javax.validation.constraints.Size;

import com.ulht.pw.dto.user.UserDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ManagedUserVM extends UserDTO {

	public static final int PASSWORD_MIN_LENGTH = 4;

	public static final int PASSWORD_MAX_LENGTH = 100;

	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
	private String password;

}
