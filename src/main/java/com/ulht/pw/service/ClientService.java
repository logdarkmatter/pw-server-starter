package com.ulht.pw.service;

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
			// DO SOMETHING
		}
		return client;
	}
}
