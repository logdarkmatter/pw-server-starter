package com.ulht.pw.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ulht.pw.controller.rest.errors.InternalServerErrorException;
import com.ulht.pw.controller.vm.LoginVM;
import com.ulht.pw.dto.user.UserDTO;
import com.ulht.pw.security.jwt.JWTFilter;
import com.ulht.pw.security.jwt.TokenProvider;
import com.ulht.pw.service.UserService;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {
	private final TokenProvider tokenProvider;

	private final UserService userService;

	private final AuthenticationManager authenticationManager;

	public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserService userService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername().toLowerCase(), loginVM.getPassword());

		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.createToken(authentication);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}

	/**
	 * GET /account : get the current user.
	 *
	 * @return the current user
	 * @throws RuntimeException
	 *             500 (Internal Server Error) if the user couldn't be returned
	 */
	@GetMapping("/account")
	public UserDTO getAccount() {
		return userService.getUserWithAuthorities().map(UserDTO::new)
				.orElseThrow(() -> new InternalServerErrorException("User could not be found"));
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private String idToken;

		JWTToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}
	}
}
