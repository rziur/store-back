package com.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WineOffer.
 */
@Entity
@Table(name = "wine_offer")
public class WineOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "price")
    private Float price;

    @Column(name = "special_price")
    private Float specialPrice;

    @ManyToOne
    @JsonIgnoreProperties(value = "wineOffers", allowSetters = true)
    private WineStock wineStock;

    @ManyToOne
    @JsonIgnoreProperties(value = "wineOffers", allowSetters = true)
    private WineStore wineStore;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsAvailable() {
        return isAvailable;
    }

    public WineOffer isAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Float getPrice() {
        return price;
    }

    public WineOffer price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSpecialPrice() {
        return specialPrice;
    }

    public WineOffer specialPrice(Float specialPrice) {
        this.specialPrice = specialPrice;
        return this;
    }

    public void setSpecialPrice(Float specialPrice) {
        this.specialPrice = specialPrice;
    }

    public WineStock getWineStock() {
        return wineStock;
    }

    public WineOffer wineStock(WineStock wineStock) {
        this.wineStock = wineStock;
        return this;
    }

    public void setWineStock(WineStock wineStock) {
        this.wineStock = wineStock;
    }

    public WineStore getWineStore() {
        return wineStore;
    }

    public WineOffer wineStore(WineStore wineStore) {
        this.wineStore = wineStore;
        return this;
    }

    public void setWineStore(WineStore wineStore) {
        this.wineStore = wineStore;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WineOffer)) {
            return false;
        }
        return id != null && id.equals(((WineOffer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineOffer{" +
            "id=" + getId() +
            ", isAvailable='" + isIsAvailable() + "'" +
            ", price=" + getPrice() +
            ", specialPrice=" + getSpecialPrice() +
            "}";
    }
}
