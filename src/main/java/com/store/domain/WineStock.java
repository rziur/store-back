package com.store.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A WineStock.
 */
@Entity
@Table(name = "wine_stock")
public class WineStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "region")
    private String region;

    @Column(name = "description")
    private String description;

    @Column(name = "vintage")
    private String vintage;

    @DecimalMin(value = "0")
    @DecimalMax(value = "90")
    @Column(name = "alcohol_level")
    private Float alcoholLevel;

    @Column(name = "print_run")
    private String printRun;

    @Column(name = "price")
    private Float price;

    @Column(name = "physical")
    private Integer physical;

    @Column(name = "purchases")
    private Integer purchases;

    @Column(name = "sales")
    private Integer sales;

    @Column(name = "availability")
    private Integer availability;

    @Column(name = "px_rev_col")
    private Float pxRevCol;

    @Column(name = "last_purchase_price")
    private Float lastPurchasePrice;

    @Column(name = "last_purchase_date")
    private Instant lastPurchaseDate;

    @Column(name = "date_import")
    private Instant dateImport;

    @Column(name = "image_url")
    private String imageUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public WineStock supplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRegion() {
        return region;
    }

    public WineStock region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public WineStock description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVintage() {
        return vintage;
    }

    public WineStock vintage(String vintage) {
        this.vintage = vintage;
        return this;
    }

    public void setVintage(String vintage) {
        this.vintage = vintage;
    }

    public Float getAlcoholLevel() {
        return alcoholLevel;
    }

    public WineStock alcoholLevel(Float alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
        return this;
    }

    public void setAlcoholLevel(Float alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
    }

    public String getPrintRun() {
        return printRun;
    }

    public WineStock printRun(String printRun) {
        this.printRun = printRun;
        return this;
    }

    public void setPrintRun(String printRun) {
        this.printRun = printRun;
    }

    public Float getPrice() {
        return price;
    }

    public WineStock price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getPhysical() {
        return physical;
    }

    public WineStock physical(Integer physical) {
        this.physical = physical;
        return this;
    }

    public void setPhysical(Integer physical) {
        this.physical = physical;
    }

    public Integer getPurchases() {
        return purchases;
    }

    public WineStock purchases(Integer purchases) {
        this.purchases = purchases;
        return this;
    }

    public void setPurchases(Integer purchases) {
        this.purchases = purchases;
    }

    public Integer getSales() {
        return sales;
    }

    public WineStock sales(Integer sales) {
        this.sales = sales;
        return this;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getAvailability() {
        return availability;
    }

    public WineStock availability(Integer availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public Float getPxRevCol() {
        return pxRevCol;
    }

    public WineStock pxRevCol(Float pxRevCol) {
        this.pxRevCol = pxRevCol;
        return this;
    }

    public void setPxRevCol(Float pxRevCol) {
        this.pxRevCol = pxRevCol;
    }

    public Float getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public WineStock lastPurchasePrice(Float lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
        return this;
    }

    public void setLastPurchasePrice(Float lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public Instant getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public WineStock lastPurchaseDate(Instant lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
        return this;
    }

    public void setLastPurchaseDate(Instant lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public Instant getDateImport() {
        return dateImport;
    }

    public WineStock dateImport(Instant dateImport) {
        this.dateImport = dateImport;
        return this;
    }

    public void setDateImport(Instant dateImport) {
        this.dateImport = dateImport;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public WineStock imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WineStock)) {
            return false;
        }
        return id != null && id.equals(((WineStock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineStock{" +
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
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
