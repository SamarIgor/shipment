package org.app.shipment.dto.app_user;

import java.time.LocalDateTime;

public class AppUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;

    public AppUserResponse(Long id, String firstName, String lastName, String email, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public AppUserResponse(){}

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
