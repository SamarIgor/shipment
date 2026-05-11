package org.app.shipment.controller.service;

import org.app.shipment.dto.item.ItemRequest;
import org.app.shipment.dto.item.ItemResponse;
import org.app.shipment.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
public class ItemController {

    public ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public List<ItemResponse> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ItemResponse getItemById(@PathVariable Long id){
        return itemService.getItemById(id);
    }

    @PostMapping
    public ItemResponse addItem(ItemRequest request){
        return itemService.insertItem(request);
    }

    @PutMapping("/{id}")
    public ItemResponse updateItem(@PathVariable Long id, @RequestBody ItemRequest request){
        return itemService.updateItem(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
    }
}
