package com.store.service;

import com.store.service.dto.WineStockDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.store.domain.WineStock}.
 */
public interface WineStockService {

    /**
     * Save a wineStock.
     *
     * @param wineStockDTO the entity to save.
     * @return the persisted entity.
     */
    WineStockDTO save(WineStockDTO wineStockDTO);

    /**
     * Get all the wineStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WineStockDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wineStock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WineStockDTO> findOne(Long id);

    /**
     * Delete the "id" wineStock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
