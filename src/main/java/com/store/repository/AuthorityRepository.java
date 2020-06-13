package com.store.repository;

import java.util.List;
import java.util.Set;

import com.store.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    
    List<Authority> findByNameNot(String name);

    Set<Authority> findOneByName(String name);
}
