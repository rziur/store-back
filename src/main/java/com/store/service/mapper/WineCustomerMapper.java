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
    @Mapping(source = "user.login", target = "login")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.imageUrl", target = "imageUrl")
    @Mapping(source = "user.activated", target = "activated")
    @Mapping(source = "user.langKey", target = "langKey")
    @Mapping(source = "user.createdBy", target = "createdBy")
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.lastModifiedBy", target = "lastModifiedBy")
    @Mapping(source = "user.lastModifiedDate", target = "lastModifiedDate")
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
