package org.app.shipment.dto.shipment_item;

public class ShipmentItemRequest {

    private Long itemId;
    private int quantity;

    public ShipmentItemRequest(Long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public ShipmentItemRequest(){}

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
