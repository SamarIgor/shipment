package org.app.shipment.service;

import org.app.shipment.dto.client.ClientRequest;
import org.app.shipment.dto.client.ClientResponse;
import org.app.shipment.exception.NotFoundException;
import org.app.shipment.mapper.ClientMapper;
import org.app.shipment.model.Client;
import org.app.shipment.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    public ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ClientResponse insertClient(ClientRequest request) {

        Client client = ClientMapper.toEntity(request);
        Client saved = clientRepository.save(client);

        return ClientMapper.toResponse(saved);
    }

    public ClientResponse getClientById(Long id) {
        return clientRepository.findById(id)
                .map(ClientMapper::toResponse)
                .orElseThrow(() -> {
                    return new NotFoundException("Client by id "+id+" not found");
                });
    }

    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException("Client by id "+id+" not found");
                });

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());

        Client updated = clientRepository.save(existing);

        return ClientMapper.toResponse(updated);
    }

    public void deleteClient(Long id) {
        if(!clientRepository.existsById(id)){
            throw new NotFoundException("Cant find Client by id "+id);
        }

        clientRepository.deleteById(id);
    }
}
