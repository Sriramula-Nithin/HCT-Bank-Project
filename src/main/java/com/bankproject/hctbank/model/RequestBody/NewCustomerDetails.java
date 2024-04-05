package com.bankproject.hctbank.model.RequestBody;
import lombok.Data;


@Data

public class NewCustomerDetails {

    private String name;
    private String country;
    private String city;
    private String addressLine;
    private long pin;
    private long phone;
    private String email;

    public NewCustomerDetails(String name, String country, String city, String addressLine, long PIN, long phone, String email) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.addressLine = addressLine;
        this.pin = PIN;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "NewCustomerDetails{" +
                "Name='" + name + '\'' +
                ", Country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addressLane='" + addressLine + '\'' +
                ", PIN=" + pin +
                ", Phone=" + phone +
                ", Email='" + email + '\'' +
                '}';
    }
}
