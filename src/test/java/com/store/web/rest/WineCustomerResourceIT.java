package com.store.web.rest;

import com.store.StoreApp;
import com.store.domain.WineCustomer;
import com.store.domain.User;
import com.store.repository.WineCustomerRepository;
import com.store.service.WineCustomerService;
import com.store.service.dto.WineCustomerDTO;
import com.store.service.mapper.WineCustomerMapper;
import com.store.service.dto.WineCustomerCriteria;
import com.store.service.WineCustomerQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WineCustomerResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WineCustomerResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private WineCustomerRepository wineCustomerRepository;

    @Autowired
    private WineCustomerMapper wineCustomerMapper;

    @Autowired
    private WineCustomerService wineCustomerService;

    @Autowired
    private WineCustomerQueryService wineCustomerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWineCustomerMockMvc;

    private WineCustomer wineCustomer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineCustomer createEntity(EntityManager em) {
        WineCustomer wineCustomer = new WineCustomer()
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE);
        return wineCustomer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineCustomer createUpdatedEntity(EntityManager em) {
        WineCustomer wineCustomer = new WineCustomer()
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE);
        return wineCustomer;
    }

    @BeforeEach
    public void initTest() {
        wineCustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createWineCustomer() throws Exception {
        int databaseSizeBeforeCreate = wineCustomerRepository.findAll().size();
        // Create the WineCustomer
        WineCustomerDTO wineCustomerDTO = wineCustomerMapper.toDto(wineCustomer);
        restWineCustomerMockMvc.perform(post("/api/wine-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineCustomerDTO)))
            .andExpect(status().isCreated());

        // Validate the WineCustomer in the database
        List<WineCustomer> wineCustomerList = wineCustomerRepository.findAll();
        assertThat(wineCustomerList).hasSize(databaseSizeBeforeCreate + 1);
        WineCustomer testWineCustomer = wineCustomerList.get(wineCustomerList.size() - 1);
        assertThat(testWineCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWineCustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createWineCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wineCustomerRepository.findAll().size();

        // Create the WineCustomer with an existing ID
        wineCustomer.setId(1L);
        WineCustomerDTO wineCustomerDTO = wineCustomerMapper.toDto(wineCustomer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWineCustomerMockMvc.perform(post("/api/wine-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineCustomerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineCustomer in the database
        List<WineCustomer> wineCustomerList = wineCustomerRepository.findAll();
        assertThat(wineCustomerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWineCustomers() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList
        restWineCustomerMockMvc.perform(get("/api/wine-customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineCustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getWineCustomer() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get the wineCustomer
        restWineCustomerMockMvc.perform(get("/api/wine-customers/{id}", wineCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wineCustomer.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }


    @Test
    @Transactional
    public void getWineCustomersByIdFiltering() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        Long id = wineCustomer.getId();

        defaultWineCustomerShouldBeFound("id.equals=" + id);
        defaultWineCustomerShouldNotBeFound("id.notEquals=" + id);

        defaultWineCustomerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWineCustomerShouldNotBeFound("id.greaterThan=" + id);

        defaultWineCustomerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWineCustomerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWineCustomersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where address equals to DEFAULT_ADDRESS
        defaultWineCustomerShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the wineCustomerList where address equals to UPDATED_ADDRESS
        defaultWineCustomerShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllWineCustomersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where address not equals to DEFAULT_ADDRESS
        defaultWineCustomerShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the wineCustomerList where address not equals to UPDATED_ADDRESS
        defaultWineCustomerShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllWineCustomersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultWineCustomerShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the wineCustomerList where address equals to UPDATED_ADDRESS
        defaultWineCustomerShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllWineCustomersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where address is not null
        defaultWineCustomerShouldBeFound("address.specified=true");

        // Get all the wineCustomerList where address is null
        defaultWineCustomerShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineCustomersByAddressContainsSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where address contains DEFAULT_ADDRESS
        defaultWineCustomerShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the wineCustomerList where address contains UPDATED_ADDRESS
        defaultWineCustomerShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllWineCustomersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where address does not contain DEFAULT_ADDRESS
        defaultWineCustomerShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the wineCustomerList where address does not contain UPDATED_ADDRESS
        defaultWineCustomerShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllWineCustomersByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where phone equals to DEFAULT_PHONE
        defaultWineCustomerShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the wineCustomerList where phone equals to UPDATED_PHONE
        defaultWineCustomerShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllWineCustomersByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where phone not equals to DEFAULT_PHONE
        defaultWineCustomerShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the wineCustomerList where phone not equals to UPDATED_PHONE
        defaultWineCustomerShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllWineCustomersByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultWineCustomerShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the wineCustomerList where phone equals to UPDATED_PHONE
        defaultWineCustomerShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllWineCustomersByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where phone is not null
        defaultWineCustomerShouldBeFound("phone.specified=true");

        // Get all the wineCustomerList where phone is null
        defaultWineCustomerShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineCustomersByPhoneContainsSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where phone contains DEFAULT_PHONE
        defaultWineCustomerShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the wineCustomerList where phone contains UPDATED_PHONE
        defaultWineCustomerShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllWineCustomersByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        // Get all the wineCustomerList where phone does not contain DEFAULT_PHONE
        defaultWineCustomerShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the wineCustomerList where phone does not contain UPDATED_PHONE
        defaultWineCustomerShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllWineCustomersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        wineCustomer.setUser(user);
        wineCustomerRepository.saveAndFlush(wineCustomer);
        Long userId = user.getId();

        // Get all the wineCustomerList where user equals to userId
        defaultWineCustomerShouldBeFound("userId.equals=" + userId);

        // Get all the wineCustomerList where user equals to userId + 1
        defaultWineCustomerShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWineCustomerShouldBeFound(String filter) throws Exception {
        restWineCustomerMockMvc.perform(get("/api/wine-customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineCustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));

        // Check, that the count call also returns 1
        restWineCustomerMockMvc.perform(get("/api/wine-customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWineCustomerShouldNotBeFound(String filter) throws Exception {
        restWineCustomerMockMvc.perform(get("/api/wine-customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWineCustomerMockMvc.perform(get("/api/wine-customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWineCustomer() throws Exception {
        // Get the wineCustomer
        restWineCustomerMockMvc.perform(get("/api/wine-customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWineCustomer() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        int databaseSizeBeforeUpdate = wineCustomerRepository.findAll().size();

        // Update the wineCustomer
        WineCustomer updatedWineCustomer = wineCustomerRepository.findById(wineCustomer.getId()).get();
        // Disconnect from session so that the updates on updatedWineCustomer are not directly saved in db
        em.detach(updatedWineCustomer);
        updatedWineCustomer
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE);
        WineCustomerDTO wineCustomerDTO = wineCustomerMapper.toDto(updatedWineCustomer);

        restWineCustomerMockMvc.perform(put("/api/wine-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineCustomerDTO)))
            .andExpect(status().isOk());

        // Validate the WineCustomer in the database
        List<WineCustomer> wineCustomerList = wineCustomerRepository.findAll();
        assertThat(wineCustomerList).hasSize(databaseSizeBeforeUpdate);
        WineCustomer testWineCustomer = wineCustomerList.get(wineCustomerList.size() - 1);
        assertThat(testWineCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWineCustomer.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingWineCustomer() throws Exception {
        int databaseSizeBeforeUpdate = wineCustomerRepository.findAll().size();

        // Create the WineCustomer
        WineCustomerDTO wineCustomerDTO = wineCustomerMapper.toDto(wineCustomer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWineCustomerMockMvc.perform(put("/api/wine-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineCustomerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineCustomer in the database
        List<WineCustomer> wineCustomerList = wineCustomerRepository.findAll();
        assertThat(wineCustomerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWineCustomer() throws Exception {
        // Initialize the database
        wineCustomerRepository.saveAndFlush(wineCustomer);

        int databaseSizeBeforeDelete = wineCustomerRepository.findAll().size();

        // Delete the wineCustomer
        restWineCustomerMockMvc.perform(delete("/api/wine-customers/{id}", wineCustomer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WineCustomer> wineCustomerList = wineCustomerRepository.findAll();
        assertThat(wineCustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
