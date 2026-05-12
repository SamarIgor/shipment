package org.app.shipment.dto.shipment;

import jakarta.validation.constraints.*;
import org.app.shipment.dto.shipment_item.ShipmentItemRequest;

import java.util.List;

public class ShipmentRequest {

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotBlank
    @Size(min = 5, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9À-ÿ\\s,./'-]+$",
            message = "Address contains invalid characters")
    private String sendingAddress;

    @NotBlank
    @Size(min = 5, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9À-ÿ\\s,./'-]+$",
            message = "Address contains invalid characters")
    private String arrivingAddress;

    @NotEmpty(message = "Shipment must contain items")
    private List<ShipmentItemRequest> items;

    public ShipmentRequest(){}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
