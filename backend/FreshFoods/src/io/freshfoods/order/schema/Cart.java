
package io.freshfoods.order.schema;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("subTotal")
    @Expose
    private Double subTotal;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;
    @SerializedName("itemCount")
    @Expose
    private Integer itemCount;
    @SerializedName("delivery")
    @Expose
    private Double delivery;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Double getDelivery() {
        return delivery;
    }

    public void setDelivery(Double delivery) {
        this.delivery = delivery;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
