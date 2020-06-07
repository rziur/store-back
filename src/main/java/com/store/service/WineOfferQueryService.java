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

import com.store.domain.WineOffer;
import com.store.domain.*; // for static metamodels
import com.store.repository.WineOfferRepository;
import com.store.service.dto.WineOfferCriteria;
import com.store.service.dto.WineOfferDTO;
import com.store.service.mapper.WineOfferMapper;

/**
 * Service for executing complex queries for {@link WineOffer} entities in the database.
 * The main input is a {@link WineOfferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WineOfferDTO} or a {@link Page} of {@link WineOfferDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WineOfferQueryService extends QueryService<WineOffer> {

    private final Logger log = LoggerFactory.getLogger(WineOfferQueryService.class);

    private final WineOfferRepository wineOfferRepository;

    private final WineOfferMapper wineOfferMapper;

    public WineOfferQueryService(WineOfferRepository wineOfferRepository, WineOfferMapper wineOfferMapper) {
        this.wineOfferRepository = wineOfferRepository;
        this.wineOfferMapper = wineOfferMapper;
    }

    /**
     * Return a {@link List} of {@link WineOfferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WineOfferDTO> findByCriteria(WineOfferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WineOffer> specification = createSpecification(criteria);
        return wineOfferMapper.toDto(wineOfferRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WineOfferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WineOfferDTO> findByCriteria(WineOfferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WineOffer> specification = createSpecification(criteria);
        return wineOfferRepository.findAll(specification, page)
            .map(wineOfferMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WineOfferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WineOffer> specification = createSpecification(criteria);
        return wineOfferRepository.count(specification);
    }

    /**
     * Function to convert {@link WineOfferCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WineOffer> createSpecification(WineOfferCriteria criteria) {
        Specification<WineOffer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WineOffer_.id));
            }
            if (criteria.getIsAvailable() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAvailable(), WineOffer_.isAvailable));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), WineOffer_.price));
            }
            if (criteria.getSpecialPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSpecialPrice(), WineOffer_.specialPrice));
            }
            if (criteria.getWineStockId() != null) {
                specification = specification.and(buildSpecification(criteria.getWineStockId(),
                    root -> root.join(WineOffer_.wineStock, JoinType.LEFT).get(WineStock_.id)));
            }
            if (criteria.getWineStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getWineStoreId(),
                    root -> root.join(WineOffer_.wineStore, JoinType.LEFT).get(WineStore_.id)));
            }
        }
        return specification;
    }
}
