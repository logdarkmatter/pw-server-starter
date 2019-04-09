package com.ulht.pw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ulht.pw.domain.ClientEntity;
import com.ulht.pw.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

	private final ClientRepository clientRepository;

	public ClientEntity searchClientById(Long clientId) {
		ClientEntity client = clientRepository.findById(clientId).orElse(null);
		if (client == null) {
			throw new RuntimeException("Client with ID: " + clientId + " not found");
		}
		return client;
	}

	public List<ClientEntity> findAllClients() {
		return clientRepository.findAll();
	}

	@Transactional
	public ClientEntity createClient(ClientEntity clientEntity) {
		handClientSave(clientEntity);
		return clientRepository.save(clientEntity);
	}

	@Transactional
	public ClientEntity updateClient(ClientEntity clientEntity) {
		handClientSave(clientEntity);
		return clientRepository.save(clientEntity);
	}

	private void handClientSave(ClientEntity clientEntity) {
		clientEntity.getAddresses().forEach(address -> address.setClient(clientEntity));
		clientEntity.getContacts().forEach(contact -> contact.setClient(clientEntity));
	}

	@Transactional
	public void deleteClientById(Long clientId) {
		clientRepository.deleteById(clientId);
	}
}
