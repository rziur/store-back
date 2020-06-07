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

import com.store.domain.WineCustomer;
import com.store.domain.*; // for static metamodels
import com.store.repository.WineCustomerRepository;
import com.store.service.dto.WineCustomerCriteria;
import com.store.service.dto.WineCustomerDTO;
import com.store.service.mapper.WineCustomerMapper;

/**
 * Service for executing complex queries for {@link WineCustomer} entities in the database.
 * The main input is a {@link WineCustomerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WineCustomerDTO} or a {@link Page} of {@link WineCustomerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WineCustomerQueryService extends QueryService<WineCustomer> {

    private final Logger log = LoggerFactory.getLogger(WineCustomerQueryService.class);

    private final WineCustomerRepository wineCustomerRepository;

    private final WineCustomerMapper wineCustomerMapper;

    public WineCustomerQueryService(WineCustomerRepository wineCustomerRepository, WineCustomerMapper wineCustomerMapper) {
        this.wineCustomerRepository = wineCustomerRepository;
        this.wineCustomerMapper = wineCustomerMapper;
    }

    /**
     * Return a {@link List} of {@link WineCustomerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WineCustomerDTO> findByCriteria(WineCustomerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WineCustomer> specification = createSpecification(criteria);
        return wineCustomerMapper.toDto(wineCustomerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WineCustomerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WineCustomerDTO> findByCriteria(WineCustomerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WineCustomer> specification = createSpecification(criteria);
        return wineCustomerRepository.findAll(specification, page)
            .map(wineCustomerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WineCustomerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WineCustomer> specification = createSpecification(criteria);
        return wineCustomerRepository.count(specification);
    }

    /**
     * Function to convert {@link WineCustomerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WineCustomer> createSpecification(WineCustomerCriteria criteria) {
        Specification<WineCustomer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WineCustomer_.id));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), WineCustomer_.address));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), WineCustomer_.phone));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(WineCustomer_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
