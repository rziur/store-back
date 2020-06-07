package com.store.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.store.domain.WineStoreAddress} entity.
 */
public class WineStoreAddressDTO implements Serializable {
    
    private Long id;

    private String street;

    private String postcode;

    private String city;

    private String region;

    private BigDecimal latitude;

    private BigDecimal longitude;


    private Long wineStoreId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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
        if (!(o instanceof WineStoreAddressDTO)) {
            return false;
        }

        return id != null && id.equals(((WineStoreAddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineStoreAddressDTO{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", city='" + getCity() + "'" +
            ", region='" + getRegion() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", wineStoreId=" + getWineStoreId() +
            "}";
    }
}
