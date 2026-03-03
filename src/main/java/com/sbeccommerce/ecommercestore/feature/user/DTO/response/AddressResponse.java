package com.sbeccommerce.ecommercestore.feature.user.DTO.response;

public class AddressResponse {

    private Long addressId;
    private Integer buildingNumber;
    private String street;
    private String city;
    private String region;
    private String postcode;
    private String country;

    public AddressResponse() {
    }

    public AddressResponse(Long addressId, Integer buildingNumber, String street, String city, String region, String postcode, String country) {
        this.addressId = addressId;
        this.buildingNumber = buildingNumber;
        this.street = street;
        this.city = city;
        this.region = region;
        this.postcode = postcode;
        this.country = country;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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
