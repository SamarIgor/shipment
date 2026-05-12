package org.app.shipment.service;

import org.app.shipment.dto.shipment.ShipmentRequest;
import org.app.shipment.dto.shipment.ShipmentResponse;
import org.app.shipment.dto.shipment.ShipmentStatusRequest;
import org.app.shipment.dto.shipment_item.ShipmentItemRequest;
import org.app.shipment.exception.NotFoundException;
import org.app.shipment.mapper.ShipmentMapper;
import org.app.shipment.model.AppUser;
import org.app.shipment.model.Item;
import org.app.shipment.model.Shipment;
import org.app.shipment.model.ShipmentItem;
import org.app.shipment.repository.AppUserRepository;
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
    private final AppUserRepository appUserRepository;
    private final ItemRepository itemRepository;
    private static final Logger log =
            LoggerFactory.getLogger(ShipmentService.class);

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            AppUserRepository appUserRepository,
            ItemRepository itemRepository
    ) {
        this.shipmentRepository = shipmentRepository;
        this.appUserRepository = appUserRepository;
        this.itemRepository = itemRepository;
    }

    // Adding new Shipment order
    public ShipmentResponse createShipment(ShipmentRequest request) {

        AppUser user = appUserRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.warn("Cannot find User by id '{}' in creating the Shipment", request.getUserId());
                    return new NotFoundException("User not found");
                });

        Shipment shipment = new Shipment();

        shipment.setUser(user);
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

    // Get Shipment by its ID
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
    public Page<ShipmentResponse> getUserShipments(Long userId, Pageable pageable) {

        appUserRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Cannot find User '{}' to show their Shipments", userId);
                    return new NotFoundException("Client not found");
                });

        Page<ShipmentResponse> responses = shipmentRepository
                .findByUserId(userId, pageable)
                .map(ShipmentMapper::toResponse);

        log.info("Successfully found '{}' Shipments", responses.getSize());
        return responses;
    }

    // Get specific shipment from User
    public ShipmentResponse getUserShipment(Long userId, Long shipmentId) {

        Shipment shipment = shipmentRepository.findByIdAndUserId(shipmentId, userId)
                .orElseThrow(() -> {
                    log.warn("Cannot find User '{}' for specific Shipment", userId);
                    return new NotFoundException("Shipment not found for User");
                });

        log.info("Found Shipment '{}' for User '{}'", shipmentId, userId);
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