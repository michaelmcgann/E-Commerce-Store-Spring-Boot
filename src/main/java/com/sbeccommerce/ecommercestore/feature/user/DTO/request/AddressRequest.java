package com.sbeccommerce.ecommercestore.feature.user.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class AddressRequest {

    @NotNull
    @PositiveOrZero
    private Integer buildingNumber;

    @NotBlank
    @Size(min = 3, message = "Street name must be 3 or more characters")
    private String street;

    @NotBlank
    @Size(min = 3, message = "City name must be 3 or more characters")
    private String city;

    @NotBlank
    @Size(min = 3, message = "Region name must be 3 or more characters")
    private String region;

    @NotBlank
    @Size(min = 3, message = "Postcode must be 3 or more characters")
    private String postcode;

    @NotBlank
    @Size(min = 3, message = "Country name must be 3 or more characters")
    private String country;

    public AddressRequest() {
    }

    public AddressRequest(Integer buildingNumber, String street, String city, String region, String postcode, String country) {
        this.buildingNumber = buildingNumber;
        this.street = street;
        this.city = city;
        this.region = region;
        this.postcode = postcode;
        this.country = country;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
