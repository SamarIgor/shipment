package org.app.shipment.service;

import org.app.shipment.dto.shipment.ShipmentRequest;
import org.app.shipment.dto.shipment.ShipmentResponse;
import org.app.shipment.dto.shipment.ShipmentStatusRequest;
import org.app.shipment.dto.shipment_item.ShipmentItemRequest;
import org.app.shipment.exception.NotFoundException;
import org.app.shipment.mapper.ShipmentMapper;
import org.app.shipment.model.Client;
import org.app.shipment.model.Item;
import org.app.shipment.model.Shipment;
import org.app.shipment.model.ShipmentItem;
import org.app.shipment.repository.ClientRepository;
import org.app.shipment.repository.ItemRepository;
import org.app.shipment.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ClientRepository clientRepository;
    private final ItemRepository itemRepository;

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            ClientRepository clientRepository,
            ItemRepository itemRepository
    ) {
        this.shipmentRepository = shipmentRepository;
        this.clientRepository = clientRepository;
        this.itemRepository = itemRepository;
    }

    // Adding new Shipment order
    public ShipmentResponse createShipment(ShipmentRequest request) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() ->
                new NotFoundException("Client not found"));

        Shipment shipment = new Shipment();

        shipment.setClient(client);
        shipment.setStatus("CREATED");
        shipment.setSendingAddress(request.getSendingAddress());
        shipment.setArrivingAddress(request.getArrivingAddress());

        for (ShipmentItemRequest reqItem : request.getItems()) {

            Item item = itemRepository.findById(reqItem.getItemId())
                    .orElseThrow(() ->
                    new NotFoundException("Item not found"));

            ShipmentItem shipmentItem = new ShipmentItem();
            shipmentItem.setItem(item);
            shipmentItem.setQuantity(reqItem.getQuantity());
            shipment.addItem(shipmentItem);
        }

        Shipment savedShipment =
                shipmentRepository.save(shipment);

        return ShipmentMapper.toResponse(savedShipment);
    }

    // Get Shipment by its Id
    public ShipmentResponse getShipmentById(Long shipmentId) {

        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> new NotFoundException("Shipment not found"));

        return ShipmentMapper.toResponse(shipment);
    }

    // Get All Shipments from One Client
    public List<ShipmentResponse> getClientShipments(Long clientId) {

        clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        return shipmentRepository
                .findByClientId(clientId)
                .stream()
                .map(ShipmentMapper::toResponse)
                .toList();
    }

    // Get specific shipment from Client
    public ShipmentResponse getClientShipment(Long clientId, Long shipmentId) {

        Shipment shipment = shipmentRepository.findByIdAndClientId(shipmentId, clientId)
                .orElseThrow(() -> new NotFoundException("Shipment not found for client"));

        return ShipmentMapper.toResponse(shipment);
    }

    // Update Status of shipment
    public ShipmentResponse updateShipmentStatus(Long shipmentId, ShipmentStatusRequest request) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                        .orElseThrow(() -> new NotFoundException("Shipment not found"));

        shipment.setStatus(request.getStatus());

        Shipment updated = shipmentRepository.save(shipment);

        return ShipmentMapper.toResponse(updated);
    }
}