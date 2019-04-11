package com.ulht.pw.repository.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ulht.pw.domain.AuthorityEntity;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {

}