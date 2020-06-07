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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.store.domain.WineStock} entity. This class is used
 * in {@link com.store.web.rest.WineStockResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wine-stocks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WineStockCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter supplier;

    private StringFilter region;

    private StringFilter description;

    private StringFilter vintage;

    private FloatFilter alcoholLevel;

    private StringFilter printRun;

    private FloatFilter price;

    private IntegerFilter physical;

    private IntegerFilter purchases;

    private IntegerFilter sales;

    private IntegerFilter availability;

    private FloatFilter pxRevCol;

    private FloatFilter lastPurchasePrice;

    private InstantFilter lastPurchaseDate;

    private InstantFilter dateImport;

    public WineStockCriteria() {
    }

    public WineStockCriteria(WineStockCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.supplier = other.supplier == null ? null : other.supplier.copy();
        this.region = other.region == null ? null : other.region.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.vintage = other.vintage == null ? null : other.vintage.copy();
        this.alcoholLevel = other.alcoholLevel == null ? null : other.alcoholLevel.copy();
        this.printRun = other.printRun == null ? null : other.printRun.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.physical = other.physical == null ? null : other.physical.copy();
        this.purchases = other.purchases == null ? null : other.purchases.copy();
        this.sales = other.sales == null ? null : other.sales.copy();
        this.availability = other.availability == null ? null : other.availability.copy();
        this.pxRevCol = other.pxRevCol == null ? null : other.pxRevCol.copy();
        this.lastPurchasePrice = other.lastPurchasePrice == null ? null : other.lastPurchasePrice.copy();
        this.lastPurchaseDate = other.lastPurchaseDate == null ? null : other.lastPurchaseDate.copy();
        this.dateImport = other.dateImport == null ? null : other.dateImport.copy();
    }

    @Override
    public WineStockCriteria copy() {
        return new WineStockCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSupplier() {
        return supplier;
    }

    public void setSupplier(StringFilter supplier) {
        this.supplier = supplier;
    }

    public StringFilter getRegion() {
        return region;
    }

    public void setRegion(StringFilter region) {
        this.region = region;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getVintage() {
        return vintage;
    }

    public void setVintage(StringFilter vintage) {
        this.vintage = vintage;
    }

    public FloatFilter getAlcoholLevel() {
        return alcoholLevel;
    }

    public void setAlcoholLevel(FloatFilter alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
    }

    public StringFilter getPrintRun() {
        return printRun;
    }

    public void setPrintRun(StringFilter printRun) {
        this.printRun = printRun;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public IntegerFilter getPhysical() {
        return physical;
    }

    public void setPhysical(IntegerFilter physical) {
        this.physical = physical;
    }

    public IntegerFilter getPurchases() {
        return purchases;
    }

    public void setPurchases(IntegerFilter purchases) {
        this.purchases = purchases;
    }

    public IntegerFilter getSales() {
        return sales;
    }

    public void setSales(IntegerFilter sales) {
        this.sales = sales;
    }

    public IntegerFilter getAvailability() {
        return availability;
    }

    public void setAvailability(IntegerFilter availability) {
        this.availability = availability;
    }

    public FloatFilter getPxRevCol() {
        return pxRevCol;
    }

    public void setPxRevCol(FloatFilter pxRevCol) {
        this.pxRevCol = pxRevCol;
    }

    public FloatFilter getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(FloatFilter lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public InstantFilter getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(InstantFilter lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public InstantFilter getDateImport() {
        return dateImport;
    }

    public void setDateImport(InstantFilter dateImport) {
        this.dateImport = dateImport;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WineStockCriteria that = (WineStockCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(supplier, that.supplier) &&
            Objects.equals(region, that.region) &&
            Objects.equals(description, that.description) &&
            Objects.equals(vintage, that.vintage) &&
            Objects.equals(alcoholLevel, that.alcoholLevel) &&
            Objects.equals(printRun, that.printRun) &&
            Objects.equals(price, that.price) &&
            Objects.equals(physical, that.physical) &&
            Objects.equals(purchases, that.purchases) &&
            Objects.equals(sales, that.sales) &&
            Objects.equals(availability, that.availability) &&
            Objects.equals(pxRevCol, that.pxRevCol) &&
            Objects.equals(lastPurchasePrice, that.lastPurchasePrice) &&
            Objects.equals(lastPurchaseDate, that.lastPurchaseDate) &&
            Objects.equals(dateImport, that.dateImport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        supplier,
        region,
        description,
        vintage,
        alcoholLevel,
        printRun,
        price,
        physical,
        purchases,
        sales,
        availability,
        pxRevCol,
        lastPurchasePrice,
        lastPurchaseDate,
        dateImport
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineStockCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (supplier != null ? "supplier=" + supplier + ", " : "") +
                (region != null ? "region=" + region + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (vintage != null ? "vintage=" + vintage + ", " : "") +
                (alcoholLevel != null ? "alcoholLevel=" + alcoholLevel + ", " : "") +
                (printRun != null ? "printRun=" + printRun + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (physical != null ? "physical=" + physical + ", " : "") +
                (purchases != null ? "purchases=" + purchases + ", " : "") +
                (sales != null ? "sales=" + sales + ", " : "") +
                (availability != null ? "availability=" + availability + ", " : "") +
                (pxRevCol != null ? "pxRevCol=" + pxRevCol + ", " : "") +
                (lastPurchasePrice != null ? "lastPurchasePrice=" + lastPurchasePrice + ", " : "") +
                (lastPurchaseDate != null ? "lastPurchaseDate=" + lastPurchaseDate + ", " : "") +
                (dateImport != null ? "dateImport=" + dateImport + ", " : "") +
            "}";
    }

}
