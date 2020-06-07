package com.store.service;

import com.store.service.dto.WineOfferDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.store.domain.WineOffer}.
 */
public interface WineOfferService {

    /**
     * Save a wineOffer.
     *
     * @param wineOfferDTO the entity to save.
     * @return the persisted entity.
     */
    WineOfferDTO save(WineOfferDTO wineOfferDTO);

    /**
     * Get all the wineOffers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WineOfferDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wineOffer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WineOfferDTO> findOne(Long id);

    /**
     * Delete the "id" wineOffer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
