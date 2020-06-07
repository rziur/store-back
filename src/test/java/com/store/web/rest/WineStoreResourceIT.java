package com.store.web.rest;

import com.store.StoreApp;
import com.store.domain.WineStore;
import com.store.repository.WineStoreRepository;
import com.store.service.WineStoreService;
import com.store.service.dto.WineStoreDTO;
import com.store.service.mapper.WineStoreMapper;
import com.store.service.dto.WineStoreCriteria;
import com.store.service.WineStoreQueryService;

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
 * Integration tests for the {@link WineStoreResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WineStoreResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;
    private static final Integer SMALLER_RATING = 1 - 1;

    @Autowired
    private WineStoreRepository wineStoreRepository;

    @Autowired
    private WineStoreMapper wineStoreMapper;

    @Autowired
    private WineStoreService wineStoreService;

    @Autowired
    private WineStoreQueryService wineStoreQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWineStoreMockMvc;

    private WineStore wineStore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineStore createEntity(EntityManager em) {
        WineStore wineStore = new WineStore()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .rating(DEFAULT_RATING);
        return wineStore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineStore createUpdatedEntity(EntityManager em) {
        WineStore wineStore = new WineStore()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .rating(UPDATED_RATING);
        return wineStore;
    }

    @BeforeEach
    public void initTest() {
        wineStore = createEntity(em);
    }

    @Test
    @Transactional
    public void createWineStore() throws Exception {
        int databaseSizeBeforeCreate = wineStoreRepository.findAll().size();
        // Create the WineStore
        WineStoreDTO wineStoreDTO = wineStoreMapper.toDto(wineStore);
        restWineStoreMockMvc.perform(post("/api/wine-stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStoreDTO)))
            .andExpect(status().isCreated());

        // Validate the WineStore in the database
        List<WineStore> wineStoreList = wineStoreRepository.findAll();
        assertThat(wineStoreList).hasSize(databaseSizeBeforeCreate + 1);
        WineStore testWineStore = wineStoreList.get(wineStoreList.size() - 1);
        assertThat(testWineStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWineStore.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWineStore.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createWineStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wineStoreRepository.findAll().size();

        // Create the WineStore with an existing ID
        wineStore.setId(1L);
        WineStoreDTO wineStoreDTO = wineStoreMapper.toDto(wineStore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWineStoreMockMvc.perform(post("/api/wine-stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineStore in the database
        List<WineStore> wineStoreList = wineStoreRepository.findAll();
        assertThat(wineStoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWineStores() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList
        restWineStoreMockMvc.perform(get("/api/wine-stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)));
    }
    
    @Test
    @Transactional
    public void getWineStore() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get the wineStore
        restWineStoreMockMvc.perform(get("/api/wine-stores/{id}", wineStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wineStore.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING));
    }


    @Test
    @Transactional
    public void getWineStoresByIdFiltering() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        Long id = wineStore.getId();

        defaultWineStoreShouldBeFound("id.equals=" + id);
        defaultWineStoreShouldNotBeFound("id.notEquals=" + id);

        defaultWineStoreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWineStoreShouldNotBeFound("id.greaterThan=" + id);

        defaultWineStoreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWineStoreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWineStoresByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where name equals to DEFAULT_NAME
        defaultWineStoreShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the wineStoreList where name equals to UPDATED_NAME
        defaultWineStoreShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWineStoresByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where name not equals to DEFAULT_NAME
        defaultWineStoreShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the wineStoreList where name not equals to UPDATED_NAME
        defaultWineStoreShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWineStoresByNameIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWineStoreShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the wineStoreList where name equals to UPDATED_NAME
        defaultWineStoreShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWineStoresByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where name is not null
        defaultWineStoreShouldBeFound("name.specified=true");

        // Get all the wineStoreList where name is null
        defaultWineStoreShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStoresByNameContainsSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where name contains DEFAULT_NAME
        defaultWineStoreShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the wineStoreList where name contains UPDATED_NAME
        defaultWineStoreShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWineStoresByNameNotContainsSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where name does not contain DEFAULT_NAME
        defaultWineStoreShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the wineStoreList where name does not contain UPDATED_NAME
        defaultWineStoreShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllWineStoresByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where description equals to DEFAULT_DESCRIPTION
        defaultWineStoreShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the wineStoreList where description equals to UPDATED_DESCRIPTION
        defaultWineStoreShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineStoresByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where description not equals to DEFAULT_DESCRIPTION
        defaultWineStoreShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the wineStoreList where description not equals to UPDATED_DESCRIPTION
        defaultWineStoreShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineStoresByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultWineStoreShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the wineStoreList where description equals to UPDATED_DESCRIPTION
        defaultWineStoreShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineStoresByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where description is not null
        defaultWineStoreShouldBeFound("description.specified=true");

        // Get all the wineStoreList where description is null
        defaultWineStoreShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStoresByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where description contains DEFAULT_DESCRIPTION
        defaultWineStoreShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the wineStoreList where description contains UPDATED_DESCRIPTION
        defaultWineStoreShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineStoresByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where description does not contain DEFAULT_DESCRIPTION
        defaultWineStoreShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the wineStoreList where description does not contain UPDATED_DESCRIPTION
        defaultWineStoreShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllWineStoresByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where rating equals to DEFAULT_RATING
        defaultWineStoreShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the wineStoreList where rating equals to UPDATED_RATING
        defaultWineStoreShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllWineStoresByRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where rating not equals to DEFAULT_RATING
        defaultWineStoreShouldNotBeFound("rating.notEquals=" + DEFAULT_RATING);

        // Get all the wineStoreList where rating not equals to UPDATED_RATING
        defaultWineStoreShouldBeFound("rating.notEquals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllWineStoresByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultWineStoreShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the wineStoreList where rating equals to UPDATED_RATING
        defaultWineStoreShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllWineStoresByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where rating is not null
        defaultWineStoreShouldBeFound("rating.specified=true");

        // Get all the wineStoreList where rating is null
        defaultWineStoreShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStoresByRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where rating is greater than or equal to DEFAULT_RATING
        defaultWineStoreShouldBeFound("rating.greaterThanOrEqual=" + DEFAULT_RATING);

        // Get all the wineStoreList where rating is greater than or equal to (DEFAULT_RATING + 1)
        defaultWineStoreShouldNotBeFound("rating.greaterThanOrEqual=" + (DEFAULT_RATING + 1));
    }

    @Test
    @Transactional
    public void getAllWineStoresByRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where rating is less than or equal to DEFAULT_RATING
        defaultWineStoreShouldBeFound("rating.lessThanOrEqual=" + DEFAULT_RATING);

        // Get all the wineStoreList where rating is less than or equal to SMALLER_RATING
        defaultWineStoreShouldNotBeFound("rating.lessThanOrEqual=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    public void getAllWineStoresByRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where rating is less than DEFAULT_RATING
        defaultWineStoreShouldNotBeFound("rating.lessThan=" + DEFAULT_RATING);

        // Get all the wineStoreList where rating is less than (DEFAULT_RATING + 1)
        defaultWineStoreShouldBeFound("rating.lessThan=" + (DEFAULT_RATING + 1));
    }

    @Test
    @Transactional
    public void getAllWineStoresByRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        // Get all the wineStoreList where rating is greater than DEFAULT_RATING
        defaultWineStoreShouldNotBeFound("rating.greaterThan=" + DEFAULT_RATING);

        // Get all the wineStoreList where rating is greater than SMALLER_RATING
        defaultWineStoreShouldBeFound("rating.greaterThan=" + SMALLER_RATING);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWineStoreShouldBeFound(String filter) throws Exception {
        restWineStoreMockMvc.perform(get("/api/wine-stores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)));

        // Check, that the count call also returns 1
        restWineStoreMockMvc.perform(get("/api/wine-stores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWineStoreShouldNotBeFound(String filter) throws Exception {
        restWineStoreMockMvc.perform(get("/api/wine-stores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWineStoreMockMvc.perform(get("/api/wine-stores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWineStore() throws Exception {
        // Get the wineStore
        restWineStoreMockMvc.perform(get("/api/wine-stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWineStore() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        int databaseSizeBeforeUpdate = wineStoreRepository.findAll().size();

        // Update the wineStore
        WineStore updatedWineStore = wineStoreRepository.findById(wineStore.getId()).get();
        // Disconnect from session so that the updates on updatedWineStore are not directly saved in db
        em.detach(updatedWineStore);
        updatedWineStore
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .rating(UPDATED_RATING);
        WineStoreDTO wineStoreDTO = wineStoreMapper.toDto(updatedWineStore);

        restWineStoreMockMvc.perform(put("/api/wine-stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStoreDTO)))
            .andExpect(status().isOk());

        // Validate the WineStore in the database
        List<WineStore> wineStoreList = wineStoreRepository.findAll();
        assertThat(wineStoreList).hasSize(databaseSizeBeforeUpdate);
        WineStore testWineStore = wineStoreList.get(wineStoreList.size() - 1);
        assertThat(testWineStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWineStore.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWineStore.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingWineStore() throws Exception {
        int databaseSizeBeforeUpdate = wineStoreRepository.findAll().size();

        // Create the WineStore
        WineStoreDTO wineStoreDTO = wineStoreMapper.toDto(wineStore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWineStoreMockMvc.perform(put("/api/wine-stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineStore in the database
        List<WineStore> wineStoreList = wineStoreRepository.findAll();
        assertThat(wineStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWineStore() throws Exception {
        // Initialize the database
        wineStoreRepository.saveAndFlush(wineStore);

        int databaseSizeBeforeDelete = wineStoreRepository.findAll().size();

        // Delete the wineStore
        restWineStoreMockMvc.perform(delete("/api/wine-stores/{id}", wineStore.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WineStore> wineStoreList = wineStoreRepository.findAll();
        assertThat(wineStoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
