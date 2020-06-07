package com.store.repository;

import com.store.domain.WineStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WineStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineStockRepository extends JpaRepository<WineStock, Long>, JpaSpecificationExecutor<WineStock> {
}
