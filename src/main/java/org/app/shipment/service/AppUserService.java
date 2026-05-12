package org.app.shipment.service;

import org.app.shipment.dto.app_user.AppUserRequest;
import org.app.shipment.dto.app_user.AppUserResponse;
import org.app.shipment.exception.NotFoundException;
import org.app.shipment.mapper.AppUserMapper;
import org.app.shipment.model.AppUser;
import org.app.shipment.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    public AppUserRepository appUserRepository;
    private static final Logger log =
            LoggerFactory.getLogger(AppUserService.class);

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // GET all users
    public Page<AppUserResponse> getAllUsers(Pageable pageable) {
        Page<AppUserResponse> responses = appUserRepository.findAll(pageable)
                .map(AppUserMapper::toResponse);

        log.info("Fetched {} users", responses.getSize());
        return responses;
    }

    // POST Inserting new User
    public AppUserResponse insertUser(AppUserRequest request) {

        AppUser client = AppUserMapper.toEntity(request);
        AppUser saved = appUserRepository.save(client);

        log.info("Inserted new User '{}'", client.getFirstName());
        return AppUserMapper.toResponse(saved);
    }

    // GET/id Find User by their ID
    public AppUserResponse getUserById(Long id) {
        AppUserResponse response = appUserRepository.findById(id)
                .map(AppUserMapper::toResponse)
                .orElseThrow(() -> {
                    log.warn("Cannot GET User with id '{}'", id);
                    return new NotFoundException("User by id "+id+" not found");
                });

        log.info("Found User with id '{}'", response.getId());
        return response;
    }

    // PUT Update user
    public AppUserResponse updateUser(Long id, AppUserRequest request) {
        AppUser existing = appUserRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot find User with id '{}' to UPDATE", id);
                    return new NotFoundException("User by id "+id+" not found");
                });

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());

        AppUser updated = appUserRepository.save(existing);

        log.info("Updated User with id '{}'", updated.getId());
        return AppUserMapper.toResponse(updated);
    }

    public void deleteUser(Long id) {
        if(!appUserRepository.existsById(id)){
            log.warn("Cannot find User by id '{}' to DELETE", id);
            throw new NotFoundException("Cant find User by id "+id);
        }

        appUserRepository.deleteById(id);
        log.info("Deleted User with id '{}'", id);
    }
}
