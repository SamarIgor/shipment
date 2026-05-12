package org.app.shipment.mapper;

import org.app.shipment.dto.app_user.AppUserRequest;
import org.app.shipment.dto.app_user.AppUserResponse;
import org.app.shipment.model.AppUser;

import java.time.LocalDateTime;

public class AppUserMapper {

    public static AppUser toEntity(AppUserRequest request){
        AppUser c = new AppUser();
        c.setEmail(request.getEmail());
        c.setFirstName(request.getFirstName());
        c.setLastName(request.getLastName());
        c.setCreatedAt(LocalDateTime.now());
        return c;
    }

    public static AppUserResponse toResponse(AppUser c){
        return new AppUserResponse(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getCreatedAt()
        );
    }
}
