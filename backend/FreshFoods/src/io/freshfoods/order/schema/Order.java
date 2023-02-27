
package io.freshfoods.order.schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("cart")
    @Expose
    private Cart cart;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("deliveryOptions")
    @Expose
    private DeliveryOptions deliveryOptions;
    @SerializedName("payment")
    @Expose
    private Payment payment;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DeliveryOptions getDeliveryOptions() {
        return deliveryOptions;
    }

    public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}
