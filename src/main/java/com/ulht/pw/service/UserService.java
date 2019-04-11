package com.ulht.pw.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ulht.pw.controller.rest.errors.EmailAlreadyUsedException;
import com.ulht.pw.controller.rest.errors.LoginAlreadyUsedException;
import com.ulht.pw.domain.AuthorityEntity;
import com.ulht.pw.domain.UserEntity;
import com.ulht.pw.dto.user.UserDTO;
import com.ulht.pw.repository.UserRepository;
import com.ulht.pw.repository.audit.AuthorityRepository;
import com.ulht.pw.security.AuthoritiesConstants;
import com.ulht.pw.security.SecurityUtils;
import com.ulht.pw.service.util.RandomUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthorityRepository authorityRepository;

	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	public UserEntity createUser(UserDTO userDTO) {
		UserEntity user = new UserEntity();
		user.setLogin(userDTO.getLogin().toLowerCase());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		// user.setEmail(userDTO.getEmail().toLowerCase()); // PHASE 1
		user.setEmail(userDTO.getLogin().toLowerCase());

		String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
		user.setPassword(encryptedPassword);

		if (userDTO.getAuthorities() != null) {
			Set<AuthorityEntity> authorities = userDTO.getAuthorities().stream()
					.map(authorityRepository::findById)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toSet());
			user.setAuthorities(authorities);
		}

		userRepository.save(user);
		log.debug("Created Information for User: {}", user);
		return user;
	}

	public UserEntity registerUser(UserDTO userDTO, String password) {

		userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
			throw new LoginAlreadyUsedException();
		});

		userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
			throw new EmailAlreadyUsedException();
		});

		UserEntity newUser = new UserEntity();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getLogin().toLowerCase());
		// new user gets initially a institutionsgenerated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		// newUser.setEmail(userDTO.getEmail().toLowerCase()); // PHASE 1
		newUser.setEmail(userDTO.getLogin().toLowerCase() + "@gmail.com");
		Set<AuthorityEntity> authorities = new HashSet<>();
		authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
		newUser.setAuthorities(authorities);
		userRepository.save(newUser);
		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDTO
	 *            user to update
	 * @return updated user
	 */
	public Optional<UserDTO> updateUser(UserDTO userDTO) {
		return Optional.of(userRepository
				.findById(userDTO.getId()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(user -> {
					user.setLogin(userDTO.getLogin().toLowerCase());
					user.setFirstName(userDTO.getFirstName());
					user.setLastName(userDTO.getLastName());
					user.setEmail(userDTO.getEmail().toLowerCase());
					Set<AuthorityEntity> managedAuthorities = user.getAuthorities();
					managedAuthorities.clear();
					userDTO.getAuthorities().stream()
							.map(authorityRepository::findById)
							.filter(Optional::isPresent)
							.map(Optional::get)
							.forEach(managedAuthorities::add);
					log.debug("Changed Information for User: {}", user);
					return user;
				})
				.map(UserDTO::new);
	}

	/**
	 * Update basic information (first name, last name, email, language) for the current user.
	 *
	 * @param firstName
	 *            first name of user
	 * @param lastName
	 *            last name of user
	 * @param email
	 *            email id of user
	 * @param langKey
	 *            language key
	 * @param imageUrl
	 *            image URL of user
	 */
	public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
		SecurityUtils.getCurrentUserLogin()
				.flatMap(userRepository::findOneByLogin)
				.ifPresent(user -> {
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email.toLowerCase());
					log.debug("Changed Information for User: {}", user);
				});
	}

	public void deleteUser(String login) {
		userRepository.findOneByLogin(login).ifPresent(user -> {
			userRepository.delete(user);
			log.debug("Deleted User: {}", user);
		});
	}

	@Transactional(readOnly = true)
	public Optional<UserEntity> getUserWithAuthoritiesByLogin(String login) {
		return userRepository.findOneWithAuthoritiesByLogin(login);
	}

	@Transactional(readOnly = true)
	public Optional<UserEntity> getUserWithAuthorities(Long id) {
		return userRepository.findOneWithAuthoritiesById(id);
	}

	@Transactional(readOnly = true)
	public Optional<UserEntity> getUserWithAuthorities() {
		return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
	}

	/**
	 * @return a list of all the authorities
	 */
	public List<String> getAuthorities() {
		return authorityRepository.findAll().stream().map(AuthorityEntity::getName).collect(Collectors.toList());
	}

}
