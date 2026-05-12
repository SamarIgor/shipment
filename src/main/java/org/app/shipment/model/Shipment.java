package org.app.shipment.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sendingAddress;
    private String arrivingAddress;
    private String status;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShipmentItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public Shipment() {
        this.createdAt = LocalDateTime.now();
    }

    public Shipment(String sendingAddress, String arrivingAddress) {
        this.status = "CREATED";
        this.sendingAddress = sendingAddress;
        this.arrivingAddress = arrivingAddress;
        this.createdAt = LocalDateTime.now();
    }

    public void addItem(ShipmentItem item) {
        items.add(item);
        item.setShipment(this);
    }

    public float getTotalWeight() {
        float total = 0;

        for (ShipmentItem shipmentItem : items) {

            total += shipmentItem.getItem().getWeight()
                    * shipmentItem.getQuantity();
        }

        return total;
    }

    public List<ShipmentItem> getItems() {
        return items;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getArrivingAddress() {
        return arrivingAddress;
    }

    public String getSendingAddress() {
        return sendingAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSendingAddress(String sendingAddress) {
        this.sendingAddress = sendingAddress;
    }

    public void setArrivingAddress(String arrivingAddress) {
        this.arrivingAddress = arrivingAddress;
    }

    public void setItems(List<ShipmentItem> items) {
        this.items = items;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
