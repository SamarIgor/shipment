package org.app.shipment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.app.shipment.api_response.ApiResponse;
import org.app.shipment.dto.client.ClientRequest;
import org.app.shipment.dto.client.ClientResponse;
import org.app.shipment.service.ClientService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/client")
public class ClientController {

    public ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ClientResponse>>> getClients(
            @PageableDefault(size=10,sort = "id")
            @ParameterObject Pageable pageable,
            HttpServletRequest request){

        Page<ClientResponse> response = clientService.getAllClients(pageable);

        ApiResponse<Page<ClientResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Clients fetched successfully",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponse>> getClientById(
            @PathVariable Long id, HttpServletRequest request){

        ClientResponse response = clientService.getClientById(id);

        ApiResponse<ClientResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Client with id '"+id+"' fetched successfully",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponse>> insertClient(
            @RequestBody @Valid ClientRequest clientRequest, HttpServletRequest request){

        ClientResponse response = clientService.insertClient(clientRequest);

        ApiResponse<ClientResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Added new client'"+clientRequest.getFirstName()+"'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponse>> updateClient(
            @PathVariable Long id, HttpServletRequest request,
            @RequestBody @Valid ClientRequest clientRequest){
        ClientResponse response = clientService.updateClient(id, clientRequest);

        ApiResponse<ClientResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Updated client with id '"+id+"'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(
            @PathVariable Long id, HttpServletRequest request){

        clientService.deleteClient(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Deleted client '"+id+"'",
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }
}
