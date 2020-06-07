package com.store.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.store.domain.WineSale} entity.
 */
public class WineSaleDTO implements Serializable {
    
    private Long id;

    private String shippingDescription;

    private Integer shippingAmount;

    private Float discount;

    private Float total;

    private Integer state;


    private Long wineCustomerId;

    private Long wineStockId;

    private Long wineStoreId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingDescription() {
        return shippingDescription;
    }

    public void setShippingDescription(String shippingDescription) {
        this.shippingDescription = shippingDescription;
    }

    public Integer getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Integer shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getWineCustomerId() {
        return wineCustomerId;
    }

    public void setWineCustomerId(Long wineCustomerId) {
        this.wineCustomerId = wineCustomerId;
    }

    public Long getWineStockId() {
        return wineStockId;
    }

    public void setWineStockId(Long wineStockId) {
        this.wineStockId = wineStockId;
    }

    public Long getWineStoreId() {
        return wineStoreId;
    }

    public void setWineStoreId(Long wineStoreId) {
        this.wineStoreId = wineStoreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WineSaleDTO)) {
            return false;
        }

        return id != null && id.equals(((WineSaleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineSaleDTO{" +
            "id=" + getId() +
            ", shippingDescription='" + getShippingDescription() + "'" +
            ", shippingAmount=" + getShippingAmount() +
            ", discount=" + getDiscount() +
            ", total=" + getTotal() +
            ", state=" + getState() +
            ", wineCustomerId=" + getWineCustomerId() +
            ", wineStockId=" + getWineStockId() +
            ", wineStoreId=" + getWineStoreId() +
            "}";
    }
}
