package com.ulht.pw.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.ulht.pw.security.AuthoritiesConstants;
import com.ulht.pw.security.jwt.JWTConfigurer;
import com.ulht.pw.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final UserDetailsService userDetailsService;

	private final TokenProvider tokenProvider;

	private final CorsFilter corsFilter;

	private final SecurityProblemSupport problemSupport;

	@PostConstruct
	public void init() {
		try {
			authenticationManagerBuilder
					.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder());
		} catch (Exception e) {
			throw new BeanInitializationException("Security configuration failed", e);
		}
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/app/**/*.{js,html}")
				.antMatchers("/i18n/**")
				.antMatchers("/content/**")
				.antMatchers("/test/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
				.disable()
				.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
				.authenticationEntryPoint(problemSupport)
				.accessDeniedHandler(problemSupport)
				.and()
				.headers()
				.frameOptions()
				.disable()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/api/authenticate").permitAll()
				.antMatchers("/api/**").authenticated()
				.antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.and()
				.apply(securityConfigurerAdapter());
	}

	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(tokenProvider);
	}
}
