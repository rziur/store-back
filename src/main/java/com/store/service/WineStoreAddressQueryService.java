package com.store.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.store.domain.WineStoreAddress;
import com.store.domain.*; // for static metamodels
import com.store.repository.WineStoreAddressRepository;
import com.store.service.dto.WineStoreAddressCriteria;
import com.store.service.dto.WineStoreAddressDTO;
import com.store.service.mapper.WineStoreAddressMapper;

/**
 * Service for executing complex queries for {@link WineStoreAddress} entities in the database.
 * The main input is a {@link WineStoreAddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WineStoreAddressDTO} or a {@link Page} of {@link WineStoreAddressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WineStoreAddressQueryService extends QueryService<WineStoreAddress> {

    private final Logger log = LoggerFactory.getLogger(WineStoreAddressQueryService.class);

    private final WineStoreAddressRepository wineStoreAddressRepository;

    private final WineStoreAddressMapper wineStoreAddressMapper;

    public WineStoreAddressQueryService(WineStoreAddressRepository wineStoreAddressRepository, WineStoreAddressMapper wineStoreAddressMapper) {
        this.wineStoreAddressRepository = wineStoreAddressRepository;
        this.wineStoreAddressMapper = wineStoreAddressMapper;
    }

    /**
     * Return a {@link List} of {@link WineStoreAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WineStoreAddressDTO> findByCriteria(WineStoreAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WineStoreAddress> specification = createSpecification(criteria);
        return wineStoreAddressMapper.toDto(wineStoreAddressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WineStoreAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WineStoreAddressDTO> findByCriteria(WineStoreAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WineStoreAddress> specification = createSpecification(criteria);
        return wineStoreAddressRepository.findAll(specification, page)
            .map(wineStoreAddressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WineStoreAddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WineStoreAddress> specification = createSpecification(criteria);
        return wineStoreAddressRepository.count(specification);
    }

    /**
     * Function to convert {@link WineStoreAddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WineStoreAddress> createSpecification(WineStoreAddressCriteria criteria) {
        Specification<WineStoreAddress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WineStoreAddress_.id));
            }
            if (criteria.getStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreet(), WineStoreAddress_.street));
            }
            if (criteria.getPostcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostcode(), WineStoreAddress_.postcode));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), WineStoreAddress_.city));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegion(), WineStoreAddress_.region));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), WineStoreAddress_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), WineStoreAddress_.longitude));
            }
            if (criteria.getWineStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getWineStoreId(),
                    root -> root.join(WineStoreAddress_.wineStore, JoinType.LEFT).get(WineStore_.id)));
            }
        }
        return specification;
    }
}
