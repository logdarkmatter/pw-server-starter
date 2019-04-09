package com.ulht.pw.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ulht.pw.domain.UserEntity;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findOneByEmailIgnoreCase(String email);

	Optional<UserEntity> findOneByLogin(String login);

	@EntityGraph(attributePaths = "authorities")
	Optional<UserEntity> findOneWithAuthoritiesById(Long id);

	@EntityGraph(attributePaths = "authorities")
	Optional<UserEntity> findOneWithAuthoritiesByLogin(String login);

	@EntityGraph(attributePaths = "authorities")
	Optional<UserEntity> findOneWithAuthoritiesByEmail(String email);

	Page<UserEntity> findAllByLoginNot(Pageable pageable, String login);
}
