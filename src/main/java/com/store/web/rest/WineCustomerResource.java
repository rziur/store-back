package com.store.web.rest;

import com.store.service.WineCustomerService;
import com.store.web.rest.errors.BadRequestAlertException;
import com.store.service.dto.WineCustomerDTO;
import com.store.service.dto.WineCustomerCriteria;
import com.store.service.WineCustomerQueryService;

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
 * REST controller for managing {@link com.store.domain.WineCustomer}.
 */
@RestController
@RequestMapping("/api")
public class WineCustomerResource {

    private final Logger log = LoggerFactory.getLogger(WineCustomerResource.class);

    private static final String ENTITY_NAME = "wineCustomer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WineCustomerService wineCustomerService;

    private final WineCustomerQueryService wineCustomerQueryService;

    public WineCustomerResource(WineCustomerService wineCustomerService, WineCustomerQueryService wineCustomerQueryService) {
        this.wineCustomerService = wineCustomerService;
        this.wineCustomerQueryService = wineCustomerQueryService;
    }

    /**
     * {@code POST  /wine-customers} : Create a new wineCustomer.
     *
     * @param wineCustomerDTO the wineCustomerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wineCustomerDTO, or with status {@code 400 (Bad Request)} if the wineCustomer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wine-customers")
    public ResponseEntity<WineCustomerDTO> createWineCustomer(@RequestBody WineCustomerDTO wineCustomerDTO) throws URISyntaxException {
        log.debug("REST request to save WineCustomer : {}", wineCustomerDTO);
        if (wineCustomerDTO.getId() != null) {
            throw new BadRequestAlertException("A new wineCustomer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WineCustomerDTO result = wineCustomerService.save(wineCustomerDTO);
        return ResponseEntity.created(new URI("/api/wine-customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wine-customers} : Updates an existing wineCustomer.
     *
     * @param wineCustomerDTO the wineCustomerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wineCustomerDTO,
     * or with status {@code 400 (Bad Request)} if the wineCustomerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wineCustomerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wine-customers")
    public ResponseEntity<WineCustomerDTO> updateWineCustomer(@RequestBody WineCustomerDTO wineCustomerDTO) throws URISyntaxException {
        log.debug("REST request to update WineCustomer : {}", wineCustomerDTO);
        if (wineCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WineCustomerDTO result = wineCustomerService.save(wineCustomerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wineCustomerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wine-customers} : get all the wineCustomers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wineCustomers in body.
     */
    @GetMapping("/wine-customers")
    public ResponseEntity<List<WineCustomerDTO>> getAllWineCustomers(WineCustomerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WineCustomers by criteria: {}", criteria);
        Page<WineCustomerDTO> page = wineCustomerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wine-customers/count} : count all the wineCustomers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wine-customers/count")
    public ResponseEntity<Long> countWineCustomers(WineCustomerCriteria criteria) {
        log.debug("REST request to count WineCustomers by criteria: {}", criteria);
        return ResponseEntity.ok().body(wineCustomerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wine-customers/:id} : get the "id" wineCustomer.
     *
     * @param id the id of the wineCustomerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wineCustomerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wine-customers/{id}")
    public ResponseEntity<WineCustomerDTO> getWineCustomer(@PathVariable Long id) {
        log.debug("REST request to get WineCustomer : {}", id);
        Optional<WineCustomerDTO> wineCustomerDTO = wineCustomerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wineCustomerDTO);
    }

    /**
     * {@code DELETE  /wine-customers/:id} : delete the "id" wineCustomer.
     *
     * @param id the id of the wineCustomerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wine-customers/{id}")
    public ResponseEntity<Void> deleteWineCustomer(@PathVariable Long id) {
        log.debug("REST request to delete WineCustomer : {}", id);
        wineCustomerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
