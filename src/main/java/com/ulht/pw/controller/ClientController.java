package com.ulht.pw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulht.pw.domain.ClientEntity;
import com.ulht.pw.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientService;

	@GetMapping("/{id}")
	public ResponseEntity<ClientEntity> searchClientById(@PathVariable(value = "id") Long clientId) {
		return ResponseEntity.ok().body(clientService.searchClientById(clientId));
	}

}
