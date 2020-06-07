package com.store.service;

import com.store.service.dto.WineStoreAddressDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.store.domain.WineStoreAddress}.
 */
public interface WineStoreAddressService {

    /**
     * Save a wineStoreAddress.
     *
     * @param wineStoreAddressDTO the entity to save.
     * @return the persisted entity.
     */
    WineStoreAddressDTO save(WineStoreAddressDTO wineStoreAddressDTO);

    /**
     * Get all the wineStoreAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WineStoreAddressDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wineStoreAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WineStoreAddressDTO> findOne(Long id);

    /**
     * Delete the "id" wineStoreAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
