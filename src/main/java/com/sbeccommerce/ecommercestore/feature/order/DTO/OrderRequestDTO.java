package com.sbeccommerce.ecommercestore.feature.order.DTO;

public class OrderRequestDTO {

    private Long addressId;
    private String paymentMethod;
    private String pgName;
    private String pgPaymentId;
    private String pgResponseMessage;
    private String pgStatus;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgResponseMessage, String pgStatus) {
        this.addressId = addressId;
        this.paymentMethod = paymentMethod;
        this.pgName = pgName;
        this.pgPaymentId = pgPaymentId;
        this.pgResponseMessage = pgResponseMessage;
        this.pgStatus = pgStatus;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgPaymentId() {
        return pgPaymentId;
    }

    public void setPgPaymentId(String pgPaymentId) {
        this.pgPaymentId = pgPaymentId;
    }

    public String getPgResponseMessage() {
        return pgResponseMessage;
    }

    public void setPgResponseMessage(String pgResponseMessage) {
        this.pgResponseMessage = pgResponseMessage;
    }

    public String getPgStatus() {
        return pgStatus;
    }

    public void setPgStatus(String pgStatus) {
        this.pgStatus = pgStatus;
    }


}
