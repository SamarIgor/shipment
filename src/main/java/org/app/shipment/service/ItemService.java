package org.app.shipment.service;

import org.app.shipment.dto.client.ClientRequest;
import org.app.shipment.dto.client.ClientResponse;
import org.app.shipment.dto.item.ItemRequest;
import org.app.shipment.dto.item.ItemResponse;
import org.app.shipment.exception.NotFoundException;
import org.app.shipment.mapper.ClientMapper;
import org.app.shipment.mapper.ItemMapper;
import org.app.shipment.model.Client;
import org.app.shipment.model.Item;
import org.app.shipment.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private static final Logger log =
            LoggerFactory.getLogger(ItemService.class);

    public ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long id) {
        return itemRepository.findById(id)
                .map(ItemMapper::toResponse)
                .orElseThrow(() -> {
                    return new NotFoundException("Item by id "+id+" not found");
                });
    }

    public ItemResponse insertItem(ItemRequest request) {
        log.info("Adding new '{}' item", request.getName());
        Item item = ItemMapper.toEntity(request);
        Item saved = itemRepository.save(item);
        log.info("Saved '{}' into database", saved.getName());
        return ItemMapper.toResponse(saved);
    }

    public ItemResponse updateItem(Long id, ItemRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException("Item by id "+id+" not found");
                });

        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setWeight(request.getWeight());
        item.setSku(request.getSku());

        Item saved = itemRepository.save(item);

        return ItemMapper.toResponse(saved);
    }

    public void deleteItem(Long id) {
        if(!itemRepository.existsById(id)){
            throw new NotFoundException("Item with id "+id+" not found");
        }

        itemRepository.deleteById(id);
    }
}
