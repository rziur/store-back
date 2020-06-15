package com.store.service.mapper;


import com.store.domain.*;
import com.store.service.dto.WineOfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WineOffer} and its DTO {@link WineOfferDTO}.
 */
@Mapper(componentModel = "spring", uses = {WineStockMapper.class, WineStoreMapper.class})
public interface WineOfferMapper extends EntityMapper<WineOfferDTO, WineOffer> {

    @Mapping(source = "wineStock.id", target = "wineStockId")
    @Mapping(source = "wineStock.name", target = "wineStockName")
    @Mapping(source = "wineStock.supplier", target = "wineStockSupplier")
    @Mapping(source = "wineStock.region", target = "wineStockRegion")
    @Mapping(source = "wineStock.description", target = "wineStockDescription")
    @Mapping(source = "wineStock.imageUrl", target = "wineStockImageUrl")
    @Mapping(source = "wineStock.rating", target = "wineStockRating")
    @Mapping(source = "wineStock.voteCount", target = "wineStockVoteCount")
    @Mapping(source = "wineStock.sales", target = "wineStockSales")
    @Mapping(source = "wineStore.id", target = "wineStoreId")
    @Mapping(source = "wineStore.name", target = "wineStoreName")
    @Mapping(source = "wineStore.description", target = "wineStoreDescription")
    @Mapping(source = "wineStore.rating", target = "wineStoreRating")
    WineOfferDTO toDto(WineOffer wineOffer);

    @Mapping(source = "wineStockId", target = "wineStock")
    @Mapping(source = "wineStoreId", target = "wineStore")
    WineOffer toEntity(WineOfferDTO wineOfferDTO);

    default WineOffer fromId(Long id) {
        if (id == null) {
            return null;
        }
        WineOffer wineOffer = new WineOffer();
        wineOffer.setId(id);
        return wineOffer;
    }
}
