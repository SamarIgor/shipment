package org.app.shipment.controller.service;

import org.app.shipment.dto.client.ClientRequest;
import org.app.shipment.dto.client.ClientResponse;
import org.app.shipment.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/client")
public class ClientController {

    public ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientResponse> getClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientResponse getClientById(@PathVariable Long id){
        return clientService.getClientById(id);
    }

    @PostMapping
    public ClientResponse insertClient(@RequestBody ClientRequest request){
        return clientService.insertClient(request);
    }

    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id,ClientRequest request){
        return clientService.updateClient(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
    }
}
