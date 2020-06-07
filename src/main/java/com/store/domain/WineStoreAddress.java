package com.store.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A WineStoreAddress.
 */
@Entity
@Table(name = "wine_store_address")
public class WineStoreAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "latitude", precision = 21, scale = 2)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 21, scale = 2)
    private BigDecimal longitude;

    @OneToOne
    @JoinColumn(unique = true)
    private WineStore wineStore;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public WineStoreAddress street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public WineStoreAddress postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public WineStoreAddress city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public WineStoreAddress region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public WineStoreAddress latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public WineStoreAddress longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public WineStore getWineStore() {
        return wineStore;
    }

    public WineStoreAddress wineStore(WineStore wineStore) {
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
        if (!(o instanceof WineStoreAddress)) {
            return false;
        }
        return id != null && id.equals(((WineStoreAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineStoreAddress{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", city='" + getCity() + "'" +
            ", region='" + getRegion() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
