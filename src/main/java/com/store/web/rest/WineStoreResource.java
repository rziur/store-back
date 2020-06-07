package com.store.web.rest;

import com.store.service.WineStoreService;
import com.store.web.rest.errors.BadRequestAlertException;
import com.store.service.dto.WineStoreDTO;
import com.store.service.dto.WineStoreCriteria;
import com.store.service.WineStoreQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.store.domain.WineStore}.
 */
@RestController
@RequestMapping("/api")
public class WineStoreResource {

    private final Logger log = LoggerFactory.getLogger(WineStoreResource.class);

    private static final String ENTITY_NAME = "wineStore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WineStoreService wineStoreService;

    private final WineStoreQueryService wineStoreQueryService;

    public WineStoreResource(WineStoreService wineStoreService, WineStoreQueryService wineStoreQueryService) {
        this.wineStoreService = wineStoreService;
        this.wineStoreQueryService = wineStoreQueryService;
    }

    /**
     * {@code POST  /wine-stores} : Create a new wineStore.
     *
     * @param wineStoreDTO the wineStoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wineStoreDTO, or with status {@code 400 (Bad Request)} if the wineStore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wine-stores")
    public ResponseEntity<WineStoreDTO> createWineStore(@Valid @RequestBody WineStoreDTO wineStoreDTO) throws URISyntaxException {
        log.debug("REST request to save WineStore : {}", wineStoreDTO);
        if (wineStoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new wineStore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WineStoreDTO result = wineStoreService.save(wineStoreDTO);
        return ResponseEntity.created(new URI("/api/wine-stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wine-stores} : Updates an existing wineStore.
     *
     * @param wineStoreDTO the wineStoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wineStoreDTO,
     * or with status {@code 400 (Bad Request)} if the wineStoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wineStoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wine-stores")
    public ResponseEntity<WineStoreDTO> updateWineStore(@Valid @RequestBody WineStoreDTO wineStoreDTO) throws URISyntaxException {
        log.debug("REST request to update WineStore : {}", wineStoreDTO);
        if (wineStoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WineStoreDTO result = wineStoreService.save(wineStoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wineStoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wine-stores} : get all the wineStores.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wineStores in body.
     */
    @GetMapping("/wine-stores")
    public ResponseEntity<List<WineStoreDTO>> getAllWineStores(WineStoreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WineStores by criteria: {}", criteria);
        Page<WineStoreDTO> page = wineStoreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wine-stores/count} : count all the wineStores.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wine-stores/count")
    public ResponseEntity<Long> countWineStores(WineStoreCriteria criteria) {
        log.debug("REST request to count WineStores by criteria: {}", criteria);
        return ResponseEntity.ok().body(wineStoreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wine-stores/:id} : get the "id" wineStore.
     *
     * @param id the id of the wineStoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wineStoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wine-stores/{id}")
    public ResponseEntity<WineStoreDTO> getWineStore(@PathVariable Long id) {
        log.debug("REST request to get WineStore : {}", id);
        Optional<WineStoreDTO> wineStoreDTO = wineStoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wineStoreDTO);
    }

    /**
     * {@code DELETE  /wine-stores/:id} : delete the "id" wineStore.
     *
     * @param id the id of the wineStoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wine-stores/{id}")
    public ResponseEntity<Void> deleteWineStore(@PathVariable Long id) {
        log.debug("REST request to delete WineStore : {}", id);
        wineStoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
