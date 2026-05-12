package org.app.shipment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.app.shipment.api_response.ApiResponse;
import org.app.shipment.dto.item.ItemRequest;
import org.app.shipment.dto.item.ItemResponse;
import org.app.shipment.service.ItemService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/item")
public class ItemController {

    public ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<ItemResponse>>> getAllItems(
            @PageableDefault(size=10,sort = "id")
            @ParameterObject Pageable pageable,
            HttpServletRequest request
    ){
        Page<ItemResponse> response = itemService.getAllItems(pageable);

        ApiResponse<Page<ItemResponse>>  apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Items fetched successfully",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemResponse>> getItemById(
            @PathVariable Long id, HttpServletRequest request){

        ItemResponse item = itemService.getItemById(id);

        ApiResponse<ItemResponse>  apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Successfully fetched item with id '"+id+"'",
                request.getRequestURI(),
                item
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponse>> addItem(
           @RequestBody @Valid ItemRequest itemRequest, HttpServletRequest request){

        ItemResponse response = itemService.insertItem(itemRequest);

        ApiResponse<ItemResponse>  apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Inserted new item '"+response.getName()+"'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemResponse>> updateItem(
            @PathVariable Long id, @RequestBody @Valid ItemRequest itemRequest, HttpServletRequest request){

        ItemResponse response = itemService.updateItem(id, itemRequest);

        ApiResponse<ItemResponse>  apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Updated item with id '"+response.getId()+"'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(
            @PathVariable Long id, HttpServletRequest request){

        itemService.deleteItem(id);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "Item with id '"+id+"' deleted",
                        request.getRequestURI(),
                        null
                );

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
    }
}
