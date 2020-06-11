package com.store.repository;

import java.util.List;
import java.util.Optional;

import com.store.domain.User;
import com.store.domain.WineCustomer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WineCustomer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineCustomerRepository extends JpaRepository<WineCustomer, Long>, JpaSpecificationExecutor<WineCustomer> {

    Optional<WineCustomer> findOneByUser(User user);
}
