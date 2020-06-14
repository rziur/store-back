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

    private String wineStockSupplier;

    private String wineStockRegion;

    private String wineStockDescription;

    private String wineStockImageUrl;

    private Integer wineStockRating;

    private String wineStoreName;

    private String wineStoreDescription;

    private Integer wineStoreRating;


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

    public String getWineStockSupplier() {
        return wineStockSupplier;
    }

    public void setWineStockSupplier(String wineStockSupplier) {
        this.wineStockSupplier = wineStockSupplier;
    }

    public String getWineStockRegion() {
        return wineStockRegion;
    }

    public void setWineStockRegion(String wineStockRegion) {
        this.wineStockRegion = wineStockRegion;
    }

    public String getWineStockDescription() {
        return wineStockDescription;
    }

    public void setWineStockDescription(String wineStockDescription) {
        this.wineStockDescription = wineStockDescription;
    }

    public String getWineStockImageUrl() {
        return wineStockImageUrl;
    }

    public void setWineStockImageUrl(String wineStockImageUrl) {
        this.wineStockImageUrl = wineStockImageUrl;
    }

    public Integer getWineStockRating() {
        return wineStockRating;
    }

    public void setWineStockRating(Integer wineStockRating) {
        this.wineStockRating = wineStockRating;
    }

    public Long getWineStoreId() {
        return wineStoreId;
    }

    public void setWineStoreId(Long wineStoreId) {
        this.wineStoreId = wineStoreId;
    }

    public String getWineStoreName() {
        return wineStoreName;
    }

    public void setWineStoreName(String wineStoreName) {
        this.wineStoreName = wineStoreName;
    }

    public String getWineStoreDescription() {
        return wineStoreDescription;
    }

    public void setWineStoreDescription(String wineStoreDescription) {
        this.wineStoreDescription = wineStoreDescription;
    }

    public Integer getWineStoreRating() {
        return wineStoreRating;
    }

    public void setWineStoreRating(Integer wineStoreRating) {
        this.wineStoreRating = wineStoreRating;
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
