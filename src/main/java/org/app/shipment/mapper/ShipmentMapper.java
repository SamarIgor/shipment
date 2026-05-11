package org.app.shipment.mapper;

import org.app.shipment.dto.shipment.ShipmentResponse;
import org.app.shipment.dto.shipment_item.ShipmentItemResponse;
import org.app.shipment.model.Shipment;

import java.util.List;
import java.util.stream.Collectors;

public class ShipmentMapper {

    public static ShipmentResponse toResponse(Shipment shipment) {

        List<ShipmentItemResponse> items =
                shipment.getItems()
                        .stream()
                        .map(ShipmentItemMapper::toResponse)
                        .collect(Collectors.toList());

        return new ShipmentResponse(
                shipment.getId(),
                items,
                shipment.getCreatedAt(),
                shipment.getTotalWeight(),
                shipment.getStatus(),
                shipment.getSendingAddress(),
                shipment.getArrivingAddress()
        );
    }
}