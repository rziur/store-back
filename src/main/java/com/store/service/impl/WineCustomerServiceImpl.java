package com.store.service.impl;

import com.store.service.WineCustomerService;
import com.store.domain.WineCustomer;
import com.store.repository.WineCustomerRepository;
import com.store.service.dto.WineCustomerDTO;
import com.store.service.mapper.WineCustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WineCustomer}.
 */
@Service
@Transactional
public class WineCustomerServiceImpl implements WineCustomerService {

    private final Logger log = LoggerFactory.getLogger(WineCustomerServiceImpl.class);

    private final WineCustomerRepository wineCustomerRepository;

    private final WineCustomerMapper wineCustomerMapper;

    public WineCustomerServiceImpl(WineCustomerRepository wineCustomerRepository, WineCustomerMapper wineCustomerMapper) {
        this.wineCustomerRepository = wineCustomerRepository;
        this.wineCustomerMapper = wineCustomerMapper;
    }

    /**
     * Save a wineCustomer.
     *
     * @param wineCustomerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WineCustomerDTO save(WineCustomerDTO wineCustomerDTO) {
        log.debug("Request to save WineCustomer : {}", wineCustomerDTO);
        WineCustomer wineCustomer = wineCustomerMapper.toEntity(wineCustomerDTO);
        wineCustomer = wineCustomerRepository.save(wineCustomer);
        return wineCustomerMapper.toDto(wineCustomer);
    }

    /**
     * Get all the wineCustomers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WineCustomerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WineCustomers");
        return wineCustomerRepository.findAll(pageable)
            .map(wineCustomerMapper::toDto);
    }


    /**
     * Get one wineCustomer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WineCustomerDTO> findOne(Long id) {
        log.debug("Request to get WineCustomer : {}", id);
        return wineCustomerRepository.findById(id)
            .map(wineCustomerMapper::toDto);
    }

    /**
     * Delete the wineCustomer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WineCustomer : {}", id);
        wineCustomerRepository.deleteById(id);
    }
}
