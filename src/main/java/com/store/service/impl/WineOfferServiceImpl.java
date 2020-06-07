package com.store.service.impl;

import com.store.service.WineOfferService;
import com.store.domain.WineOffer;
import com.store.repository.WineOfferRepository;
import com.store.service.dto.WineOfferDTO;
import com.store.service.mapper.WineOfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WineOffer}.
 */
@Service
@Transactional
public class WineOfferServiceImpl implements WineOfferService {

    private final Logger log = LoggerFactory.getLogger(WineOfferServiceImpl.class);

    private final WineOfferRepository wineOfferRepository;

    private final WineOfferMapper wineOfferMapper;

    public WineOfferServiceImpl(WineOfferRepository wineOfferRepository, WineOfferMapper wineOfferMapper) {
        this.wineOfferRepository = wineOfferRepository;
        this.wineOfferMapper = wineOfferMapper;
    }

    /**
     * Save a wineOffer.
     *
     * @param wineOfferDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WineOfferDTO save(WineOfferDTO wineOfferDTO) {
        log.debug("Request to save WineOffer : {}", wineOfferDTO);
        WineOffer wineOffer = wineOfferMapper.toEntity(wineOfferDTO);
        wineOffer = wineOfferRepository.save(wineOffer);
        return wineOfferMapper.toDto(wineOffer);
    }

    /**
     * Get all the wineOffers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WineOfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WineOffers");
        return wineOfferRepository.findAll(pageable)
            .map(wineOfferMapper::toDto);
    }


    /**
     * Get one wineOffer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WineOfferDTO> findOne(Long id) {
        log.debug("Request to get WineOffer : {}", id);
        return wineOfferRepository.findById(id)
            .map(wineOfferMapper::toDto);
    }

    /**
     * Delete the wineOffer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WineOffer : {}", id);
        wineOfferRepository.deleteById(id);
    }
}
