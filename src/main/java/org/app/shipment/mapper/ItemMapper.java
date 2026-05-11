package org.app.shipment.mapper;

import org.app.shipment.dto.item.ItemRequest;
import org.app.shipment.dto.item.ItemResponse;
import org.app.shipment.model.Item;

public class ItemMapper {

    public static Item toEntity(ItemRequest request){
        Item item = new Item();

        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setWeight(request.getWeight());
        item.setSku(request.getSku());

        return item;
    }

    public static ItemResponse toResponse(Item item){
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getWeight(),
                item.getSku());
    }
}
