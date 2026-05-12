package org.app.shipment.service;

import org.app.shipment.dto.client.ClientRequest;
import org.app.shipment.dto.client.ClientResponse;
import org.app.shipment.exception.NotFoundException;
import org.app.shipment.mapper.ClientMapper;
import org.app.shipment.model.Client;
import org.app.shipment.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    public ClientRepository clientRepository;
    private static final Logger log =
            LoggerFactory.getLogger(ClientService.class);

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // GET all clients
    public Page<ClientResponse> getAllClients(Pageable pageable) {
        Page<ClientResponse> responses = clientRepository.findAll(pageable)
                .map(ClientMapper::toResponse);

        log.info("Fetched {} clients", responses.getSize());
        return responses;
    }

    // POST Inserting new Client
    public ClientResponse insertClient(ClientRequest request) {

        Client client = ClientMapper.toEntity(request);
        Client saved = clientRepository.save(client);

        log.info("Inserted new Client '{}'", client.getFirstName());
        return ClientMapper.toResponse(saved);
    }

    // GET/id Find Client by their ID
    public ClientResponse getClientById(Long id) {
        ClientResponse response = clientRepository.findById(id)
                .map(ClientMapper::toResponse)
                .orElseThrow(() -> {
                    log.warn("Cannot GET Client with id '{}'", id);
                    return new NotFoundException("Client by id "+id+" not found");
                });

        log.info("Found Client with id '{}'", response.getId());
        return response;
    }

    // PUT Update user
    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot find Client with id '{}' to UPDATE", id);
                    return new NotFoundException("Client by id "+id+" not found");
                });

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());

        Client updated = clientRepository.save(existing);

        log.info("Updated Client with id '{}'", updated.getId());
        return ClientMapper.toResponse(updated);
    }

    public void deleteClient(Long id) {
        if(!clientRepository.existsById(id)){
            log.warn("Cannot find Client by id '{}' to DELETE", id);
            throw new NotFoundException("Cant find Client by id "+id);
        }

        clientRepository.deleteById(id);
        log.info("Deleted Client with id '{}'", id);
    }
}
