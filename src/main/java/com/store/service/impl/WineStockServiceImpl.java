package com.store.service.impl;

import com.store.service.WineStockService;
import com.store.domain.WineStock;
import com.store.repository.WineStockRepository;
import com.store.service.dto.WineStockDTO;
import com.store.service.mapper.WineStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WineStock}.
 */
@Service
@Transactional
public class WineStockServiceImpl implements WineStockService {

    private final Logger log = LoggerFactory.getLogger(WineStockServiceImpl.class);

    private final WineStockRepository wineStockRepository;

    private final WineStockMapper wineStockMapper;

    public WineStockServiceImpl(WineStockRepository wineStockRepository, WineStockMapper wineStockMapper) {
        this.wineStockRepository = wineStockRepository;
        this.wineStockMapper = wineStockMapper;
    }

    /**
     * Save a wineStock.
     *
     * @param wineStockDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WineStockDTO save(WineStockDTO wineStockDTO) {
        log.debug("Request to save WineStock : {}", wineStockDTO);
        WineStock wineStock = wineStockMapper.toEntity(wineStockDTO);
        wineStock = wineStockRepository.save(wineStock);
        return wineStockMapper.toDto(wineStock);
    }

    /**
     * Get all the wineStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WineStockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WineStocks");
        return wineStockRepository.findAll(pageable)
            .map(wineStockMapper::toDto);
    }


    /**
     * Get one wineStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WineStockDTO> findOne(Long id) {
        log.debug("Request to get WineStock : {}", id);
        return wineStockRepository.findById(id)
            .map(wineStockMapper::toDto);
    }

    /**
     * Delete the wineStock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WineStock : {}", id);
        wineStockRepository.deleteById(id);
    }
}
