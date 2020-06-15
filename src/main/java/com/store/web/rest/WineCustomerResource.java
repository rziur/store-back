package com.store.web.rest;

import com.store.service.WineCustomerService;
import com.store.web.rest.errors.BadRequestAlertException;
import com.store.web.rest.errors.InvalidPasswordException;
import com.store.web.rest.errors.LoginAlreadyUsedException;
import com.store.web.rest.vm.ManagedCustomerVM;
import com.store.service.dto.WineCustomerDTO;
import com.store.service.dto.UserDTO;
import com.store.service.dto.WineCustomerCriteria;
import com.store.domain.User;
import com.store.repository.UserRepository;
import com.store.security.AuthoritiesConstants;
import com.store.security.SecurityUtils;
import com.store.service.EmailAlreadyUsedException;
import com.store.service.MailService;
import com.store.service.UserService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * REST controller for managing {@link com.store.domain.WineCustomer}.
 */
@RestController
@RequestMapping("/api")
public class WineCustomerResource {

    private static class CustomerAccountResourceException extends RuntimeException {
        private CustomerAccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(WineCustomerResource.class);

    private static final String ENTITY_NAME = "wineCustomer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRepository userRepository;
    
    private final UserService userService;

    private final MailService mailService;
    
    private final WineCustomerService wineCustomerService;

    private final WineCustomerQueryService wineCustomerQueryService;

    public WineCustomerResource(WineCustomerService wineCustomerService, WineCustomerQueryService wineCustomerQueryService, UserService userService, UserRepository userRepository, MailService mailService) {
        this.wineCustomerService = wineCustomerService;
        this.wineCustomerQueryService = wineCustomerQueryService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;        
    }

    /**
     * {@code POST  /register-customer} : register the customer.
     *
     * @param ManagedCustomerVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register-customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@Valid @RequestBody ManagedCustomerVM managedCustomerVM) {
        
        log.debug("REST request to register WineCustomer : {}", managedCustomerVM);
      
        if (!userService.checkPasswordLength(managedCustomerVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedCustomerVM, managedCustomerVM.getPassword());

        WineCustomerDTO wineCustomerDTO = new WineCustomerDTO();
        wineCustomerDTO.setAddress(managedCustomerVM.getAddress());
        wineCustomerDTO.setPhone(managedCustomerVM.getPhone());
        wineCustomerDTO.setUserId(user.getId());
        wineCustomerService.save(wineCustomerDTO);
        mailService.sendActivationEmail(user);
    }

    /**
     * {@code POST  /wine-customers/account} : update the current customer information.
     *
     * @param userDTO the current customer information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/wine-customers/account")
    public void saveCustomerAccount(@Valid @RequestBody WineCustomerDTO wineCustomerDTO) {
        
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new CustomerAccountResourceException("Current user login not found"));
        
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(wineCustomerDTO.getEmail());
        
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        
        if (!user.isPresent()) {
            throw new CustomerAccountResourceException("User could not be found");
        }

        wineCustomerService.save(wineCustomerDTO);

        if(wineCustomerDTO.getUserId() != null)
        {
            userService.updateUser(
                wineCustomerDTO.getFirstName(), 
                wineCustomerDTO.getLastName(), 
                wineCustomerDTO.getEmail(),
                wineCustomerDTO.getLangKey(), 
                wineCustomerDTO.getImageUrl()
            );
        }
    }

    /**
     * {@code POST  /wine-customers} : Create a new wineCustomer.
     *
     * @param wineCustomerDTO the wineCustomerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wineCustomerDTO, or with status {@code 400 (Bad Request)} if the wineCustomer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wine-customers")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineCustomerDTO> createWineCustomer(@RequestBody WineCustomerDTO wineCustomerDTO) throws URISyntaxException {
        log.debug("REST request to save WineCustomer : {}", wineCustomerDTO);
        if (wineCustomerDTO.getId() != null) {
            throw new BadRequestAlertException("A new wineCustomer cannot already have an ID", ENTITY_NAME, "idexists");
        }  else if (userRepository.findOneByLogin(wineCustomerDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(wineCustomerDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
        
            User newUser = userService.createUser(wineCustomerDTO);

            wineCustomerDTO.setUserId(newUser.getId());

            WineCustomerDTO result = wineCustomerService.save(wineCustomerDTO);

            mailService.sendCreationEmail(newUser);

            return ResponseEntity.created(new URI("/api/wine-customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);

        }
        
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
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WineCustomerDTO> updateWineCustomer(@RequestBody WineCustomerDTO wineCustomerDTO) throws URISyntaxException {
        log.debug("REST request to update WineCustomer : {}", wineCustomerDTO);
        if (wineCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(wineCustomerDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(wineCustomerDTO.getUserId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(wineCustomerDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(wineCustomerDTO.getUserId()))) {
            throw new LoginAlreadyUsedException();
        }
        
        WineCustomerDTO result = wineCustomerService.save(wineCustomerDTO);

        if(wineCustomerDTO.getUserId() != null)
        {
            wineCustomerDTO.setId(wineCustomerDTO.getUserId());
            userService.updateUser(wineCustomerDTO);
        }

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
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
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
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
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
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteWineCustomer(@PathVariable Long id) {
        log.debug("REST request to delete WineCustomer : {}", id);
        
        Optional<WineCustomerDTO> existingUser = wineCustomerService.findOne(id);        
        
        wineCustomerService.delete(id);
        
        if (existingUser.isPresent()) {
            userService.deleteUser(existingUser.get().getLogin());
        }        
        
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
