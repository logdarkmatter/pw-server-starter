package com.ulht.pw.config;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.log4j.Log4j2;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Log4j2
@Configuration
public class WebConfigurer {

	@Value("${cors.allowed-origins}")
	private String allowedOrigins;

	@Value("${cors.allowed-methods}")
	private String allowedMethods;

	@Value("${cors.allowed-headers}")
	private String allowedHeaders;

	@Value("${cors.allow-credentials}")
	private Boolean allowCredentials;

	@Value("${cors.max-age}")
	private Long maxAge;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Stream.of(allowedOrigins).map(w -> w.split(",")).flatMap(Arrays::stream).collect(Collectors.toList()));
		config.setAllowedMethods(Stream.of(allowedMethods).map(w -> w.split(",")).flatMap(Arrays::stream).collect(Collectors.toList()));
		config.setAllowedHeaders(Stream.of(allowedHeaders).map(w -> w.split(",")).flatMap(Arrays::stream).collect(Collectors.toList()));
		config.setAllowCredentials(allowCredentials);
		config.setMaxAge(maxAge);
		if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
			log.debug("Registering CORS filter");
			source.registerCorsConfiguration("/api/**", config);
		}
		return new CorsFilter(source);
	}
}
