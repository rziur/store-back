package com.store.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.store.domain.WineOffer} entity.
 */
public class WineOfferDTO implements Serializable {
    
    private Long id;

    private Boolean isAvailable;

    private Float price;

    private Float specialPrice;


    private Long wineStockId;

    private Long wineStoreId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(Float specialPrice) {
        this.specialPrice = specialPrice;
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
        if (!(o instanceof WineOfferDTO)) {
            return false;
        }

        return id != null && id.equals(((WineOfferDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineOfferDTO{" +
            "id=" + getId() +
            ", isAvailable='" + isIsAvailable() + "'" +
            ", price=" + getPrice() +
            ", specialPrice=" + getSpecialPrice() +
            ", wineStockId=" + getWineStockId() +
            ", wineStoreId=" + getWineStoreId() +
            "}";
    }
}
