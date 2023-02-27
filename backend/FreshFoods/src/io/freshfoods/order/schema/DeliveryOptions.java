
package io.freshfoods.order.schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryOptions {

    @SerializedName("deliveryType")
    @Expose
    private Double deliveryType;
    @SerializedName("deliveryDate")
    @Expose
    private String deliveryDate;

    public Double getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Double deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}
