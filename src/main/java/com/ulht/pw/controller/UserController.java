package com.ulht.pw.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulht.pw.PWApplicationConstants;
import com.ulht.pw.controller.rest.errors.BadRequestAlertException;
import com.ulht.pw.controller.rest.errors.EmailAlreadyUsedException;
import com.ulht.pw.controller.rest.errors.LoginAlreadyUsedException;
import com.ulht.pw.domain.UserEntity;
import com.ulht.pw.dto.user.UserDTO;
import com.ulht.pw.repository.UserRepository;
import com.ulht.pw.security.AuthoritiesConstants;
import com.ulht.pw.service.UserService;
import com.ulht.pw.service.util.HeaderUtil;
import com.ulht.pw.service.util.ResponseUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private static final String ENTITY_NAME = "userEntity";

	private final UserService userService;

	private final UserRepository userRepository;

	@GetMapping("/list")
	public ResponseEntity<List<UserEntity>> getAllUsers() {
		log.debug("REST request to get all Users");
		return ResponseEntity.ok().body(userService.findAllUsers());
	}

	/**
	 * POST /users : Creates a new user.
	 * 
	 * Creates a new user if the login and email are not already used, and sends an
	 * mail with an activation link.
	 * The user needs to be activated on creation.
	 *
	 * @param userDTO
	 *            the user to create
	 * 
	 * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or
	 *         email is already in use
	 * 
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 * 
	 * @throws BadRequestAlertException
	 *             400 (Bad Request) if the login or email is already in use
	 */
	@PostMapping("/users")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
		log.debug("REST request to save User : {}", userDTO);

		if (userDTO.getId() != null) {
			throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
			// Lowercase the user login before comparing with database
		} else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
			throw new LoginAlreadyUsedException();
		} else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) { // PHASE 1
			throw new EmailAlreadyUsedException();
		} else {
			UserEntity newUser = userService.createUser(userDTO);

			return ResponseEntity.created(new URI("/api/user/" + newUser.getLogin()))
					.headers(HeaderUtil.createAlert("userManagement.created", newUser.getLogin()))
					.body(newUser);
		}
	}

	/**
	 * PUT /users : Updates an existing User.
	 *
	 * @param userDTO
	 *            the user to update
	 * 
	 * @return the ResponseEntity with status 200 (OK) and with body the updated user
	 * 
	 * @throws EmailAlreadyUsedException
	 *             400 (Bad Request) if the email is already in use
	 * 
	 * @throws LoginAlreadyUsedException
	 *             400 (Bad Request) if the login is already in use
	 */
	@PutMapping("/users")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
		log.debug("REST request to update User : {}", userDTO);

		Optional<UserEntity> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			throw new EmailAlreadyUsedException();
		}

		existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			throw new LoginAlreadyUsedException();
		}

		Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

		return ResponseUtil.wrapOrNotFound(updatedUser,
				HeaderUtil.createAlert("userManagement.updated", userDTO.getLogin()));
	}

	/**
	 * @return a string list of the all of the roles
	 */
	@GetMapping("/users/authorities")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public List<String> getAuthorities() {
		return userService.getAuthorities();
	}

	/**
	 * GET /users/:login : get the "login" user.
	 *
	 * @param login
	 *            the login of the user to find
	 * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
	 */
	@GetMapping("/users/{login:" + PWApplicationConstants.LOGIN_REGEX + "}")
	public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
		log.debug("REST request to get User : {}", login);
		return ResponseUtil.wrapOrNotFound(userService.getUserWithAuthoritiesByLogin(login).map(UserDTO::new));
	}

	/**
	 * DELETE /users/:login : delete the "login" User.
	 *
	 * @param login
	 *            the login of the user to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/users/{login:" + PWApplicationConstants.LOGIN_REGEX + "}")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<Void> deleteUser(@PathVariable String login) {
		log.debug("REST request to delete User: {}", login);
		userService.deleteUser(login);
		return ResponseEntity.ok().build();
	}

}
