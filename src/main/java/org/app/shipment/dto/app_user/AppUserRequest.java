package org.app.shipment.dto.app_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AppUserRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name can contain only letters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name can contain only letters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public AppUserRequest(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public AppUserRequest(){}

    public String getFirstName() {
        return firstName;
    }


    public String getEmail() {
        return email;
    }


    public String getLastName() {
        return lastName;
    }

}
