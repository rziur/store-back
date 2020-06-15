package com.store.service.impl;

import com.store.service.WineSaleService;
import com.store.domain.WineCustomer;
import com.store.domain.WineSale;
import com.store.repository.WineSaleRepository;
import com.store.service.dto.WineSaleDTO;
import com.store.service.mapper.WineSaleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WineSale}.
 */
@Service
@Transactional
public class WineSaleServiceImpl implements WineSaleService {

    private final Logger log = LoggerFactory.getLogger(WineSaleServiceImpl.class);

    private final WineSaleRepository wineSaleRepository;

    private final WineSaleMapper wineSaleMapper;

    public WineSaleServiceImpl(WineSaleRepository wineSaleRepository, WineSaleMapper wineSaleMapper) {
        this.wineSaleRepository = wineSaleRepository;
        this.wineSaleMapper = wineSaleMapper;
    }

    /**
     * Save a wineSale.
     *
     * @param wineSaleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WineSaleDTO save(WineSaleDTO wineSaleDTO) {
        log.debug("Request to save WineSale : {}", wineSaleDTO);
        WineSale wineSale = wineSaleMapper.toEntity(wineSaleDTO);
        wineSale = wineSaleRepository.save(wineSale);
        return wineSaleMapper.toDto(wineSale);
    }

    /**
     * Get all the wineSales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WineSaleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WineSales");
        return wineSaleRepository.findAll(pageable)
            .map(wineSaleMapper::toDto);
    }

    /**
     * Get all the wineSales.
     * @param wineCustomer
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WineSaleDTO> findAllByCustomer(WineCustomer wineCustomer,Pageable pageable) {
        log.debug("Request to get all WineSales by wineCustomer");
        return wineSaleRepository.findByWineCustomer(wineCustomer, pageable)
            .map(wineSaleMapper::toDto);
    }


    /**
     * Get one wineSale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WineSaleDTO> findOne(Long id) {
        log.debug("Request to get WineSale : {}", id);
        return wineSaleRepository.findById(id)
            .map(wineSaleMapper::toDto);
    }

    /**
     * Delete the wineSale by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WineSale : {}", id);
        wineSaleRepository.deleteById(id);
    }
}
