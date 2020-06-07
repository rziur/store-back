package com.store.repository;

import com.store.domain.WineCustomer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WineCustomer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineCustomerRepository extends JpaRepository<WineCustomer, Long>, JpaSpecificationExecutor<WineCustomer> {
}
