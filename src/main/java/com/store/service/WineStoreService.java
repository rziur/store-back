package com.store.service;

import com.store.service.dto.WineStoreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.store.domain.WineStore}.
 */
public interface WineStoreService {

    /**
     * Save a wineStore.
     *
     * @param wineStoreDTO the entity to save.
     * @return the persisted entity.
     */
    WineStoreDTO save(WineStoreDTO wineStoreDTO);

    /**
     * Get all the wineStores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WineStoreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wineStore.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WineStoreDTO> findOne(Long id);

    /**
     * Delete the "id" wineStore.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
