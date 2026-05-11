package org.app.shipment.dto.item;

public class ItemResponse {

    private Long id;

    private String name;

    private String category;

    private float weight;

    private String sku;

    public ItemResponse(Long id, String name, String category, float weight, String sku) {
        this.id = id;
        this.sku = sku;
        this.category = category;
        this.weight = weight;
        this.name = name;
    }

    public ItemResponse(){}

    public Long getId() {
        return id;
    }

    public float getWeight() {
        return weight;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }
}
