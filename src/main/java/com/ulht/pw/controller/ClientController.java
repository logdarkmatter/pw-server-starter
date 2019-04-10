package com.ulht.pw.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulht.pw.controller.rest.errors.InvalidCreateException;
import com.ulht.pw.controller.rest.errors.InvalidUpdateException;
import com.ulht.pw.dto.client.ClientDTO;
import com.ulht.pw.service.ClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

	private static final String ENTITY_NAME = "clientEntity";

	private final ClientService clientService;

	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> searchClientById(@PathVariable(value = "id") Long clientId) {
		log.debug("REST request to get client with Id : {}", clientId);
		return ResponseEntity.ok().body(clientService.searchClientById(clientId));
	}

	@GetMapping("/list")
	public ResponseEntity<List<ClientDTO>> getAllClients() {
		log.debug("REST request to get all Clients");
		return ResponseEntity.ok().body(clientService.findAllClients());
	}

	@PostMapping("/save")
	public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO client) throws URISyntaxException {
		log.debug("REST request to save Client : {}", client);
		if (!client.isNew()) {
			throw new InvalidCreateException(ENTITY_NAME);
		}

		ClientDTO result = clientService.createClient(client);
		return ResponseEntity.created(new URI("/api/client/" + result.getId())).body(result);
	}

	@PutMapping("/update")
	public ResponseEntity<ClientDTO> updateClient(@Valid @RequestBody ClientDTO client) throws URISyntaxException {
		log.debug("REST request to save ClientEntity : {}", client);
		if (client.isNew()) {
			throw new InvalidUpdateException(ENTITY_NAME);
		}

		ClientDTO result = clientService.updateClient(client);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteClient(@PathVariable(value = "id") Long id) {
		log.debug("REST request to delete Client : {}", id);
		clientService.deleteClientById(id);
		return ResponseEntity.ok().build();
	}

}
