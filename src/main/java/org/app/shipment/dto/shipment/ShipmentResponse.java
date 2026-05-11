package org.app.shipment.dto.shipment;

import org.app.shipment.dto.shipment_item.ShipmentItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ShipmentResponse {

    private Long id;
    private String sendingAddress;
    private String arrivingAddress;
    private String status;
    private float totalWeight;
    private LocalDateTime createdAt;
    private List<ShipmentItemResponse> items;

    public ShipmentResponse(Long id, List<ShipmentItemResponse> items,
                            LocalDateTime createdAt,
                            float totalWeight, String status,
                            String sendingAddress, String arrivingAddress) {
        this.id = id;
        this.items = items;
        this.createdAt = createdAt;
        this.totalWeight = totalWeight;
        this.status = status;
        this.sendingAddress = sendingAddress;
        this.arrivingAddress = arrivingAddress;
    }

    public Long getId() {
        return id;
    }

    public List<ShipmentItemResponse> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public String getStatus() {
        return status;
    }

    public String getArrivingAddress() {
        return arrivingAddress;
    }

    public String getSendingAddress() {
        return sendingAddress;
    }
}
