package com.ulht.pw.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulht.pw.domain.ClientEntity;
import com.ulht.pw.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

	private final Logger log = LoggerFactory.getLogger(ClientController.class);

	private static final String ENTITY_NAME = "clientEntity";

	private final ClientService clientService;

	@GetMapping("/{id}")
	public ResponseEntity<ClientEntity> searchClientById(@PathVariable(value = "id") Long clientId) {
		return ResponseEntity.ok().body(clientService.searchClientById(clientId));
	}

	@GetMapping("/list")
	public ResponseEntity<List<ClientEntity>> getAllClients() {
		log.debug("REST request to get all Clients");
		return ResponseEntity.ok().body(clientService.findAllClients());
	}

	@PostMapping("/save")
	public ResponseEntity<ClientEntity> createClient(@Valid @RequestBody ClientEntity clientEntity) throws URISyntaxException {
		log.debug("REST request to save ClientEntity : {}", clientEntity);
		if (clientEntity.getId() != null) {
			throw new RuntimeException("A new Client cannot already have an ID - " + ENTITY_NAME);
		}

		ClientEntity result = clientService.createClient(clientEntity);
		return ResponseEntity.created(new URI("/api/client/" + result.getId())).body(result);
	}

	@PutMapping("/update")
	public ResponseEntity<ClientEntity> updateClient(@Valid @RequestBody ClientEntity clientEntity) throws URISyntaxException {
		log.debug("REST request to save ClientEntity : {}", clientEntity);
		if (clientEntity.getId() == null) {
			throw new RuntimeException("Invalid id - " + ENTITY_NAME);
		}

		ClientEntity result = clientService.updateClient(clientEntity);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
		log.debug("REST request to delete Client : {}", id);
		clientService.deleteClientById(id);
		return ResponseEntity.ok().build();
	}

}
