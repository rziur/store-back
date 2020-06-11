package com.store.service;

import com.store.service.dto.WineCustomerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.store.domain.WineCustomer}.
 */
public interface WineCustomerService {

    /**
     * Save a wineCustomer.
     *
     * @param wineCustomerDTO the entity to save.
     * @return the persisted entity.
     */
    WineCustomerDTO save(WineCustomerDTO wineCustomerDTO);

    /**
     * Get all the wineCustomers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WineCustomerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wineCustomer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WineCustomerDTO> findOne(Long id);

    /**
     * Get the "id" wineCustomer by userId.
     *
     * @param userId the userId of the entity.
     * @return the entity.
     */
    Optional<WineCustomerDTO> findOneByUserId(Long userId);

    /**
     * Delete the "id" wineCustomer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
