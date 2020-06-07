package com.store.service.impl;

import com.store.service.WineStoreService;
import com.store.domain.WineStore;
import com.store.repository.WineStoreRepository;
import com.store.service.dto.WineStoreDTO;
import com.store.service.mapper.WineStoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WineStore}.
 */
@Service
@Transactional
public class WineStoreServiceImpl implements WineStoreService {

    private final Logger log = LoggerFactory.getLogger(WineStoreServiceImpl.class);

    private final WineStoreRepository wineStoreRepository;

    private final WineStoreMapper wineStoreMapper;

    public WineStoreServiceImpl(WineStoreRepository wineStoreRepository, WineStoreMapper wineStoreMapper) {
        this.wineStoreRepository = wineStoreRepository;
        this.wineStoreMapper = wineStoreMapper;
    }

    /**
     * Save a wineStore.
     *
     * @param wineStoreDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WineStoreDTO save(WineStoreDTO wineStoreDTO) {
        log.debug("Request to save WineStore : {}", wineStoreDTO);
        WineStore wineStore = wineStoreMapper.toEntity(wineStoreDTO);
        wineStore = wineStoreRepository.save(wineStore);
        return wineStoreMapper.toDto(wineStore);
    }

    /**
     * Get all the wineStores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WineStoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WineStores");
        return wineStoreRepository.findAll(pageable)
            .map(wineStoreMapper::toDto);
    }


    /**
     * Get one wineStore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WineStoreDTO> findOne(Long id) {
        log.debug("Request to get WineStore : {}", id);
        return wineStoreRepository.findById(id)
            .map(wineStoreMapper::toDto);
    }

    /**
     * Delete the wineStore by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WineStore : {}", id);
        wineStoreRepository.deleteById(id);
    }
}
