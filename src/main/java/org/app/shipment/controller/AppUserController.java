package org.app.shipment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.app.shipment.api_response.ApiResponse;
import org.app.shipment.dto.app_user.AppUserRequest;
import org.app.shipment.dto.app_user.AppUserResponse;
import org.app.shipment.service.AppUserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class AppUserController {

    public AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AppUserResponse>>> getUsers(
            @PageableDefault(size=10,sort = "id")
            @ParameterObject Pageable pageable,
            HttpServletRequest request){

        Page<AppUserResponse> response = appUserService.getAllUsers(pageable);

        ApiResponse<Page<AppUserResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Users fetched successfully",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppUserResponse>> getUserById(
            @PathVariable Long id, HttpServletRequest request){

        AppUserResponse response = appUserService.getUserById(id);

        ApiResponse<AppUserResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Users with id '"+id+"' fetched successfully",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AppUserResponse>> insertUser(
            @RequestBody @Valid AppUserRequest userRequest, HttpServletRequest request){

        AppUserResponse response = appUserService.insertUser(userRequest);

        ApiResponse<AppUserResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Added new user'"+userRequest.getFirstName()+"'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppUserResponse>> updateUser(
            @PathVariable Long id, HttpServletRequest request,
            @RequestBody @Valid AppUserRequest userRequest){
        AppUserResponse response = appUserService.updateUser(id, userRequest);

        ApiResponse<AppUserResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Updated user with id '"+id+"'",
                request.getRequestURI(),
                response
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id, HttpServletRequest request){

        appUserService.deleteUser(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Deleted user '"+id+"'",
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }
}
