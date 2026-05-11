package org.app.shipment.mapper;

import org.app.shipment.dto.shipment_item.ShipmentItemResponse;
import org.app.shipment.model.ShipmentItem;

public class ShipmentItemMapper {

    public static ShipmentItemResponse toResponse(ShipmentItem shipmentItem) {

        return new ShipmentItemResponse(
                shipmentItem.getId(),
                shipmentItem.getItem().getName(),
                shipmentItem.getQuantity(),
                shipmentItem.getItem().getWeight()
        );
    }
}