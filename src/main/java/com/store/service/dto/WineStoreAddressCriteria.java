package com.store.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.store.domain.WineStoreAddress} entity. This class is used
 * in {@link com.store.web.rest.WineStoreAddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wine-store-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WineStoreAddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter street;

    private StringFilter postcode;

    private StringFilter city;

    private StringFilter region;

    private BigDecimalFilter latitude;

    private BigDecimalFilter longitude;

    private LongFilter wineStoreId;

    public WineStoreAddressCriteria() {
    }

    public WineStoreAddressCriteria(WineStoreAddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.street = other.street == null ? null : other.street.copy();
        this.postcode = other.postcode == null ? null : other.postcode.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.region = other.region == null ? null : other.region.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.wineStoreId = other.wineStoreId == null ? null : other.wineStoreId.copy();
    }

    @Override
    public WineStoreAddressCriteria copy() {
        return new WineStoreAddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getPostcode() {
        return postcode;
    }

    public void setPostcode(StringFilter postcode) {
        this.postcode = postcode;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getRegion() {
        return region;
    }

    public void setRegion(StringFilter region) {
        this.region = region;
    }

    public BigDecimalFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimalFilter latitude) {
        this.latitude = latitude;
    }

    public BigDecimalFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimalFilter longitude) {
        this.longitude = longitude;
    }

    public LongFilter getWineStoreId() {
        return wineStoreId;
    }

    public void setWineStoreId(LongFilter wineStoreId) {
        this.wineStoreId = wineStoreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WineStoreAddressCriteria that = (WineStoreAddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(street, that.street) &&
            Objects.equals(postcode, that.postcode) &&
            Objects.equals(city, that.city) &&
            Objects.equals(region, that.region) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(wineStoreId, that.wineStoreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        street,
        postcode,
        city,
        region,
        latitude,
        longitude,
        wineStoreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineStoreAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
                (postcode != null ? "postcode=" + postcode + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (region != null ? "region=" + region + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (wineStoreId != null ? "wineStoreId=" + wineStoreId + ", " : "") +
            "}";
    }

}
