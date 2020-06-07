package com.store.service.mapper;


import com.store.domain.*;
import com.store.service.dto.WineCustomerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WineCustomer} and its DTO {@link WineCustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface WineCustomerMapper extends EntityMapper<WineCustomerDTO, WineCustomer> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    WineCustomerDTO toDto(WineCustomer wineCustomer);

    @Mapping(source = "userId", target = "user")
    WineCustomer toEntity(WineCustomerDTO wineCustomerDTO);

    default WineCustomer fromId(Long id) {
        if (id == null) {
            return null;
        }
        WineCustomer wineCustomer = new WineCustomer();
        wineCustomer.setId(id);
        return wineCustomer;
    }
}
