package com.store.repository;

import com.store.domain.WineCustomer;
import com.store.domain.WineSale;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

/**
 * Spring Data  repository for the WineSale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineSaleRepository extends JpaRepository<WineSale, Long>, JpaSpecificationExecutor<WineSale> {

    Page<WineSale> findByWineCustomer(WineCustomer wineCustomer,Pageable pageable);
}
