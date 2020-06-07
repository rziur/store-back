package com.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WineSale.
 */
@Entity
@Table(name = "wine_sale")
public class WineSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipping_description")
    private String shippingDescription;

    @Column(name = "shipping_amount")
    private Integer shippingAmount;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "total")
    private Float total;

    @Column(name = "state")
    private Integer state;

    @ManyToOne
    @JsonIgnoreProperties(value = "wineSales", allowSetters = true)
    private WineCustomer wineCustomer;

    @ManyToOne
    @JsonIgnoreProperties(value = "wineSales", allowSetters = true)
    private WineStock wineStock;

    @ManyToOne
    @JsonIgnoreProperties(value = "wineSales", allowSetters = true)
    private WineStore wineStore;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingDescription() {
        return shippingDescription;
    }

    public WineSale shippingDescription(String shippingDescription) {
        this.shippingDescription = shippingDescription;
        return this;
    }

    public void setShippingDescription(String shippingDescription) {
        this.shippingDescription = shippingDescription;
    }

    public Integer getShippingAmount() {
        return shippingAmount;
    }

    public WineSale shippingAmount(Integer shippingAmount) {
        this.shippingAmount = shippingAmount;
        return this;
    }

    public void setShippingAmount(Integer shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public Float getDiscount() {
        return discount;
    }

    public WineSale discount(Float discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTotal() {
        return total;
    }

    public WineSale total(Float total) {
        this.total = total;
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public WineSale state(Integer state) {
        this.state = state;
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public WineCustomer getWineCustomer() {
        return wineCustomer;
    }

    public WineSale wineCustomer(WineCustomer wineCustomer) {
        this.wineCustomer = wineCustomer;
        return this;
    }

    public void setWineCustomer(WineCustomer wineCustomer) {
        this.wineCustomer = wineCustomer;
    }

    public WineStock getWineStock() {
        return wineStock;
    }

    public WineSale wineStock(WineStock wineStock) {
        this.wineStock = wineStock;
        return this;
    }

    public void setWineStock(WineStock wineStock) {
        this.wineStock = wineStock;
    }

    public WineStore getWineStore() {
        return wineStore;
    }

    public WineSale wineStore(WineStore wineStore) {
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
        if (!(o instanceof WineSale)) {
            return false;
        }
        return id != null && id.equals(((WineSale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineSale{" +
            "id=" + getId() +
            ", shippingDescription='" + getShippingDescription() + "'" +
            ", shippingAmount=" + getShippingAmount() +
            ", discount=" + getDiscount() +
            ", total=" + getTotal() +
            ", state=" + getState() +
            "}";
    }
}
