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
    @Mapping(source = "wineStore.id", target = "wineStoreId")
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
