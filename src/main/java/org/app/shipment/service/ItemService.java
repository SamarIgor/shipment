package org.app.shipment.service;

import org.app.shipment.dto.item.ItemRequest;
import org.app.shipment.dto.item.ItemResponse;
import org.app.shipment.exception.NotFoundException;
import org.app.shipment.mapper.ItemMapper;
import org.app.shipment.model.Item;
import org.app.shipment.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ItemService {

    private static final Logger log =
            LoggerFactory.getLogger(ItemService.class);

    public ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Page<ItemResponse> getAllItems(Pageable pageable) {
        Page<ItemResponse> itemResponses = itemRepository.findAll(pageable)
                .map(ItemMapper::toResponse);

        log.info("Successfully fetched {} items", itemResponses.getSize());
        return itemResponses;
    }

    public ItemResponse getItemById(Long id) {
        ItemResponse response = itemRepository.findById(id)
                .map(ItemMapper::toResponse)
                .orElseThrow(() -> {
                    log.warn("Cannot GET item with id '{}' ", id);
                    return new NotFoundException("Item by id "+id+" not found");
                });

        log.info("Found item with id '{}'", id);
        return response;
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
                    log.warn("Cannot find item with id '{}' to UPDATE", id);
                    return new NotFoundException("Item by id "+id+" not found");
                });

        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setWeight(request.getWeight());
        item.setSku(request.getSku());

        Item saved = itemRepository.save(item);

        log.info("Updated item with id '{}'", id);
        return ItemMapper.toResponse(saved);
    }

    public void deleteItem(Long id) {
        if(!itemRepository.existsById(id)){
            log.warn("Cannot find item with id '{}' to DELETE", id);
            throw new NotFoundException("Item with id "+id+" not found");
        }

        log.info("Deleted item with id '{}'", id);
        itemRepository.deleteById(id);
    }
}
