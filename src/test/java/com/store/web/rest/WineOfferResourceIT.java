package com.store.web.rest;

import com.store.StoreApp;
import com.store.domain.WineOffer;
import com.store.domain.WineStock;
import com.store.domain.WineStore;
import com.store.repository.WineOfferRepository;
import com.store.service.WineOfferService;
import com.store.service.dto.WineOfferDTO;
import com.store.service.mapper.WineOfferMapper;
import com.store.service.dto.WineOfferCriteria;
import com.store.service.WineOfferQueryService;

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
 * Integration tests for the {@link WineOfferResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WineOfferResourceIT {

    private static final Boolean DEFAULT_IS_AVAILABLE = false;
    private static final Boolean UPDATED_IS_AVAILABLE = true;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final Float DEFAULT_SPECIAL_PRICE = 1F;
    private static final Float UPDATED_SPECIAL_PRICE = 2F;
    private static final Float SMALLER_SPECIAL_PRICE = 1F - 1F;

    @Autowired
    private WineOfferRepository wineOfferRepository;

    @Autowired
    private WineOfferMapper wineOfferMapper;

    @Autowired
    private WineOfferService wineOfferService;

    @Autowired
    private WineOfferQueryService wineOfferQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWineOfferMockMvc;

    private WineOffer wineOffer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineOffer createEntity(EntityManager em) {
        WineOffer wineOffer = new WineOffer()
            .isAvailable(DEFAULT_IS_AVAILABLE)
            .price(DEFAULT_PRICE)
            .specialPrice(DEFAULT_SPECIAL_PRICE);
        return wineOffer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineOffer createUpdatedEntity(EntityManager em) {
        WineOffer wineOffer = new WineOffer()
            .isAvailable(UPDATED_IS_AVAILABLE)
            .price(UPDATED_PRICE)
            .specialPrice(UPDATED_SPECIAL_PRICE);
        return wineOffer;
    }

    @BeforeEach
    public void initTest() {
        wineOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createWineOffer() throws Exception {
        int databaseSizeBeforeCreate = wineOfferRepository.findAll().size();
        // Create the WineOffer
        WineOfferDTO wineOfferDTO = wineOfferMapper.toDto(wineOffer);
        restWineOfferMockMvc.perform(post("/api/wine-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineOfferDTO)))
            .andExpect(status().isCreated());

        // Validate the WineOffer in the database
        List<WineOffer> wineOfferList = wineOfferRepository.findAll();
        assertThat(wineOfferList).hasSize(databaseSizeBeforeCreate + 1);
        WineOffer testWineOffer = wineOfferList.get(wineOfferList.size() - 1);
        assertThat(testWineOffer.isIsAvailable()).isEqualTo(DEFAULT_IS_AVAILABLE);
        assertThat(testWineOffer.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testWineOffer.getSpecialPrice()).isEqualTo(DEFAULT_SPECIAL_PRICE);
    }

    @Test
    @Transactional
    public void createWineOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wineOfferRepository.findAll().size();

        // Create the WineOffer with an existing ID
        wineOffer.setId(1L);
        WineOfferDTO wineOfferDTO = wineOfferMapper.toDto(wineOffer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWineOfferMockMvc.perform(post("/api/wine-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineOfferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineOffer in the database
        List<WineOffer> wineOfferList = wineOfferRepository.findAll();
        assertThat(wineOfferList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWineOffers() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList
        restWineOfferMockMvc.perform(get("/api/wine-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].isAvailable").value(hasItem(DEFAULT_IS_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].specialPrice").value(hasItem(DEFAULT_SPECIAL_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getWineOffer() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get the wineOffer
        restWineOfferMockMvc.perform(get("/api/wine-offers/{id}", wineOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wineOffer.getId().intValue()))
            .andExpect(jsonPath("$.isAvailable").value(DEFAULT_IS_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.specialPrice").value(DEFAULT_SPECIAL_PRICE.doubleValue()));
    }


    @Test
    @Transactional
    public void getWineOffersByIdFiltering() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        Long id = wineOffer.getId();

        defaultWineOfferShouldBeFound("id.equals=" + id);
        defaultWineOfferShouldNotBeFound("id.notEquals=" + id);

        defaultWineOfferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWineOfferShouldNotBeFound("id.greaterThan=" + id);

        defaultWineOfferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWineOfferShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWineOffersByIsAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where isAvailable equals to DEFAULT_IS_AVAILABLE
        defaultWineOfferShouldBeFound("isAvailable.equals=" + DEFAULT_IS_AVAILABLE);

        // Get all the wineOfferList where isAvailable equals to UPDATED_IS_AVAILABLE
        defaultWineOfferShouldNotBeFound("isAvailable.equals=" + UPDATED_IS_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByIsAvailableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where isAvailable not equals to DEFAULT_IS_AVAILABLE
        defaultWineOfferShouldNotBeFound("isAvailable.notEquals=" + DEFAULT_IS_AVAILABLE);

        // Get all the wineOfferList where isAvailable not equals to UPDATED_IS_AVAILABLE
        defaultWineOfferShouldBeFound("isAvailable.notEquals=" + UPDATED_IS_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByIsAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where isAvailable in DEFAULT_IS_AVAILABLE or UPDATED_IS_AVAILABLE
        defaultWineOfferShouldBeFound("isAvailable.in=" + DEFAULT_IS_AVAILABLE + "," + UPDATED_IS_AVAILABLE);

        // Get all the wineOfferList where isAvailable equals to UPDATED_IS_AVAILABLE
        defaultWineOfferShouldNotBeFound("isAvailable.in=" + UPDATED_IS_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByIsAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where isAvailable is not null
        defaultWineOfferShouldBeFound("isAvailable.specified=true");

        // Get all the wineOfferList where isAvailable is null
        defaultWineOfferShouldNotBeFound("isAvailable.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineOffersByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where price equals to DEFAULT_PRICE
        defaultWineOfferShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the wineOfferList where price equals to UPDATED_PRICE
        defaultWineOfferShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where price not equals to DEFAULT_PRICE
        defaultWineOfferShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the wineOfferList where price not equals to UPDATED_PRICE
        defaultWineOfferShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultWineOfferShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the wineOfferList where price equals to UPDATED_PRICE
        defaultWineOfferShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where price is not null
        defaultWineOfferShouldBeFound("price.specified=true");

        // Get all the wineOfferList where price is null
        defaultWineOfferShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineOffersByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where price is greater than or equal to DEFAULT_PRICE
        defaultWineOfferShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the wineOfferList where price is greater than or equal to UPDATED_PRICE
        defaultWineOfferShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where price is less than or equal to DEFAULT_PRICE
        defaultWineOfferShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the wineOfferList where price is less than or equal to SMALLER_PRICE
        defaultWineOfferShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where price is less than DEFAULT_PRICE
        defaultWineOfferShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the wineOfferList where price is less than UPDATED_PRICE
        defaultWineOfferShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where price is greater than DEFAULT_PRICE
        defaultWineOfferShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the wineOfferList where price is greater than SMALLER_PRICE
        defaultWineOfferShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllWineOffersBySpecialPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where specialPrice equals to DEFAULT_SPECIAL_PRICE
        defaultWineOfferShouldBeFound("specialPrice.equals=" + DEFAULT_SPECIAL_PRICE);

        // Get all the wineOfferList where specialPrice equals to UPDATED_SPECIAL_PRICE
        defaultWineOfferShouldNotBeFound("specialPrice.equals=" + UPDATED_SPECIAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersBySpecialPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where specialPrice not equals to DEFAULT_SPECIAL_PRICE
        defaultWineOfferShouldNotBeFound("specialPrice.notEquals=" + DEFAULT_SPECIAL_PRICE);

        // Get all the wineOfferList where specialPrice not equals to UPDATED_SPECIAL_PRICE
        defaultWineOfferShouldBeFound("specialPrice.notEquals=" + UPDATED_SPECIAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersBySpecialPriceIsInShouldWork() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where specialPrice in DEFAULT_SPECIAL_PRICE or UPDATED_SPECIAL_PRICE
        defaultWineOfferShouldBeFound("specialPrice.in=" + DEFAULT_SPECIAL_PRICE + "," + UPDATED_SPECIAL_PRICE);

        // Get all the wineOfferList where specialPrice equals to UPDATED_SPECIAL_PRICE
        defaultWineOfferShouldNotBeFound("specialPrice.in=" + UPDATED_SPECIAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersBySpecialPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where specialPrice is not null
        defaultWineOfferShouldBeFound("specialPrice.specified=true");

        // Get all the wineOfferList where specialPrice is null
        defaultWineOfferShouldNotBeFound("specialPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineOffersBySpecialPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where specialPrice is greater than or equal to DEFAULT_SPECIAL_PRICE
        defaultWineOfferShouldBeFound("specialPrice.greaterThanOrEqual=" + DEFAULT_SPECIAL_PRICE);

        // Get all the wineOfferList where specialPrice is greater than or equal to UPDATED_SPECIAL_PRICE
        defaultWineOfferShouldNotBeFound("specialPrice.greaterThanOrEqual=" + UPDATED_SPECIAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersBySpecialPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where specialPrice is less than or equal to DEFAULT_SPECIAL_PRICE
        defaultWineOfferShouldBeFound("specialPrice.lessThanOrEqual=" + DEFAULT_SPECIAL_PRICE);

        // Get all the wineOfferList where specialPrice is less than or equal to SMALLER_SPECIAL_PRICE
        defaultWineOfferShouldNotBeFound("specialPrice.lessThanOrEqual=" + SMALLER_SPECIAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersBySpecialPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where specialPrice is less than DEFAULT_SPECIAL_PRICE
        defaultWineOfferShouldNotBeFound("specialPrice.lessThan=" + DEFAULT_SPECIAL_PRICE);

        // Get all the wineOfferList where specialPrice is less than UPDATED_SPECIAL_PRICE
        defaultWineOfferShouldBeFound("specialPrice.lessThan=" + UPDATED_SPECIAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineOffersBySpecialPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        // Get all the wineOfferList where specialPrice is greater than DEFAULT_SPECIAL_PRICE
        defaultWineOfferShouldNotBeFound("specialPrice.greaterThan=" + DEFAULT_SPECIAL_PRICE);

        // Get all the wineOfferList where specialPrice is greater than SMALLER_SPECIAL_PRICE
        defaultWineOfferShouldBeFound("specialPrice.greaterThan=" + SMALLER_SPECIAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllWineOffersByWineStockIsEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);
        WineStock wineStock = WineStockResourceIT.createEntity(em);
        em.persist(wineStock);
        em.flush();
        wineOffer.setWineStock(wineStock);
        wineOfferRepository.saveAndFlush(wineOffer);
        Long wineStockId = wineStock.getId();

        // Get all the wineOfferList where wineStock equals to wineStockId
        defaultWineOfferShouldBeFound("wineStockId.equals=" + wineStockId);

        // Get all the wineOfferList where wineStock equals to wineStockId + 1
        defaultWineOfferShouldNotBeFound("wineStockId.equals=" + (wineStockId + 1));
    }


    @Test
    @Transactional
    public void getAllWineOffersByWineStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);
        WineStore wineStore = WineStoreResourceIT.createEntity(em);
        em.persist(wineStore);
        em.flush();
        wineOffer.setWineStore(wineStore);
        wineOfferRepository.saveAndFlush(wineOffer);
        Long wineStoreId = wineStore.getId();

        // Get all the wineOfferList where wineStore equals to wineStoreId
        defaultWineOfferShouldBeFound("wineStoreId.equals=" + wineStoreId);

        // Get all the wineOfferList where wineStore equals to wineStoreId + 1
        defaultWineOfferShouldNotBeFound("wineStoreId.equals=" + (wineStoreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWineOfferShouldBeFound(String filter) throws Exception {
        restWineOfferMockMvc.perform(get("/api/wine-offers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].isAvailable").value(hasItem(DEFAULT_IS_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].specialPrice").value(hasItem(DEFAULT_SPECIAL_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restWineOfferMockMvc.perform(get("/api/wine-offers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWineOfferShouldNotBeFound(String filter) throws Exception {
        restWineOfferMockMvc.perform(get("/api/wine-offers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWineOfferMockMvc.perform(get("/api/wine-offers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWineOffer() throws Exception {
        // Get the wineOffer
        restWineOfferMockMvc.perform(get("/api/wine-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWineOffer() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        int databaseSizeBeforeUpdate = wineOfferRepository.findAll().size();

        // Update the wineOffer
        WineOffer updatedWineOffer = wineOfferRepository.findById(wineOffer.getId()).get();
        // Disconnect from session so that the updates on updatedWineOffer are not directly saved in db
        em.detach(updatedWineOffer);
        updatedWineOffer
            .isAvailable(UPDATED_IS_AVAILABLE)
            .price(UPDATED_PRICE)
            .specialPrice(UPDATED_SPECIAL_PRICE);
        WineOfferDTO wineOfferDTO = wineOfferMapper.toDto(updatedWineOffer);

        restWineOfferMockMvc.perform(put("/api/wine-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineOfferDTO)))
            .andExpect(status().isOk());

        // Validate the WineOffer in the database
        List<WineOffer> wineOfferList = wineOfferRepository.findAll();
        assertThat(wineOfferList).hasSize(databaseSizeBeforeUpdate);
        WineOffer testWineOffer = wineOfferList.get(wineOfferList.size() - 1);
        assertThat(testWineOffer.isIsAvailable()).isEqualTo(UPDATED_IS_AVAILABLE);
        assertThat(testWineOffer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testWineOffer.getSpecialPrice()).isEqualTo(UPDATED_SPECIAL_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingWineOffer() throws Exception {
        int databaseSizeBeforeUpdate = wineOfferRepository.findAll().size();

        // Create the WineOffer
        WineOfferDTO wineOfferDTO = wineOfferMapper.toDto(wineOffer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWineOfferMockMvc.perform(put("/api/wine-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineOfferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineOffer in the database
        List<WineOffer> wineOfferList = wineOfferRepository.findAll();
        assertThat(wineOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWineOffer() throws Exception {
        // Initialize the database
        wineOfferRepository.saveAndFlush(wineOffer);

        int databaseSizeBeforeDelete = wineOfferRepository.findAll().size();

        // Delete the wineOffer
        restWineOfferMockMvc.perform(delete("/api/wine-offers/{id}", wineOffer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WineOffer> wineOfferList = wineOfferRepository.findAll();
        assertThat(wineOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
