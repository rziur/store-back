package com.store.web.rest;

import com.store.service.WineSaleService;
import com.store.web.rest.errors.BadRequestAlertException;
import com.store.service.dto.WineSaleDTO;
import com.store.service.dto.WineCustomerDTO;
import com.store.service.dto.WineSaleCriteria;
import com.store.domain.User;
import com.store.domain.WineCustomer;
import com.store.repository.UserRepository;
import com.store.security.AuthoritiesConstants;
import com.store.security.SecurityUtils;
import com.store.service.WineCustomerService;
import com.store.service.WineSaleQueryService;

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
 * REST controller for managing {@link com.store.domain.WineSale}.
 */
@RestController
@RequestMapping("/api")
public class WineSaleResource {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(WineSaleResource.class);

    private static final String ENTITY_NAME = "wineSale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WineSaleService wineSaleService;

    private final WineSaleQueryService wineSaleQueryService;

    private final UserRepository userRepository;

    private final WineCustomerService wineCustomerService;

    public WineSaleResource(WineSaleService wineSaleService, WineSaleQueryService wineSaleQueryService, UserRepository userRepository, WineCustomerService wineCustomerService) {
        this.wineSaleService = wineSaleService;
        this.wineSaleQueryService = wineSaleQueryService;
        this.userRepository = userRepository;
        this.wineCustomerService = wineCustomerService;
    }

    /**
     * {@code POST  /wine-sales} : Create a new wineSale.
     *
     * @param wineSaleDTO the wineSaleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wineSaleDTO, or with status {@code 400 (Bad Request)} if the wineSale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wine-sales")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineSaleDTO> createWineSale(@RequestBody WineSaleDTO wineSaleDTO) throws URISyntaxException {
        log.debug("REST request to save WineSale : {}", wineSaleDTO);
        if (wineSaleDTO.getId() != null) {
            throw new BadRequestAlertException("A new wineSale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WineSaleDTO result = wineSaleService.save(wineSaleDTO);
        return ResponseEntity.created(new URI("/api/wine-sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wine-sales} : Updates an existing wineSale.
     *
     * @param wineSaleDTO the wineSaleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wineSaleDTO,
     * or with status {@code 400 (Bad Request)} if the wineSaleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wineSaleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wine-sales")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineSaleDTO> updateWineSale(@RequestBody WineSaleDTO wineSaleDTO) throws URISyntaxException {
        log.debug("REST request to update WineSale : {}", wineSaleDTO);
        if (wineSaleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WineSaleDTO result = wineSaleService.save(wineSaleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wineSaleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wine-sales} : get all the wineSales.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wineSales in body.
     */
    @GetMapping("/wine-sales")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<WineSaleDTO>> getAllWineSales(WineSaleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WineSales by criteria: {}", criteria);
        Page<WineSaleDTO> page = wineSaleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wine-sales} : get all the wineSales of current user.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wineSales in body.
     */
    @GetMapping("/wine-sales/customer")
    public ResponseEntity<List<WineSaleDTO>> getAllWineSalesCustomer(Pageable pageable) {
      
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
       
        Optional<User> existingUser = userRepository.findOneByLogin(userLogin.toLowerCase());
        
        Optional<WineCustomerDTO> existingCustomer = null;

        if (existingUser.isPresent()) {
            
            existingCustomer = wineCustomerService.findOneByUserId(existingUser.get().getId());

            if(!existingCustomer.isPresent()) {
                throw new AccountResourceException("Current user login not found");
            }

        }
        WineCustomer customer = new WineCustomer();
        customer.setId(existingCustomer.get().getId());
        Page<WineSaleDTO> page = wineSaleService.findAllByCustomer(customer, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wine-sales/count} : count all the wineSales.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wine-sales/count")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Long> countWineSales(WineSaleCriteria criteria) {
        log.debug("REST request to count WineSales by criteria: {}", criteria);
        return ResponseEntity.ok().body(wineSaleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wine-sales/:id} : get the "id" wineSale.
     *
     * @param id the id of the wineSaleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wineSaleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wine-sales/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineSaleDTO> getWineSale(@PathVariable Long id) {
        log.debug("REST request to get WineSale : {}", id);
        Optional<WineSaleDTO> wineSaleDTO = wineSaleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wineSaleDTO);
    }

    /**
     * {@code DELETE  /wine-sales/:id} : delete the "id" wineSale.
     *
     * @param id the id of the wineSaleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wine-sales/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteWineSale(@PathVariable Long id) {
        log.debug("REST request to delete WineSale : {}", id);
        wineSaleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
