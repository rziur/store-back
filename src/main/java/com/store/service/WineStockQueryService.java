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

import com.store.domain.WineStock;
import com.store.domain.*; // for static metamodels
import com.store.repository.WineStockRepository;
import com.store.service.dto.WineStockCriteria;
import com.store.service.dto.WineStockDTO;
import com.store.service.mapper.WineStockMapper;

/**
 * Service for executing complex queries for {@link WineStock} entities in the database.
 * The main input is a {@link WineStockCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WineStockDTO} or a {@link Page} of {@link WineStockDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WineStockQueryService extends QueryService<WineStock> {

    private final Logger log = LoggerFactory.getLogger(WineStockQueryService.class);

    private final WineStockRepository wineStockRepository;

    private final WineStockMapper wineStockMapper;

    public WineStockQueryService(WineStockRepository wineStockRepository, WineStockMapper wineStockMapper) {
        this.wineStockRepository = wineStockRepository;
        this.wineStockMapper = wineStockMapper;
    }

    /**
     * Return a {@link List} of {@link WineStockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WineStockDTO> findByCriteria(WineStockCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WineStock> specification = createSpecification(criteria);
        return wineStockMapper.toDto(wineStockRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WineStockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WineStockDTO> findByCriteria(WineStockCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WineStock> specification = createSpecification(criteria);
        return wineStockRepository.findAll(specification, page)
            .map(wineStockMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WineStockCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WineStock> specification = createSpecification(criteria);
        return wineStockRepository.count(specification);
    }

    /**
     * Function to convert {@link WineStockCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WineStock> createSpecification(WineStockCriteria criteria) {
        Specification<WineStock> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WineStock_.id));
            }
            if (criteria.getSupplier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplier(), WineStock_.supplier));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegion(), WineStock_.region));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), WineStock_.description));
            }
            if (criteria.getVintage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVintage(), WineStock_.vintage));
            }
            if (criteria.getAlcoholLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAlcoholLevel(), WineStock_.alcoholLevel));
            }
            if (criteria.getPrintRun() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrintRun(), WineStock_.printRun));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), WineStock_.price));
            }
            if (criteria.getPhysical() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhysical(), WineStock_.physical));
            }
            if (criteria.getPurchases() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchases(), WineStock_.purchases));
            }
            if (criteria.getSales() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSales(), WineStock_.sales));
            }
            if (criteria.getAvailability() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailability(), WineStock_.availability));
            }
            if (criteria.getPxRevCol() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPxRevCol(), WineStock_.pxRevCol));
            }
            if (criteria.getLastPurchasePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastPurchasePrice(), WineStock_.lastPurchasePrice));
            }
            if (criteria.getLastPurchaseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastPurchaseDate(), WineStock_.lastPurchaseDate));
            }
            if (criteria.getDateImport() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateImport(), WineStock_.dateImport));
            }
        }
        return specification;
    }
}
