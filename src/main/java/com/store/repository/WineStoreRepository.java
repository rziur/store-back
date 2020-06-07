package com.store.repository;

import com.store.domain.WineStore;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WineStore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineStoreRepository extends JpaRepository<WineStore, Long>, JpaSpecificationExecutor<WineStore> {
}
