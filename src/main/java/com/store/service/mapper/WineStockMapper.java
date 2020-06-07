package com.store.service.mapper;


import com.store.domain.*;
import com.store.service.dto.WineStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WineStock} and its DTO {@link WineStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WineStockMapper extends EntityMapper<WineStockDTO, WineStock> {



    default WineStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        WineStock wineStock = new WineStock();
        wineStock.setId(id);
        return wineStock;
    }
}
