package org.app.shipment.dto.shipment;

import org.app.shipment.dto.shipment_item.ShipmentItemRequest;

import java.util.List;

public class ShipmentRequest {

    private Long clientId;
    private String sendingAddress;
    private String arrivingAddress;
    private List<ShipmentItemRequest> items;

    public ShipmentRequest(){}

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<ShipmentItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ShipmentItemRequest> items) {
        this.items = items;
    }

    public String getArrivingAddress() {
        return arrivingAddress;
    }

    public void setArrivingAddress(String arrivingAddress) {
        this.arrivingAddress = arrivingAddress;
    }

    public String getSendingAddress() {
        return sendingAddress;
    }

    public void setSendingAddress(String sendingAddress) {
        this.sendingAddress = sendingAddress;
    }
}
