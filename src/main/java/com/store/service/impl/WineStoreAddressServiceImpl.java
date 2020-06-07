package com.store.service.impl;

import com.store.service.WineStoreAddressService;
import com.store.domain.WineStoreAddress;
import com.store.repository.WineStoreAddressRepository;
import com.store.service.dto.WineStoreAddressDTO;
import com.store.service.mapper.WineStoreAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WineStoreAddress}.
 */
@Service
@Transactional
public class WineStoreAddressServiceImpl implements WineStoreAddressService {

    private final Logger log = LoggerFactory.getLogger(WineStoreAddressServiceImpl.class);

    private final WineStoreAddressRepository wineStoreAddressRepository;

    private final WineStoreAddressMapper wineStoreAddressMapper;

    public WineStoreAddressServiceImpl(WineStoreAddressRepository wineStoreAddressRepository, WineStoreAddressMapper wineStoreAddressMapper) {
        this.wineStoreAddressRepository = wineStoreAddressRepository;
        this.wineStoreAddressMapper = wineStoreAddressMapper;
    }

    /**
     * Save a wineStoreAddress.
     *
     * @param wineStoreAddressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WineStoreAddressDTO save(WineStoreAddressDTO wineStoreAddressDTO) {
        log.debug("Request to save WineStoreAddress : {}", wineStoreAddressDTO);
        WineStoreAddress wineStoreAddress = wineStoreAddressMapper.toEntity(wineStoreAddressDTO);
        wineStoreAddress = wineStoreAddressRepository.save(wineStoreAddress);
        return wineStoreAddressMapper.toDto(wineStoreAddress);
    }

    /**
     * Get all the wineStoreAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WineStoreAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WineStoreAddresses");
        return wineStoreAddressRepository.findAll(pageable)
            .map(wineStoreAddressMapper::toDto);
    }


    /**
     * Get one wineStoreAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WineStoreAddressDTO> findOne(Long id) {
        log.debug("Request to get WineStoreAddress : {}", id);
        return wineStoreAddressRepository.findById(id)
            .map(wineStoreAddressMapper::toDto);
    }

    /**
     * Delete the wineStoreAddress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WineStoreAddress : {}", id);
        wineStoreAddressRepository.deleteById(id);
    }
}
