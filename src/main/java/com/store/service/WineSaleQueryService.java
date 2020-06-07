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

import com.store.domain.WineSale;
import com.store.domain.*; // for static metamodels
import com.store.repository.WineSaleRepository;
import com.store.service.dto.WineSaleCriteria;
import com.store.service.dto.WineSaleDTO;
import com.store.service.mapper.WineSaleMapper;

/**
 * Service for executing complex queries for {@link WineSale} entities in the database.
 * The main input is a {@link WineSaleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WineSaleDTO} or a {@link Page} of {@link WineSaleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WineSaleQueryService extends QueryService<WineSale> {

    private final Logger log = LoggerFactory.getLogger(WineSaleQueryService.class);

    private final WineSaleRepository wineSaleRepository;

    private final WineSaleMapper wineSaleMapper;

    public WineSaleQueryService(WineSaleRepository wineSaleRepository, WineSaleMapper wineSaleMapper) {
        this.wineSaleRepository = wineSaleRepository;
        this.wineSaleMapper = wineSaleMapper;
    }

    /**
     * Return a {@link List} of {@link WineSaleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WineSaleDTO> findByCriteria(WineSaleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WineSale> specification = createSpecification(criteria);
        return wineSaleMapper.toDto(wineSaleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WineSaleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WineSaleDTO> findByCriteria(WineSaleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WineSale> specification = createSpecification(criteria);
        return wineSaleRepository.findAll(specification, page)
            .map(wineSaleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WineSaleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WineSale> specification = createSpecification(criteria);
        return wineSaleRepository.count(specification);
    }

    /**
     * Function to convert {@link WineSaleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WineSale> createSpecification(WineSaleCriteria criteria) {
        Specification<WineSale> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WineSale_.id));
            }
            if (criteria.getShippingDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingDescription(), WineSale_.shippingDescription));
            }
            if (criteria.getShippingAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShippingAmount(), WineSale_.shippingAmount));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), WineSale_.discount));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), WineSale_.total));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getState(), WineSale_.state));
            }
            if (criteria.getWineCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getWineCustomerId(),
                    root -> root.join(WineSale_.wineCustomer, JoinType.LEFT).get(WineCustomer_.id)));
            }
            if (criteria.getWineStockId() != null) {
                specification = specification.and(buildSpecification(criteria.getWineStockId(),
                    root -> root.join(WineSale_.wineStock, JoinType.LEFT).get(WineStock_.id)));
            }
            if (criteria.getWineStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getWineStoreId(),
                    root -> root.join(WineSale_.wineStore, JoinType.LEFT).get(WineStore_.id)));
            }
        }
        return specification;
    }
}
