package com.store.repository;

import com.store.domain.WineSale;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WineSale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineSaleRepository extends JpaRepository<WineSale, Long>, JpaSpecificationExecutor<WineSale> {
}
