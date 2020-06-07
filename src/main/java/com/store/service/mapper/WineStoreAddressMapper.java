package com.store.service.mapper;


import com.store.domain.*;
import com.store.service.dto.WineStoreAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WineStoreAddress} and its DTO {@link WineStoreAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {WineStoreMapper.class})
public interface WineStoreAddressMapper extends EntityMapper<WineStoreAddressDTO, WineStoreAddress> {

    @Mapping(source = "wineStore.id", target = "wineStoreId")
    WineStoreAddressDTO toDto(WineStoreAddress wineStoreAddress);

    @Mapping(source = "wineStoreId", target = "wineStore")
    WineStoreAddress toEntity(WineStoreAddressDTO wineStoreAddressDTO);

    default WineStoreAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        WineStoreAddress wineStoreAddress = new WineStoreAddress();
        wineStoreAddress.setId(id);
        return wineStoreAddress;
    }
}
