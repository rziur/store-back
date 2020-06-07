package com.store.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.store.domain.WineStock} entity.
 */
public class WineStockDTO implements Serializable {
    
    private Long id;

    private String supplier;

    private String region;

    private String description;

    private String vintage;

    @DecimalMin(value = "0")
    @DecimalMax(value = "90")
    private Float alcoholLevel;

    private String printRun;

    private Float price;

    private Integer physical;

    private Integer purchases;

    private Integer sales;

    private Integer availability;

    private Float pxRevCol;

    private Float lastPurchasePrice;

    private Instant lastPurchaseDate;

    private Instant dateImport;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVintage() {
        return vintage;
    }

    public void setVintage(String vintage) {
        this.vintage = vintage;
    }

    public Float getAlcoholLevel() {
        return alcoholLevel;
    }

    public void setAlcoholLevel(Float alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
    }

    public String getPrintRun() {
        return printRun;
    }

    public void setPrintRun(String printRun) {
        this.printRun = printRun;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getPhysical() {
        return physical;
    }

    public void setPhysical(Integer physical) {
        this.physical = physical;
    }

    public Integer getPurchases() {
        return purchases;
    }

    public void setPurchases(Integer purchases) {
        this.purchases = purchases;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public Float getPxRevCol() {
        return pxRevCol;
    }

    public void setPxRevCol(Float pxRevCol) {
        this.pxRevCol = pxRevCol;
    }

    public Float getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(Float lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public Instant getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(Instant lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public Instant getDateImport() {
        return dateImport;
    }

    public void setDateImport(Instant dateImport) {
        this.dateImport = dateImport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WineStockDTO)) {
            return false;
        }

        return id != null && id.equals(((WineStockDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineStockDTO{" +
            "id=" + getId() +
            ", supplier='" + getSupplier() + "'" +
            ", region='" + getRegion() + "'" +
            ", description='" + getDescription() + "'" +
            ", vintage='" + getVintage() + "'" +
            ", alcoholLevel=" + getAlcoholLevel() +
            ", printRun='" + getPrintRun() + "'" +
            ", price=" + getPrice() +
            ", physical=" + getPhysical() +
            ", purchases=" + getPurchases() +
            ", sales=" + getSales() +
            ", availability=" + getAvailability() +
            ", pxRevCol=" + getPxRevCol() +
            ", lastPurchasePrice=" + getLastPurchasePrice() +
            ", lastPurchaseDate='" + getLastPurchaseDate() + "'" +
            ", dateImport='" + getDateImport() + "'" +
            "}";
    }
}
