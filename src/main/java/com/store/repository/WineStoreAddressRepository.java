package com.store.repository;

import com.store.domain.WineStoreAddress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WineStoreAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineStoreAddressRepository extends JpaRepository<WineStoreAddress, Long>, JpaSpecificationExecutor<WineStoreAddress> {
}
