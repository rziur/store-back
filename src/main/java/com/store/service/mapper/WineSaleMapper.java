package com.store.service.mapper;


import com.store.domain.*;
import com.store.service.dto.WineSaleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WineSale} and its DTO {@link WineSaleDTO}.
 */
@Mapper(componentModel = "spring", uses = {WineCustomerMapper.class, WineStockMapper.class, WineStoreMapper.class})
public interface WineSaleMapper extends EntityMapper<WineSaleDTO, WineSale> {

    @Mapping(source = "wineCustomer.id", target = "wineCustomerId")
    @Mapping(source = "wineStock.id", target = "wineStockId")
    @Mapping(source = "wineStore.id", target = "wineStoreId")
    WineSaleDTO toDto(WineSale wineSale);

    @Mapping(source = "wineCustomerId", target = "wineCustomer")
    @Mapping(source = "wineStockId", target = "wineStock")
    @Mapping(source = "wineStoreId", target = "wineStore")
    WineSale toEntity(WineSaleDTO wineSaleDTO);

    default WineSale fromId(Long id) {
        if (id == null) {
            return null;
        }
        WineSale wineSale = new WineSale();
        wineSale.setId(id);
        return wineSale;
    }
}
