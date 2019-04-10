package com.ulht.pw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ulht.pw.PWApplicationConstants;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableJpaRepositories(basePackages = PWApplicationConstants.DAO_PACKAGE)
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

}
