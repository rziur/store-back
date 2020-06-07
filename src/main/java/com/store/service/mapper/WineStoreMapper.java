package com.store.service.mapper;


import com.store.domain.*;
import com.store.service.dto.WineStoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WineStore} and its DTO {@link WineStoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WineStoreMapper extends EntityMapper<WineStoreDTO, WineStore> {



    default WineStore fromId(Long id) {
        if (id == null) {
            return null;
        }
        WineStore wineStore = new WineStore();
        wineStore.setId(id);
        return wineStore;
    }
}
