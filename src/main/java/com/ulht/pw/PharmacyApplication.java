package com.ulht.pw;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class PharmacyApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(PharmacyApplication.class);
		Environment env = app.run(args).getEnvironment();

		String protocol = "http";
		// if (!env.getProperty("server.ssl.key-store").isEmpty()) {
		// protocol = "https";
		// }
		log.info("\n----------------------------------------------------------\n\t" +
				"Application '{}' is running! Access URLs:\n\t" +
				"Local: \t\t{}://localhost:{}\n\t" +
				"External: \t{}://{}:{}\n\t" +
				"Profile(s): \t{}\n"
				+ "----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				protocol,
				env.getProperty("server.port"),
				protocol,
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				env.getActiveProfiles());
	}
}
