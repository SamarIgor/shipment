package org.app.shipment.mapper;

import org.app.shipment.dto.client.ClientRequest;
import org.app.shipment.dto.client.ClientResponse;
import org.app.shipment.model.Client;

import java.time.LocalDateTime;

public class ClientMapper {

    public static Client toEntity(ClientRequest request){
        Client c = new Client();
        c.setEmail(request.getEmail());
        c.setFirstName(request.getFirstName());
        c.setLastName(request.getLastName());
        c.setCreatedAt(LocalDateTime.now());
        return c;
    }

    public static ClientResponse toResponse(Client c){
        return new ClientResponse(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getCreatedAt()
        );
    }
}
