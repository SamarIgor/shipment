package org.app.shipment.dto.item;

public class ItemRequest {

    private String name;
    private String category;

    private float weight;


    private String sku;

    public ItemRequest(String name, String category, float weight, String sku){
        this.name = name;
        this.sku = sku;
        this.category = category;
        this.weight = weight;
    }

    public ItemRequest() {
    }

    public String getSku() {
        return sku;
    }

    public String getCategory() {
        return category;
    }

    public float getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

}
