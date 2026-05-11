package org.app.shipment.dto.client;

import org.app.shipment.dto.shipment.ShipmentRequest;
import org.app.shipment.dto.shipment.ShipmentResponse;
import org.app.shipment.dto.shipment.ShipmentStatusRequest;
import org.app.shipment.service.ShipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ShipmentController {

    private ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService){
        this.shipmentService = shipmentService;
    }

    @GetMapping("/clients/{clientId}/shipments")
    public List<ShipmentResponse> getClientsShipments(@PathVariable Long clientId){
        return shipmentService.getClientShipments(clientId);
    }

    @GetMapping("/clients/{clientId}/shipments/{shipmentId}")
    public ShipmentResponse getClientShipment(
            @PathVariable Long clientId,
            @PathVariable Long shipmentId) {
        return shipmentService.getClientShipment(clientId, shipmentId);
    }

    @GetMapping("/shipments/{shipmentId}")
    public ShipmentResponse getShipmentById(@PathVariable Long shipmentId) {
        return shipmentService.getShipmentById(shipmentId);
    }

    @PostMapping("/shipment")
    public ShipmentResponse createShipment(@RequestBody ShipmentRequest request) {
        return shipmentService.createShipment(request);
    }

    @PatchMapping("/shipments/{shipmentId}/status")
    public ShipmentResponse updateShipmentStatus(
            @PathVariable Long shipmentId,
            @RequestBody ShipmentStatusRequest request
    ) {
        return shipmentService.updateShipmentStatus(shipmentId, request);
    }
}
