package org.app.shipment.dto.client;

public class ClientRequest {

    private String firstName;
    private String lastName;
    private String email;

    public ClientRequest(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public ClientRequest(){}

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
