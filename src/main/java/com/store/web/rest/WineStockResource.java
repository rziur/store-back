package com.store.web.rest;

import com.store.service.WineStockService;
import com.store.web.rest.errors.BadRequestAlertException;
import com.store.service.dto.WineStockDTO;
import com.store.service.dto.WineStockCriteria;
import com.store.security.AuthoritiesConstants;
import com.store.service.WineStockQueryService;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.store.domain.WineStock}.
 */
@RestController
@RequestMapping("/api")
public class WineStockResource {

    private final Logger log = LoggerFactory.getLogger(WineStockResource.class);

    private static final String ENTITY_NAME = "wineStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WineStockService wineStockService;

    private final WineStockQueryService wineStockQueryService;

    public WineStockResource(WineStockService wineStockService, WineStockQueryService wineStockQueryService) {
        this.wineStockService = wineStockService;
        this.wineStockQueryService = wineStockQueryService;
    }

    /**
     * {@code POST  /wine-stocks} : Create a new wineStock.
     *
     * @param wineStockDTO the wineStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wineStockDTO, or with status {@code 400 (Bad Request)} if the wineStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wine-stocks")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineStockDTO> createWineStock(@Valid @RequestBody WineStockDTO wineStockDTO) throws URISyntaxException {
        log.debug("REST request to save WineStock : {}", wineStockDTO);
        if (wineStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new wineStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WineStockDTO result = wineStockService.save(wineStockDTO);
        return ResponseEntity.created(new URI("/api/wine-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wine-stocks} : Updates an existing wineStock.
     *
     * @param wineStockDTO the wineStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wineStockDTO,
     * or with status {@code 400 (Bad Request)} if the wineStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wineStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wine-stocks")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineStockDTO> updateWineStock(@Valid @RequestBody WineStockDTO wineStockDTO) throws URISyntaxException {
        log.debug("REST request to update WineStock : {}", wineStockDTO);
        if (wineStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WineStockDTO result = wineStockService.save(wineStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wineStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wine-stocks} : get all the wineStocks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wineStocks in body.
     */
    @GetMapping("/wine-stocks")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<WineStockDTO>> getAllWineStocks(WineStockCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WineStocks by criteria: {}", criteria);
        Page<WineStockDTO> page = wineStockQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wine-stocks/count} : count all the wineStocks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wine-stocks/count")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Long> countWineStocks(WineStockCriteria criteria) {
        log.debug("REST request to count WineStocks by criteria: {}", criteria);
        return ResponseEntity.ok().body(wineStockQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wine-stocks/:id} : get the "id" wineStock.
     *
     * @param id the id of the wineStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wineStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wine-stocks/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineStockDTO> getWineStock(@PathVariable Long id) {
        log.debug("REST request to get WineStock : {}", id);
        Optional<WineStockDTO> wineStockDTO = wineStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wineStockDTO);
    }

    /**
     * {@code DELETE  /wine-stocks/:id} : delete the "id" wineStock.
     *
     * @param id the id of the wineStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wine-stocks/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteWineStock(@PathVariable Long id) {
        log.debug("REST request to delete WineStock : {}", id);
        wineStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
