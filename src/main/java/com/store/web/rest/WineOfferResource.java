package com.store.web.rest;

import com.store.service.WineOfferService;
import com.store.web.rest.errors.BadRequestAlertException;
import com.store.service.dto.WineOfferDTO;
import com.store.service.dto.WineOfferCriteria;
import com.store.security.AuthoritiesConstants;
import com.store.service.WineOfferQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.store.domain.WineOffer}.
 */
@RestController
@RequestMapping("/api")
public class WineOfferResource {

    private final Logger log = LoggerFactory.getLogger(WineOfferResource.class);

    private static final String ENTITY_NAME = "wineOffer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WineOfferService wineOfferService;

    private final WineOfferQueryService wineOfferQueryService;

    public WineOfferResource(WineOfferService wineOfferService, WineOfferQueryService wineOfferQueryService) {
        this.wineOfferService = wineOfferService;
        this.wineOfferQueryService = wineOfferQueryService;
    }

    /**
     * {@code POST  /wine-offers} : Create a new wineOffer.
     *
     * @param wineOfferDTO the wineOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wineOfferDTO, or with status {@code 400 (Bad Request)} if the wineOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wine-offers")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineOfferDTO> createWineOffer(@RequestBody WineOfferDTO wineOfferDTO) throws URISyntaxException {
        log.debug("REST request to save WineOffer : {}", wineOfferDTO);
        if (wineOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new wineOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WineOfferDTO result = wineOfferService.save(wineOfferDTO);
        return ResponseEntity.created(new URI("/api/wine-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wine-offers} : Updates an existing wineOffer.
     *
     * @param wineOfferDTO the wineOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wineOfferDTO,
     * or with status {@code 400 (Bad Request)} if the wineOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wineOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wine-offers")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineOfferDTO> updateWineOffer(@RequestBody WineOfferDTO wineOfferDTO) throws URISyntaxException {
        log.debug("REST request to update WineOffer : {}", wineOfferDTO);
        if (wineOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WineOfferDTO result = wineOfferService.save(wineOfferDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wineOfferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wine-offers} : get all the wineOffers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wineOffers in body.
     */
    @GetMapping("/wine-offers")
    public ResponseEntity<List<WineOfferDTO>> getAllWineOffers(WineOfferCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WineOffers by criteria: {}", criteria);
        Page<WineOfferDTO> page = wineOfferQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wine-offers/count} : count all the wineOffers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wine-offers/count")
    public ResponseEntity<Long> countWineOffers(WineOfferCriteria criteria) {
        log.debug("REST request to count WineOffers by criteria: {}", criteria);
        return ResponseEntity.ok().body(wineOfferQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wine-offers/:id} : get the "id" wineOffer.
     *
     * @param id the id of the wineOfferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wineOfferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wine-offers/{id}")
    public ResponseEntity<WineOfferDTO> getWineOffer(@PathVariable Long id) {
        log.debug("REST request to get WineOffer : {}", id);
        Optional<WineOfferDTO> wineOfferDTO = wineOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wineOfferDTO);
    }

    /**
     * {@code DELETE  /wine-offers/:id} : delete the "id" wineOffer.
     *
     * @param id the id of the wineOfferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wine-offers/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteWineOffer(@PathVariable Long id) {
        log.debug("REST request to delete WineOffer : {}", id);
        wineOfferService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
