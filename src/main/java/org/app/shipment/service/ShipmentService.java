package org.app.shipment.service;

import org.app.shipment.controller.ItemController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ClientRepository clientRepository;
    private final ItemRepository itemRepository;
    private static final Logger log =
            LoggerFactory.getLogger(ShipmentService.class);

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
                .orElseThrow(() -> {
                    log.warn("Cannot find Client by id '{}' in creating the Shipment", request.getClientId());
                    return new NotFoundException("Client not found");
                });

        Shipment shipment = new Shipment();

        shipment.setClient(client);
        shipment.setStatus("CREATED");
        shipment.setSendingAddress(request.getSendingAddress());
        shipment.setArrivingAddress(request.getArrivingAddress());

        log.info("Created new Shipment '{}'", shipment.getId());

        for (ShipmentItemRequest reqItem : request.getItems()) {
            Item item = itemRepository.findById(reqItem.getItemId())
                    .orElseThrow(() -> {
                        log.warn("Cannot find Shipment Item with id '{}'", reqItem.getItemId());
                        return new NotFoundException("Item not found");
                    });

            ShipmentItem shipmentItem = new ShipmentItem();
            shipmentItem.setItem(item);
            shipmentItem.setQuantity(reqItem.getQuantity());
            shipment.addItem(shipmentItem);

            log.info("Added Item '{}' to Shipment", reqItem.getItemId());
        }

        Shipment savedShipment = shipmentRepository.save(shipment);

        log.info("Saved new Shipment '{}'", savedShipment.getId());
        return ShipmentMapper.toResponse(savedShipment);
    }

    // Get Shipment by its Id
    public ShipmentResponse getShipmentById(Long shipmentId) {

        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> {
                    log.warn("Cannot find Shipment by it's id '{}'", shipmentId);
                    return new NotFoundException("Shipment not found");
                });

        log.info("Found Shipment '{}'", shipmentId);
        return ShipmentMapper.toResponse(shipment);
    }

    // Get All Shipments from One Client
    public Page<ShipmentResponse> getClientShipments(Long clientId, Pageable pageable) {

        clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.warn("Cannot find Client '{}' to show their Shipments", clientId);
                    return new NotFoundException("Client not found");
                });

        Page<ShipmentResponse> responses = shipmentRepository
                .findByClientId(clientId, pageable)
                .map(ShipmentMapper::toResponse);

        log.info("Successfully found '{}' Shipments", responses.getSize());
        return responses;
    }

    // Get specific shipment from Client
    public ShipmentResponse getClientShipment(Long clientId, Long shipmentId) {

        Shipment shipment = shipmentRepository.findByIdAndClientId(shipmentId, clientId)
                .orElseThrow(() -> {
                    log.warn("Cannot find Client '{}' for specific Shipment", clientId);
                    return new NotFoundException("Shipment not found for client");
                });

        log.info("Found Shipment '{}' for Client '{}'", shipmentId, clientId);
        return ShipmentMapper.toResponse(shipment);
    }

    // Update Status of shipment
    public ShipmentResponse updateShipmentStatus(Long shipmentId, ShipmentStatusRequest request) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                        .orElseThrow(() -> {
                            log.warn("Cannot find Shipment '{}' to UPDATE", shipmentId);
                            return new NotFoundException("Shipment not found");
                        });

        shipment.setStatus(request.getStatus());

        Shipment updated = shipmentRepository.save(shipment);

        log.info("Changed Shipment status to '{}'", updated.getStatus());
        return ShipmentMapper.toResponse(updated);
    }
}