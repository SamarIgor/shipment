package org.app.shipment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.app.shipment.api_response.ApiResponse;
import org.app.shipment.dto.shipment.ShipmentRequest;
import org.app.shipment.dto.shipment.ShipmentResponse;
import org.app.shipment.dto.shipment.ShipmentStatusRequest;
import org.app.shipment.service.ShipmentService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ShipmentController {

    private ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService){
        this.shipmentService = shipmentService;
    }

    @GetMapping("/users/{userId}/shipments")
    public ResponseEntity<ApiResponse<Page<ShipmentResponse>>> getUsersShipments(
            @PathVariable Long userId, HttpServletRequest request,
            @PageableDefault(size=10,sort = "id") @ParameterObject Pageable pageable
    ){
        Page<ShipmentResponse> response = shipmentService.getUserShipments(userId, pageable);

        ApiResponse<Page<ShipmentResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Shipments for client '" + userId + "' fetched successfully",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @GetMapping("/users/{userId}/shipments/{shipmentId}")
    public ResponseEntity<ApiResponse<ShipmentResponse>> getUserShipment(
            @PathVariable Long userId, @PathVariable Long shipmentId,
            HttpServletRequest request) {

        ShipmentResponse response = shipmentService.getUserShipment(userId, shipmentId);

        ApiResponse<ShipmentResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Fetched Shipment '"+shipmentId+"' for User '" + userId + "'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @GetMapping("/shipments/{shipmentId}")
    public ResponseEntity<ApiResponse<ShipmentResponse>> getShipmentById(
            @PathVariable Long shipmentId, HttpServletRequest request) {

        ShipmentResponse response = shipmentService.getShipmentById(shipmentId);

        ApiResponse<ShipmentResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Fetched shipment '" + shipmentId + "'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PostMapping("/shipment")
    public ResponseEntity<ApiResponse<ShipmentResponse>> createShipment(
            @RequestBody @Valid ShipmentRequest shipmentRequest, HttpServletRequest request) {

        ShipmentResponse response = shipmentService.createShipment(shipmentRequest);

        ApiResponse<ShipmentResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Inserted shipment '" + response.getId() + "'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PatchMapping("/shipments/{shipmentId}/status")
    public ResponseEntity<ApiResponse<ShipmentResponse>> updateShipmentStatus(
            @PathVariable Long shipmentId, HttpServletRequest request,
            @RequestBody @Valid ShipmentStatusRequest shipmentStatusRequest
    ) {
        ShipmentResponse response = shipmentService.updateShipmentStatus(shipmentId, shipmentStatusRequest);

        ApiResponse<ShipmentResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Updated shipment status to '" + shipmentStatusRequest.getStatus() + "'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }
}
