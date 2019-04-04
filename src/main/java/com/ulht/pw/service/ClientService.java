package com.ulht.pw.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientService {

	@GetMapping("/{id}")
	public ResponseEntity<String> test(@PathVariable(value = "id") Long clientId) {
		return ResponseEntity.ok("String");
	}
}
