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

import com.store.domain.WineStore;
import com.store.domain.*; // for static metamodels
import com.store.repository.WineStoreRepository;
import com.store.service.dto.WineStoreCriteria;
import com.store.service.dto.WineStoreDTO;
import com.store.service.mapper.WineStoreMapper;

/**
 * Service for executing complex queries for {@link WineStore} entities in the database.
 * The main input is a {@link WineStoreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WineStoreDTO} or a {@link Page} of {@link WineStoreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WineStoreQueryService extends QueryService<WineStore> {

    private final Logger log = LoggerFactory.getLogger(WineStoreQueryService.class);

    private final WineStoreRepository wineStoreRepository;

    private final WineStoreMapper wineStoreMapper;

    public WineStoreQueryService(WineStoreRepository wineStoreRepository, WineStoreMapper wineStoreMapper) {
        this.wineStoreRepository = wineStoreRepository;
        this.wineStoreMapper = wineStoreMapper;
    }

    /**
     * Return a {@link List} of {@link WineStoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WineStoreDTO> findByCriteria(WineStoreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WineStore> specification = createSpecification(criteria);
        return wineStoreMapper.toDto(wineStoreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WineStoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WineStoreDTO> findByCriteria(WineStoreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WineStore> specification = createSpecification(criteria);
        return wineStoreRepository.findAll(specification, page)
            .map(wineStoreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WineStoreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WineStore> specification = createSpecification(criteria);
        return wineStoreRepository.count(specification);
    }

    /**
     * Function to convert {@link WineStoreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WineStore> createSpecification(WineStoreCriteria criteria) {
        Specification<WineStore> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WineStore_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), WineStore_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), WineStore_.description));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRating(), WineStore_.rating));
            }
        }
        return specification;
    }
}
