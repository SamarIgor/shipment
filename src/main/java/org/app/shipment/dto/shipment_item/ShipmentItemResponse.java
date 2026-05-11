package org.app.shipment.dto.shipment_item;

public class ShipmentItemResponse {

    private Long id;
    private String itemName;
    private int quantity;
    private float itemWeight;

    public ShipmentItemResponse(Long id, String itemName, int quantity, float itemWeight) {
        this.id = id;
        this.itemName = itemName;
        this.itemWeight = itemWeight;
        this.quantity = quantity;
    }


    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getItemWeight() {
        return itemWeight;
    }
}
