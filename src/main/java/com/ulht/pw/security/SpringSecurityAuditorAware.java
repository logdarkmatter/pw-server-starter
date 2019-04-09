package com.ulht.pw.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.ulht.pw.PWApplicationConstants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(PWApplicationConstants.SYSTEM_ACCOUNT));
	}
}
