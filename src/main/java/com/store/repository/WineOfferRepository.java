package com.store.repository;

import com.store.domain.WineOffer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WineOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineOfferRepository extends JpaRepository<WineOffer, Long>, JpaSpecificationExecutor<WineOffer> {
}
