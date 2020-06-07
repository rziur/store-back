package com.store.web.rest;

import com.store.service.WineStoreAddressService;
import com.store.web.rest.errors.BadRequestAlertException;
import com.store.service.dto.WineStoreAddressDTO;
import com.store.service.dto.WineStoreAddressCriteria;
import com.store.service.WineStoreAddressQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.store.domain.WineStoreAddress}.
 */
@RestController
@RequestMapping("/api")
public class WineStoreAddressResource {

    private final Logger log = LoggerFactory.getLogger(WineStoreAddressResource.class);

    private static final String ENTITY_NAME = "wineStoreAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WineStoreAddressService wineStoreAddressService;

    private final WineStoreAddressQueryService wineStoreAddressQueryService;

    public WineStoreAddressResource(WineStoreAddressService wineStoreAddressService, WineStoreAddressQueryService wineStoreAddressQueryService) {
        this.wineStoreAddressService = wineStoreAddressService;
        this.wineStoreAddressQueryService = wineStoreAddressQueryService;
    }

    /**
     * {@code POST  /wine-store-addresses} : Create a new wineStoreAddress.
     *
     * @param wineStoreAddressDTO the wineStoreAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wineStoreAddressDTO, or with status {@code 400 (Bad Request)} if the wineStoreAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wine-store-addresses")
    public ResponseEntity<WineStoreAddressDTO> createWineStoreAddress(@RequestBody WineStoreAddressDTO wineStoreAddressDTO) throws URISyntaxException {
        log.debug("REST request to save WineStoreAddress : {}", wineStoreAddressDTO);
        if (wineStoreAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new wineStoreAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WineStoreAddressDTO result = wineStoreAddressService.save(wineStoreAddressDTO);
        return ResponseEntity.created(new URI("/api/wine-store-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wine-store-addresses} : Updates an existing wineStoreAddress.
     *
     * @param wineStoreAddressDTO the wineStoreAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wineStoreAddressDTO,
     * or with status {@code 400 (Bad Request)} if the wineStoreAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wineStoreAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wine-store-addresses")
    public ResponseEntity<WineStoreAddressDTO> updateWineStoreAddress(@RequestBody WineStoreAddressDTO wineStoreAddressDTO) throws URISyntaxException {
        log.debug("REST request to update WineStoreAddress : {}", wineStoreAddressDTO);
        if (wineStoreAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WineStoreAddressDTO result = wineStoreAddressService.save(wineStoreAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wineStoreAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wine-store-addresses} : get all the wineStoreAddresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wineStoreAddresses in body.
     */
    @GetMapping("/wine-store-addresses")
    public ResponseEntity<List<WineStoreAddressDTO>> getAllWineStoreAddresses(WineStoreAddressCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WineStoreAddresses by criteria: {}", criteria);
        Page<WineStoreAddressDTO> page = wineStoreAddressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wine-store-addresses/count} : count all the wineStoreAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wine-store-addresses/count")
    public ResponseEntity<Long> countWineStoreAddresses(WineStoreAddressCriteria criteria) {
        log.debug("REST request to count WineStoreAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(wineStoreAddressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wine-store-addresses/:id} : get the "id" wineStoreAddress.
     *
     * @param id the id of the wineStoreAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wineStoreAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wine-store-addresses/{id}")
    public ResponseEntity<WineStoreAddressDTO> getWineStoreAddress(@PathVariable Long id) {
        log.debug("REST request to get WineStoreAddress : {}", id);
        Optional<WineStoreAddressDTO> wineStoreAddressDTO = wineStoreAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wineStoreAddressDTO);
    }

    /**
     * {@code DELETE  /wine-store-addresses/:id} : delete the "id" wineStoreAddress.
     *
     * @param id the id of the wineStoreAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wine-store-addresses/{id}")
    public ResponseEntity<Void> deleteWineStoreAddress(@PathVariable Long id) {
        log.debug("REST request to delete WineStoreAddress : {}", id);
        wineStoreAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
