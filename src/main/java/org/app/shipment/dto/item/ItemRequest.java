package org.app.shipment.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ItemRequest {

    @NotBlank(message = "Item name is required")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @Positive(message = "Weight must be positive")
    private float weight;

    @NotBlank(message = "SKU is required")
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
