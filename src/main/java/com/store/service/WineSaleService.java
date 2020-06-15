package com.store.service;

import com.store.domain.WineCustomer;
import com.store.service.dto.WineSaleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.store.domain.WineSale}.
 */
public interface WineSaleService {

    /**
     * Save a wineSale.
     *
     * @param wineSaleDTO the entity to save.
     * @return the persisted entity.
     */
    WineSaleDTO save(WineSaleDTO wineSaleDTO);

    /**
     * Get all the wineSales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WineSaleDTO> findAll(Pageable pageable);


    /**
     * Get all the wineSales.
     * @param wineCustomer
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<WineSaleDTO> findAllByCustomer(WineCustomer wineCustomer,Pageable pageable);


    /**
     * Get the "id" wineSale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WineSaleDTO> findOne(Long id);

    /**
     * Delete the "id" wineSale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
