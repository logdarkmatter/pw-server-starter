package com.ulht.pw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ulht.pw.controller.rest.errors.ResourceNotFoundException;
import com.ulht.pw.domain.ClientEntity;
import com.ulht.pw.dto.client.ClientDTO;
import com.ulht.pw.repository.ClientRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClientService {

	private static final String DOMAIN_NAME = "ClientEntity";

	private final ClientRepository clientRepository;

	private final MapperFacade mapper;

	public ClientDTO searchClientById(Long clientId) {
		ClientEntity client = clientRepository.findById(clientId)
				.orElseThrow(() -> new ResourceNotFoundException(DOMAIN_NAME, "id", clientId));
		return mapper.map(client, ClientDTO.class);
	}

	public List<ClientDTO> findAllClients() {
		return mapper.mapAsList(clientRepository.findAll(), ClientDTO.class);
	}

	@Transactional
	public ClientDTO createClient(ClientDTO client) {
		ClientEntity clientEntity = handClientSave(client);
		return mapper.map(clientRepository.save(clientEntity), ClientDTO.class);
	}

	@Transactional
	public ClientDTO updateClient(ClientDTO client) {
		ClientEntity clientEntity = handClientSave(client);
		return mapper.map(clientRepository.save(clientEntity), ClientDTO.class);
	}

	private ClientEntity handClientSave(ClientDTO client) {
		ClientEntity clientEntity = mapper.map(client, ClientEntity.class);
		clientEntity.getAddresses().forEach(address -> address.setClient(clientEntity));
		clientEntity.getContacts().forEach(contact -> contact.setClient(clientEntity));
		return clientEntity;
	}

	@Transactional
	public void deleteClientById(Long clientId) {
		clientRepository.findById(clientId).ifPresent(client -> {
			clientRepository.delete(client);
			log.debug("Deleted Client: {}", client);
		});
	}
}
