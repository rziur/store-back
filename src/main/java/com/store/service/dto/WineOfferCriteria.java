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

/**
 * Criteria class for the {@link com.store.domain.WineOffer} entity. This class is used
 * in {@link com.store.web.rest.WineOfferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wine-offers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WineOfferCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isAvailable;

    private FloatFilter price;

    private FloatFilter specialPrice;

    private LongFilter wineStockId;

    private LongFilter wineStoreId;

    public WineOfferCriteria() {
    }

    public WineOfferCriteria(WineOfferCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isAvailable = other.isAvailable == null ? null : other.isAvailable.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.specialPrice = other.specialPrice == null ? null : other.specialPrice.copy();
        this.wineStockId = other.wineStockId == null ? null : other.wineStockId.copy();
        this.wineStoreId = other.wineStoreId == null ? null : other.wineStoreId.copy();
    }

    @Override
    public WineOfferCriteria copy() {
        return new WineOfferCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(BooleanFilter isAvailable) {
        this.isAvailable = isAvailable;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public FloatFilter getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(FloatFilter specialPrice) {
        this.specialPrice = specialPrice;
    }

    public LongFilter getWineStockId() {
        return wineStockId;
    }

    public void setWineStockId(LongFilter wineStockId) {
        this.wineStockId = wineStockId;
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
        final WineOfferCriteria that = (WineOfferCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isAvailable, that.isAvailable) &&
            Objects.equals(price, that.price) &&
            Objects.equals(specialPrice, that.specialPrice) &&
            Objects.equals(wineStockId, that.wineStockId) &&
            Objects.equals(wineStoreId, that.wineStoreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isAvailable,
        price,
        specialPrice,
        wineStockId,
        wineStoreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineOfferCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isAvailable != null ? "isAvailable=" + isAvailable + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (specialPrice != null ? "specialPrice=" + specialPrice + ", " : "") +
                (wineStockId != null ? "wineStockId=" + wineStockId + ", " : "") +
                (wineStoreId != null ? "wineStoreId=" + wineStoreId + ", " : "") +
            "}";
    }

}
