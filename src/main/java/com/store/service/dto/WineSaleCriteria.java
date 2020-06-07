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
 * Criteria class for the {@link com.store.domain.WineSale} entity. This class is used
 * in {@link com.store.web.rest.WineSaleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wine-sales?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WineSaleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter shippingDescription;

    private IntegerFilter shippingAmount;

    private FloatFilter discount;

    private FloatFilter total;

    private IntegerFilter state;

    private LongFilter wineCustomerId;

    private LongFilter wineStockId;

    private LongFilter wineStoreId;

    public WineSaleCriteria() {
    }

    public WineSaleCriteria(WineSaleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.shippingDescription = other.shippingDescription == null ? null : other.shippingDescription.copy();
        this.shippingAmount = other.shippingAmount == null ? null : other.shippingAmount.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.wineCustomerId = other.wineCustomerId == null ? null : other.wineCustomerId.copy();
        this.wineStockId = other.wineStockId == null ? null : other.wineStockId.copy();
        this.wineStoreId = other.wineStoreId == null ? null : other.wineStoreId.copy();
    }

    @Override
    public WineSaleCriteria copy() {
        return new WineSaleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getShippingDescription() {
        return shippingDescription;
    }

    public void setShippingDescription(StringFilter shippingDescription) {
        this.shippingDescription = shippingDescription;
    }

    public IntegerFilter getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(IntegerFilter shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public FloatFilter getDiscount() {
        return discount;
    }

    public void setDiscount(FloatFilter discount) {
        this.discount = discount;
    }

    public FloatFilter getTotal() {
        return total;
    }

    public void setTotal(FloatFilter total) {
        this.total = total;
    }

    public IntegerFilter getState() {
        return state;
    }

    public void setState(IntegerFilter state) {
        this.state = state;
    }

    public LongFilter getWineCustomerId() {
        return wineCustomerId;
    }

    public void setWineCustomerId(LongFilter wineCustomerId) {
        this.wineCustomerId = wineCustomerId;
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
        final WineSaleCriteria that = (WineSaleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(shippingDescription, that.shippingDescription) &&
            Objects.equals(shippingAmount, that.shippingAmount) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(total, that.total) &&
            Objects.equals(state, that.state) &&
            Objects.equals(wineCustomerId, that.wineCustomerId) &&
            Objects.equals(wineStockId, that.wineStockId) &&
            Objects.equals(wineStoreId, that.wineStoreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        shippingDescription,
        shippingAmount,
        discount,
        total,
        state,
        wineCustomerId,
        wineStockId,
        wineStoreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineSaleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (shippingDescription != null ? "shippingDescription=" + shippingDescription + ", " : "") +
                (shippingAmount != null ? "shippingAmount=" + shippingAmount + ", " : "") +
                (discount != null ? "discount=" + discount + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (wineCustomerId != null ? "wineCustomerId=" + wineCustomerId + ", " : "") +
                (wineStockId != null ? "wineStockId=" + wineStockId + ", " : "") +
                (wineStoreId != null ? "wineStoreId=" + wineStoreId + ", " : "") +
            "}";
    }

}
