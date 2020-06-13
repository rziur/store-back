package com.store.web.rest;

import com.store.StoreApp;
import com.store.domain.WineStock;
import com.store.repository.WineStockRepository;
import com.store.service.WineStockService;
import com.store.service.dto.WineStockDTO;
import com.store.service.mapper.WineStockMapper;
import com.store.service.dto.WineStockCriteria;
import com.store.service.WineStockQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WineStockResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WineStockResourceIT {

    private static final String DEFAULT_SUPPLIER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VINTAGE = "AAAAAAAAAA";
    private static final String UPDATED_VINTAGE = "BBBBBBBBBB";

    private static final Float DEFAULT_ALCOHOL_LEVEL = 0F;
    private static final Float UPDATED_ALCOHOL_LEVEL = 1F;
    private static final Float SMALLER_ALCOHOL_LEVEL = 0F - 1F;

    private static final String DEFAULT_PRINT_RUN = "AAAAAAAAAA";
    private static final String UPDATED_PRINT_RUN = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final Integer DEFAULT_PHYSICAL = 1;
    private static final Integer UPDATED_PHYSICAL = 2;
    private static final Integer SMALLER_PHYSICAL = 1 - 1;

    private static final Integer DEFAULT_PURCHASES = 1;
    private static final Integer UPDATED_PURCHASES = 2;
    private static final Integer SMALLER_PURCHASES = 1 - 1;

    private static final Integer DEFAULT_SALES = 1;
    private static final Integer UPDATED_SALES = 2;
    private static final Integer SMALLER_SALES = 1 - 1;

    private static final Integer DEFAULT_AVAILABILITY = 1;
    private static final Integer UPDATED_AVAILABILITY = 2;
    private static final Integer SMALLER_AVAILABILITY = 1 - 1;

    private static final Float DEFAULT_PX_REV_COL = 1F;
    private static final Float UPDATED_PX_REV_COL = 2F;
    private static final Float SMALLER_PX_REV_COL = 1F - 1F;

    private static final Float DEFAULT_LAST_PURCHASE_PRICE = 1F;
    private static final Float UPDATED_LAST_PURCHASE_PRICE = 2F;
    private static final Float SMALLER_LAST_PURCHASE_PRICE = 1F - 1F;

    private static final Instant DEFAULT_LAST_PURCHASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_PURCHASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_IMPORT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_IMPORT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private WineStockRepository wineStockRepository;

    @Autowired
    private WineStockMapper wineStockMapper;

    @Autowired
    private WineStockService wineStockService;

    @Autowired
    private WineStockQueryService wineStockQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWineStockMockMvc;

    private WineStock wineStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineStock createEntity(EntityManager em) {
        WineStock wineStock = new WineStock()
            .supplier(DEFAULT_SUPPLIER)
            .region(DEFAULT_REGION)
            .description(DEFAULT_DESCRIPTION)
            .vintage(DEFAULT_VINTAGE)
            .alcoholLevel(DEFAULT_ALCOHOL_LEVEL)
            .printRun(DEFAULT_PRINT_RUN)
            .price(DEFAULT_PRICE)
            .physical(DEFAULT_PHYSICAL)
            .purchases(DEFAULT_PURCHASES)
            .sales(DEFAULT_SALES)
            .availability(DEFAULT_AVAILABILITY)
            .pxRevCol(DEFAULT_PX_REV_COL)
            .lastPurchasePrice(DEFAULT_LAST_PURCHASE_PRICE)
            .lastPurchaseDate(DEFAULT_LAST_PURCHASE_DATE)
            .dateImport(DEFAULT_DATE_IMPORT)
            .imageUrl(DEFAULT_IMAGE_URL);
        return wineStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineStock createUpdatedEntity(EntityManager em) {
        WineStock wineStock = new WineStock()
            .supplier(UPDATED_SUPPLIER)
            .region(UPDATED_REGION)
            .description(UPDATED_DESCRIPTION)
            .vintage(UPDATED_VINTAGE)
            .alcoholLevel(UPDATED_ALCOHOL_LEVEL)
            .printRun(UPDATED_PRINT_RUN)
            .price(UPDATED_PRICE)
            .physical(UPDATED_PHYSICAL)
            .purchases(UPDATED_PURCHASES)
            .sales(UPDATED_SALES)
            .availability(UPDATED_AVAILABILITY)
            .pxRevCol(UPDATED_PX_REV_COL)
            .lastPurchasePrice(UPDATED_LAST_PURCHASE_PRICE)
            .lastPurchaseDate(UPDATED_LAST_PURCHASE_DATE)
            .dateImport(UPDATED_DATE_IMPORT)
            .imageUrl(UPDATED_IMAGE_URL);
        return wineStock;
    }

    @BeforeEach
    public void initTest() {
        wineStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createWineStock() throws Exception {
        int databaseSizeBeforeCreate = wineStockRepository.findAll().size();
        // Create the WineStock
        WineStockDTO wineStockDTO = wineStockMapper.toDto(wineStock);
        restWineStockMockMvc.perform(post("/api/wine-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStockDTO)))
            .andExpect(status().isCreated());

        // Validate the WineStock in the database
        List<WineStock> wineStockList = wineStockRepository.findAll();
        assertThat(wineStockList).hasSize(databaseSizeBeforeCreate + 1);
        WineStock testWineStock = wineStockList.get(wineStockList.size() - 1);
        assertThat(testWineStock.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testWineStock.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testWineStock.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWineStock.getVintage()).isEqualTo(DEFAULT_VINTAGE);
        assertThat(testWineStock.getAlcoholLevel()).isEqualTo(DEFAULT_ALCOHOL_LEVEL);
        assertThat(testWineStock.getPrintRun()).isEqualTo(DEFAULT_PRINT_RUN);
        assertThat(testWineStock.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testWineStock.getPhysical()).isEqualTo(DEFAULT_PHYSICAL);
        assertThat(testWineStock.getPurchases()).isEqualTo(DEFAULT_PURCHASES);
        assertThat(testWineStock.getSales()).isEqualTo(DEFAULT_SALES);
        assertThat(testWineStock.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
        assertThat(testWineStock.getPxRevCol()).isEqualTo(DEFAULT_PX_REV_COL);
        assertThat(testWineStock.getLastPurchasePrice()).isEqualTo(DEFAULT_LAST_PURCHASE_PRICE);
        assertThat(testWineStock.getLastPurchaseDate()).isEqualTo(DEFAULT_LAST_PURCHASE_DATE);
        assertThat(testWineStock.getDateImport()).isEqualTo(DEFAULT_DATE_IMPORT);
        assertThat(testWineStock.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createWineStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wineStockRepository.findAll().size();

        // Create the WineStock with an existing ID
        wineStock.setId(1L);
        WineStockDTO wineStockDTO = wineStockMapper.toDto(wineStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWineStockMockMvc.perform(post("/api/wine-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineStock in the database
        List<WineStock> wineStockList = wineStockRepository.findAll();
        assertThat(wineStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWineStocks() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList
        restWineStockMockMvc.perform(get("/api/wine-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].vintage").value(hasItem(DEFAULT_VINTAGE)))
            .andExpect(jsonPath("$.[*].alcoholLevel").value(hasItem(DEFAULT_ALCOHOL_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].printRun").value(hasItem(DEFAULT_PRINT_RUN)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].physical").value(hasItem(DEFAULT_PHYSICAL)))
            .andExpect(jsonPath("$.[*].purchases").value(hasItem(DEFAULT_PURCHASES)))
            .andExpect(jsonPath("$.[*].sales").value(hasItem(DEFAULT_SALES)))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY)))
            .andExpect(jsonPath("$.[*].pxRevCol").value(hasItem(DEFAULT_PX_REV_COL.doubleValue())))
            .andExpect(jsonPath("$.[*].lastPurchasePrice").value(hasItem(DEFAULT_LAST_PURCHASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastPurchaseDate").value(hasItem(DEFAULT_LAST_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateImport").value(hasItem(DEFAULT_DATE_IMPORT.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }
    
    @Test
    @Transactional
    public void getWineStock() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get the wineStock
        restWineStockMockMvc.perform(get("/api/wine-stocks/{id}", wineStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wineStock.getId().intValue()))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.vintage").value(DEFAULT_VINTAGE))
            .andExpect(jsonPath("$.alcoholLevel").value(DEFAULT_ALCOHOL_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.printRun").value(DEFAULT_PRINT_RUN))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.physical").value(DEFAULT_PHYSICAL))
            .andExpect(jsonPath("$.purchases").value(DEFAULT_PURCHASES))
            .andExpect(jsonPath("$.sales").value(DEFAULT_SALES))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY))
            .andExpect(jsonPath("$.pxRevCol").value(DEFAULT_PX_REV_COL.doubleValue()))
            .andExpect(jsonPath("$.lastPurchasePrice").value(DEFAULT_LAST_PURCHASE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.lastPurchaseDate").value(DEFAULT_LAST_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.dateImport").value(DEFAULT_DATE_IMPORT.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }


    @Test
    @Transactional
    public void getWineStocksByIdFiltering() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        Long id = wineStock.getId();

        defaultWineStockShouldBeFound("id.equals=" + id);
        defaultWineStockShouldNotBeFound("id.notEquals=" + id);

        defaultWineStockShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWineStockShouldNotBeFound("id.greaterThan=" + id);

        defaultWineStockShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWineStockShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWineStocksBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where supplier equals to DEFAULT_SUPPLIER
        defaultWineStockShouldBeFound("supplier.equals=" + DEFAULT_SUPPLIER);

        // Get all the wineStockList where supplier equals to UPDATED_SUPPLIER
        defaultWineStockShouldNotBeFound("supplier.equals=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySupplierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where supplier not equals to DEFAULT_SUPPLIER
        defaultWineStockShouldNotBeFound("supplier.notEquals=" + DEFAULT_SUPPLIER);

        // Get all the wineStockList where supplier not equals to UPDATED_SUPPLIER
        defaultWineStockShouldBeFound("supplier.notEquals=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySupplierIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where supplier in DEFAULT_SUPPLIER or UPDATED_SUPPLIER
        defaultWineStockShouldBeFound("supplier.in=" + DEFAULT_SUPPLIER + "," + UPDATED_SUPPLIER);

        // Get all the wineStockList where supplier equals to UPDATED_SUPPLIER
        defaultWineStockShouldNotBeFound("supplier.in=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySupplierIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where supplier is not null
        defaultWineStockShouldBeFound("supplier.specified=true");

        // Get all the wineStockList where supplier is null
        defaultWineStockShouldNotBeFound("supplier.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStocksBySupplierContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where supplier contains DEFAULT_SUPPLIER
        defaultWineStockShouldBeFound("supplier.contains=" + DEFAULT_SUPPLIER);

        // Get all the wineStockList where supplier contains UPDATED_SUPPLIER
        defaultWineStockShouldNotBeFound("supplier.contains=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySupplierNotContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where supplier does not contain DEFAULT_SUPPLIER
        defaultWineStockShouldNotBeFound("supplier.doesNotContain=" + DEFAULT_SUPPLIER);

        // Get all the wineStockList where supplier does not contain UPDATED_SUPPLIER
        defaultWineStockShouldBeFound("supplier.doesNotContain=" + UPDATED_SUPPLIER);
    }


    @Test
    @Transactional
    public void getAllWineStocksByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where region equals to DEFAULT_REGION
        defaultWineStockShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the wineStockList where region equals to UPDATED_REGION
        defaultWineStockShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllWineStocksByRegionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where region not equals to DEFAULT_REGION
        defaultWineStockShouldNotBeFound("region.notEquals=" + DEFAULT_REGION);

        // Get all the wineStockList where region not equals to UPDATED_REGION
        defaultWineStockShouldBeFound("region.notEquals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllWineStocksByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where region in DEFAULT_REGION or UPDATED_REGION
        defaultWineStockShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the wineStockList where region equals to UPDATED_REGION
        defaultWineStockShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllWineStocksByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where region is not null
        defaultWineStockShouldBeFound("region.specified=true");

        // Get all the wineStockList where region is null
        defaultWineStockShouldNotBeFound("region.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStocksByRegionContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where region contains DEFAULT_REGION
        defaultWineStockShouldBeFound("region.contains=" + DEFAULT_REGION);

        // Get all the wineStockList where region contains UPDATED_REGION
        defaultWineStockShouldNotBeFound("region.contains=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllWineStocksByRegionNotContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where region does not contain DEFAULT_REGION
        defaultWineStockShouldNotBeFound("region.doesNotContain=" + DEFAULT_REGION);

        // Get all the wineStockList where region does not contain UPDATED_REGION
        defaultWineStockShouldBeFound("region.doesNotContain=" + UPDATED_REGION);
    }


    @Test
    @Transactional
    public void getAllWineStocksByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where description equals to DEFAULT_DESCRIPTION
        defaultWineStockShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the wineStockList where description equals to UPDATED_DESCRIPTION
        defaultWineStockShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineStocksByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where description not equals to DEFAULT_DESCRIPTION
        defaultWineStockShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the wineStockList where description not equals to UPDATED_DESCRIPTION
        defaultWineStockShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineStocksByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultWineStockShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the wineStockList where description equals to UPDATED_DESCRIPTION
        defaultWineStockShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineStocksByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where description is not null
        defaultWineStockShouldBeFound("description.specified=true");

        // Get all the wineStockList where description is null
        defaultWineStockShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStocksByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where description contains DEFAULT_DESCRIPTION
        defaultWineStockShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the wineStockList where description contains UPDATED_DESCRIPTION
        defaultWineStockShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineStocksByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where description does not contain DEFAULT_DESCRIPTION
        defaultWineStockShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the wineStockList where description does not contain UPDATED_DESCRIPTION
        defaultWineStockShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllWineStocksByVintageIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where vintage equals to DEFAULT_VINTAGE
        defaultWineStockShouldBeFound("vintage.equals=" + DEFAULT_VINTAGE);

        // Get all the wineStockList where vintage equals to UPDATED_VINTAGE
        defaultWineStockShouldNotBeFound("vintage.equals=" + UPDATED_VINTAGE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByVintageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where vintage not equals to DEFAULT_VINTAGE
        defaultWineStockShouldNotBeFound("vintage.notEquals=" + DEFAULT_VINTAGE);

        // Get all the wineStockList where vintage not equals to UPDATED_VINTAGE
        defaultWineStockShouldBeFound("vintage.notEquals=" + UPDATED_VINTAGE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByVintageIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where vintage in DEFAULT_VINTAGE or UPDATED_VINTAGE
        defaultWineStockShouldBeFound("vintage.in=" + DEFAULT_VINTAGE + "," + UPDATED_VINTAGE);

        // Get all the wineStockList where vintage equals to UPDATED_VINTAGE
        defaultWineStockShouldNotBeFound("vintage.in=" + UPDATED_VINTAGE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByVintageIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where vintage is not null
        defaultWineStockShouldBeFound("vintage.specified=true");

        // Get all the wineStockList where vintage is null
        defaultWineStockShouldNotBeFound("vintage.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStocksByVintageContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where vintage contains DEFAULT_VINTAGE
        defaultWineStockShouldBeFound("vintage.contains=" + DEFAULT_VINTAGE);

        // Get all the wineStockList where vintage contains UPDATED_VINTAGE
        defaultWineStockShouldNotBeFound("vintage.contains=" + UPDATED_VINTAGE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByVintageNotContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where vintage does not contain DEFAULT_VINTAGE
        defaultWineStockShouldNotBeFound("vintage.doesNotContain=" + DEFAULT_VINTAGE);

        // Get all the wineStockList where vintage does not contain UPDATED_VINTAGE
        defaultWineStockShouldBeFound("vintage.doesNotContain=" + UPDATED_VINTAGE);
    }


    @Test
    @Transactional
    public void getAllWineStocksByAlcoholLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where alcoholLevel equals to DEFAULT_ALCOHOL_LEVEL
        defaultWineStockShouldBeFound("alcoholLevel.equals=" + DEFAULT_ALCOHOL_LEVEL);

        // Get all the wineStockList where alcoholLevel equals to UPDATED_ALCOHOL_LEVEL
        defaultWineStockShouldNotBeFound("alcoholLevel.equals=" + UPDATED_ALCOHOL_LEVEL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAlcoholLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where alcoholLevel not equals to DEFAULT_ALCOHOL_LEVEL
        defaultWineStockShouldNotBeFound("alcoholLevel.notEquals=" + DEFAULT_ALCOHOL_LEVEL);

        // Get all the wineStockList where alcoholLevel not equals to UPDATED_ALCOHOL_LEVEL
        defaultWineStockShouldBeFound("alcoholLevel.notEquals=" + UPDATED_ALCOHOL_LEVEL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAlcoholLevelIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where alcoholLevel in DEFAULT_ALCOHOL_LEVEL or UPDATED_ALCOHOL_LEVEL
        defaultWineStockShouldBeFound("alcoholLevel.in=" + DEFAULT_ALCOHOL_LEVEL + "," + UPDATED_ALCOHOL_LEVEL);

        // Get all the wineStockList where alcoholLevel equals to UPDATED_ALCOHOL_LEVEL
        defaultWineStockShouldNotBeFound("alcoholLevel.in=" + UPDATED_ALCOHOL_LEVEL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAlcoholLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where alcoholLevel is not null
        defaultWineStockShouldBeFound("alcoholLevel.specified=true");

        // Get all the wineStockList where alcoholLevel is null
        defaultWineStockShouldNotBeFound("alcoholLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByAlcoholLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where alcoholLevel is greater than or equal to DEFAULT_ALCOHOL_LEVEL
        defaultWineStockShouldBeFound("alcoholLevel.greaterThanOrEqual=" + DEFAULT_ALCOHOL_LEVEL);

        // Get all the wineStockList where alcoholLevel is greater than or equal to (DEFAULT_ALCOHOL_LEVEL + 1)
        defaultWineStockShouldNotBeFound("alcoholLevel.greaterThanOrEqual=" + (DEFAULT_ALCOHOL_LEVEL + 1));
    }

    @Test
    @Transactional
    public void getAllWineStocksByAlcoholLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where alcoholLevel is less than or equal to DEFAULT_ALCOHOL_LEVEL
        defaultWineStockShouldBeFound("alcoholLevel.lessThanOrEqual=" + DEFAULT_ALCOHOL_LEVEL);

        // Get all the wineStockList where alcoholLevel is less than or equal to SMALLER_ALCOHOL_LEVEL
        defaultWineStockShouldNotBeFound("alcoholLevel.lessThanOrEqual=" + SMALLER_ALCOHOL_LEVEL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAlcoholLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where alcoholLevel is less than DEFAULT_ALCOHOL_LEVEL
        defaultWineStockShouldNotBeFound("alcoholLevel.lessThan=" + DEFAULT_ALCOHOL_LEVEL);

        // Get all the wineStockList where alcoholLevel is less than (DEFAULT_ALCOHOL_LEVEL + 1)
        defaultWineStockShouldBeFound("alcoholLevel.lessThan=" + (DEFAULT_ALCOHOL_LEVEL + 1));
    }

    @Test
    @Transactional
    public void getAllWineStocksByAlcoholLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where alcoholLevel is greater than DEFAULT_ALCOHOL_LEVEL
        defaultWineStockShouldNotBeFound("alcoholLevel.greaterThan=" + DEFAULT_ALCOHOL_LEVEL);

        // Get all the wineStockList where alcoholLevel is greater than SMALLER_ALCOHOL_LEVEL
        defaultWineStockShouldBeFound("alcoholLevel.greaterThan=" + SMALLER_ALCOHOL_LEVEL);
    }


    @Test
    @Transactional
    public void getAllWineStocksByPrintRunIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where printRun equals to DEFAULT_PRINT_RUN
        defaultWineStockShouldBeFound("printRun.equals=" + DEFAULT_PRINT_RUN);

        // Get all the wineStockList where printRun equals to UPDATED_PRINT_RUN
        defaultWineStockShouldNotBeFound("printRun.equals=" + UPDATED_PRINT_RUN);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPrintRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where printRun not equals to DEFAULT_PRINT_RUN
        defaultWineStockShouldNotBeFound("printRun.notEquals=" + DEFAULT_PRINT_RUN);

        // Get all the wineStockList where printRun not equals to UPDATED_PRINT_RUN
        defaultWineStockShouldBeFound("printRun.notEquals=" + UPDATED_PRINT_RUN);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPrintRunIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where printRun in DEFAULT_PRINT_RUN or UPDATED_PRINT_RUN
        defaultWineStockShouldBeFound("printRun.in=" + DEFAULT_PRINT_RUN + "," + UPDATED_PRINT_RUN);

        // Get all the wineStockList where printRun equals to UPDATED_PRINT_RUN
        defaultWineStockShouldNotBeFound("printRun.in=" + UPDATED_PRINT_RUN);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPrintRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where printRun is not null
        defaultWineStockShouldBeFound("printRun.specified=true");

        // Get all the wineStockList where printRun is null
        defaultWineStockShouldNotBeFound("printRun.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStocksByPrintRunContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where printRun contains DEFAULT_PRINT_RUN
        defaultWineStockShouldBeFound("printRun.contains=" + DEFAULT_PRINT_RUN);

        // Get all the wineStockList where printRun contains UPDATED_PRINT_RUN
        defaultWineStockShouldNotBeFound("printRun.contains=" + UPDATED_PRINT_RUN);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPrintRunNotContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where printRun does not contain DEFAULT_PRINT_RUN
        defaultWineStockShouldNotBeFound("printRun.doesNotContain=" + DEFAULT_PRINT_RUN);

        // Get all the wineStockList where printRun does not contain UPDATED_PRINT_RUN
        defaultWineStockShouldBeFound("printRun.doesNotContain=" + UPDATED_PRINT_RUN);
    }


    @Test
    @Transactional
    public void getAllWineStocksByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where price equals to DEFAULT_PRICE
        defaultWineStockShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the wineStockList where price equals to UPDATED_PRICE
        defaultWineStockShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where price not equals to DEFAULT_PRICE
        defaultWineStockShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the wineStockList where price not equals to UPDATED_PRICE
        defaultWineStockShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultWineStockShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the wineStockList where price equals to UPDATED_PRICE
        defaultWineStockShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where price is not null
        defaultWineStockShouldBeFound("price.specified=true");

        // Get all the wineStockList where price is null
        defaultWineStockShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where price is greater than or equal to DEFAULT_PRICE
        defaultWineStockShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the wineStockList where price is greater than or equal to UPDATED_PRICE
        defaultWineStockShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where price is less than or equal to DEFAULT_PRICE
        defaultWineStockShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the wineStockList where price is less than or equal to SMALLER_PRICE
        defaultWineStockShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where price is less than DEFAULT_PRICE
        defaultWineStockShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the wineStockList where price is less than UPDATED_PRICE
        defaultWineStockShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where price is greater than DEFAULT_PRICE
        defaultWineStockShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the wineStockList where price is greater than SMALLER_PRICE
        defaultWineStockShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllWineStocksByPhysicalIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where physical equals to DEFAULT_PHYSICAL
        defaultWineStockShouldBeFound("physical.equals=" + DEFAULT_PHYSICAL);

        // Get all the wineStockList where physical equals to UPDATED_PHYSICAL
        defaultWineStockShouldNotBeFound("physical.equals=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPhysicalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where physical not equals to DEFAULT_PHYSICAL
        defaultWineStockShouldNotBeFound("physical.notEquals=" + DEFAULT_PHYSICAL);

        // Get all the wineStockList where physical not equals to UPDATED_PHYSICAL
        defaultWineStockShouldBeFound("physical.notEquals=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPhysicalIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where physical in DEFAULT_PHYSICAL or UPDATED_PHYSICAL
        defaultWineStockShouldBeFound("physical.in=" + DEFAULT_PHYSICAL + "," + UPDATED_PHYSICAL);

        // Get all the wineStockList where physical equals to UPDATED_PHYSICAL
        defaultWineStockShouldNotBeFound("physical.in=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPhysicalIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where physical is not null
        defaultWineStockShouldBeFound("physical.specified=true");

        // Get all the wineStockList where physical is null
        defaultWineStockShouldNotBeFound("physical.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByPhysicalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where physical is greater than or equal to DEFAULT_PHYSICAL
        defaultWineStockShouldBeFound("physical.greaterThanOrEqual=" + DEFAULT_PHYSICAL);

        // Get all the wineStockList where physical is greater than or equal to UPDATED_PHYSICAL
        defaultWineStockShouldNotBeFound("physical.greaterThanOrEqual=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPhysicalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where physical is less than or equal to DEFAULT_PHYSICAL
        defaultWineStockShouldBeFound("physical.lessThanOrEqual=" + DEFAULT_PHYSICAL);

        // Get all the wineStockList where physical is less than or equal to SMALLER_PHYSICAL
        defaultWineStockShouldNotBeFound("physical.lessThanOrEqual=" + SMALLER_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPhysicalIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where physical is less than DEFAULT_PHYSICAL
        defaultWineStockShouldNotBeFound("physical.lessThan=" + DEFAULT_PHYSICAL);

        // Get all the wineStockList where physical is less than UPDATED_PHYSICAL
        defaultWineStockShouldBeFound("physical.lessThan=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPhysicalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where physical is greater than DEFAULT_PHYSICAL
        defaultWineStockShouldNotBeFound("physical.greaterThan=" + DEFAULT_PHYSICAL);

        // Get all the wineStockList where physical is greater than SMALLER_PHYSICAL
        defaultWineStockShouldBeFound("physical.greaterThan=" + SMALLER_PHYSICAL);
    }


    @Test
    @Transactional
    public void getAllWineStocksByPurchasesIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where purchases equals to DEFAULT_PURCHASES
        defaultWineStockShouldBeFound("purchases.equals=" + DEFAULT_PURCHASES);

        // Get all the wineStockList where purchases equals to UPDATED_PURCHASES
        defaultWineStockShouldNotBeFound("purchases.equals=" + UPDATED_PURCHASES);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPurchasesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where purchases not equals to DEFAULT_PURCHASES
        defaultWineStockShouldNotBeFound("purchases.notEquals=" + DEFAULT_PURCHASES);

        // Get all the wineStockList where purchases not equals to UPDATED_PURCHASES
        defaultWineStockShouldBeFound("purchases.notEquals=" + UPDATED_PURCHASES);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPurchasesIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where purchases in DEFAULT_PURCHASES or UPDATED_PURCHASES
        defaultWineStockShouldBeFound("purchases.in=" + DEFAULT_PURCHASES + "," + UPDATED_PURCHASES);

        // Get all the wineStockList where purchases equals to UPDATED_PURCHASES
        defaultWineStockShouldNotBeFound("purchases.in=" + UPDATED_PURCHASES);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPurchasesIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where purchases is not null
        defaultWineStockShouldBeFound("purchases.specified=true");

        // Get all the wineStockList where purchases is null
        defaultWineStockShouldNotBeFound("purchases.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByPurchasesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where purchases is greater than or equal to DEFAULT_PURCHASES
        defaultWineStockShouldBeFound("purchases.greaterThanOrEqual=" + DEFAULT_PURCHASES);

        // Get all the wineStockList where purchases is greater than or equal to UPDATED_PURCHASES
        defaultWineStockShouldNotBeFound("purchases.greaterThanOrEqual=" + UPDATED_PURCHASES);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPurchasesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where purchases is less than or equal to DEFAULT_PURCHASES
        defaultWineStockShouldBeFound("purchases.lessThanOrEqual=" + DEFAULT_PURCHASES);

        // Get all the wineStockList where purchases is less than or equal to SMALLER_PURCHASES
        defaultWineStockShouldNotBeFound("purchases.lessThanOrEqual=" + SMALLER_PURCHASES);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPurchasesIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where purchases is less than DEFAULT_PURCHASES
        defaultWineStockShouldNotBeFound("purchases.lessThan=" + DEFAULT_PURCHASES);

        // Get all the wineStockList where purchases is less than UPDATED_PURCHASES
        defaultWineStockShouldBeFound("purchases.lessThan=" + UPDATED_PURCHASES);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPurchasesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where purchases is greater than DEFAULT_PURCHASES
        defaultWineStockShouldNotBeFound("purchases.greaterThan=" + DEFAULT_PURCHASES);

        // Get all the wineStockList where purchases is greater than SMALLER_PURCHASES
        defaultWineStockShouldBeFound("purchases.greaterThan=" + SMALLER_PURCHASES);
    }


    @Test
    @Transactional
    public void getAllWineStocksBySalesIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where sales equals to DEFAULT_SALES
        defaultWineStockShouldBeFound("sales.equals=" + DEFAULT_SALES);

        // Get all the wineStockList where sales equals to UPDATED_SALES
        defaultWineStockShouldNotBeFound("sales.equals=" + UPDATED_SALES);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySalesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where sales not equals to DEFAULT_SALES
        defaultWineStockShouldNotBeFound("sales.notEquals=" + DEFAULT_SALES);

        // Get all the wineStockList where sales not equals to UPDATED_SALES
        defaultWineStockShouldBeFound("sales.notEquals=" + UPDATED_SALES);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySalesIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where sales in DEFAULT_SALES or UPDATED_SALES
        defaultWineStockShouldBeFound("sales.in=" + DEFAULT_SALES + "," + UPDATED_SALES);

        // Get all the wineStockList where sales equals to UPDATED_SALES
        defaultWineStockShouldNotBeFound("sales.in=" + UPDATED_SALES);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySalesIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where sales is not null
        defaultWineStockShouldBeFound("sales.specified=true");

        // Get all the wineStockList where sales is null
        defaultWineStockShouldNotBeFound("sales.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksBySalesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where sales is greater than or equal to DEFAULT_SALES
        defaultWineStockShouldBeFound("sales.greaterThanOrEqual=" + DEFAULT_SALES);

        // Get all the wineStockList where sales is greater than or equal to UPDATED_SALES
        defaultWineStockShouldNotBeFound("sales.greaterThanOrEqual=" + UPDATED_SALES);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySalesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where sales is less than or equal to DEFAULT_SALES
        defaultWineStockShouldBeFound("sales.lessThanOrEqual=" + DEFAULT_SALES);

        // Get all the wineStockList where sales is less than or equal to SMALLER_SALES
        defaultWineStockShouldNotBeFound("sales.lessThanOrEqual=" + SMALLER_SALES);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySalesIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where sales is less than DEFAULT_SALES
        defaultWineStockShouldNotBeFound("sales.lessThan=" + DEFAULT_SALES);

        // Get all the wineStockList where sales is less than UPDATED_SALES
        defaultWineStockShouldBeFound("sales.lessThan=" + UPDATED_SALES);
    }

    @Test
    @Transactional
    public void getAllWineStocksBySalesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where sales is greater than DEFAULT_SALES
        defaultWineStockShouldNotBeFound("sales.greaterThan=" + DEFAULT_SALES);

        // Get all the wineStockList where sales is greater than SMALLER_SALES
        defaultWineStockShouldBeFound("sales.greaterThan=" + SMALLER_SALES);
    }


    @Test
    @Transactional
    public void getAllWineStocksByAvailabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where availability equals to DEFAULT_AVAILABILITY
        defaultWineStockShouldBeFound("availability.equals=" + DEFAULT_AVAILABILITY);

        // Get all the wineStockList where availability equals to UPDATED_AVAILABILITY
        defaultWineStockShouldNotBeFound("availability.equals=" + UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAvailabilityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where availability not equals to DEFAULT_AVAILABILITY
        defaultWineStockShouldNotBeFound("availability.notEquals=" + DEFAULT_AVAILABILITY);

        // Get all the wineStockList where availability not equals to UPDATED_AVAILABILITY
        defaultWineStockShouldBeFound("availability.notEquals=" + UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAvailabilityIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where availability in DEFAULT_AVAILABILITY or UPDATED_AVAILABILITY
        defaultWineStockShouldBeFound("availability.in=" + DEFAULT_AVAILABILITY + "," + UPDATED_AVAILABILITY);

        // Get all the wineStockList where availability equals to UPDATED_AVAILABILITY
        defaultWineStockShouldNotBeFound("availability.in=" + UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAvailabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where availability is not null
        defaultWineStockShouldBeFound("availability.specified=true");

        // Get all the wineStockList where availability is null
        defaultWineStockShouldNotBeFound("availability.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByAvailabilityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where availability is greater than or equal to DEFAULT_AVAILABILITY
        defaultWineStockShouldBeFound("availability.greaterThanOrEqual=" + DEFAULT_AVAILABILITY);

        // Get all the wineStockList where availability is greater than or equal to UPDATED_AVAILABILITY
        defaultWineStockShouldNotBeFound("availability.greaterThanOrEqual=" + UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAvailabilityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where availability is less than or equal to DEFAULT_AVAILABILITY
        defaultWineStockShouldBeFound("availability.lessThanOrEqual=" + DEFAULT_AVAILABILITY);

        // Get all the wineStockList where availability is less than or equal to SMALLER_AVAILABILITY
        defaultWineStockShouldNotBeFound("availability.lessThanOrEqual=" + SMALLER_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAvailabilityIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where availability is less than DEFAULT_AVAILABILITY
        defaultWineStockShouldNotBeFound("availability.lessThan=" + DEFAULT_AVAILABILITY);

        // Get all the wineStockList where availability is less than UPDATED_AVAILABILITY
        defaultWineStockShouldBeFound("availability.lessThan=" + UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllWineStocksByAvailabilityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where availability is greater than DEFAULT_AVAILABILITY
        defaultWineStockShouldNotBeFound("availability.greaterThan=" + DEFAULT_AVAILABILITY);

        // Get all the wineStockList where availability is greater than SMALLER_AVAILABILITY
        defaultWineStockShouldBeFound("availability.greaterThan=" + SMALLER_AVAILABILITY);
    }


    @Test
    @Transactional
    public void getAllWineStocksByPxRevColIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where pxRevCol equals to DEFAULT_PX_REV_COL
        defaultWineStockShouldBeFound("pxRevCol.equals=" + DEFAULT_PX_REV_COL);

        // Get all the wineStockList where pxRevCol equals to UPDATED_PX_REV_COL
        defaultWineStockShouldNotBeFound("pxRevCol.equals=" + UPDATED_PX_REV_COL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPxRevColIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where pxRevCol not equals to DEFAULT_PX_REV_COL
        defaultWineStockShouldNotBeFound("pxRevCol.notEquals=" + DEFAULT_PX_REV_COL);

        // Get all the wineStockList where pxRevCol not equals to UPDATED_PX_REV_COL
        defaultWineStockShouldBeFound("pxRevCol.notEquals=" + UPDATED_PX_REV_COL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPxRevColIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where pxRevCol in DEFAULT_PX_REV_COL or UPDATED_PX_REV_COL
        defaultWineStockShouldBeFound("pxRevCol.in=" + DEFAULT_PX_REV_COL + "," + UPDATED_PX_REV_COL);

        // Get all the wineStockList where pxRevCol equals to UPDATED_PX_REV_COL
        defaultWineStockShouldNotBeFound("pxRevCol.in=" + UPDATED_PX_REV_COL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPxRevColIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where pxRevCol is not null
        defaultWineStockShouldBeFound("pxRevCol.specified=true");

        // Get all the wineStockList where pxRevCol is null
        defaultWineStockShouldNotBeFound("pxRevCol.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByPxRevColIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where pxRevCol is greater than or equal to DEFAULT_PX_REV_COL
        defaultWineStockShouldBeFound("pxRevCol.greaterThanOrEqual=" + DEFAULT_PX_REV_COL);

        // Get all the wineStockList where pxRevCol is greater than or equal to UPDATED_PX_REV_COL
        defaultWineStockShouldNotBeFound("pxRevCol.greaterThanOrEqual=" + UPDATED_PX_REV_COL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPxRevColIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where pxRevCol is less than or equal to DEFAULT_PX_REV_COL
        defaultWineStockShouldBeFound("pxRevCol.lessThanOrEqual=" + DEFAULT_PX_REV_COL);

        // Get all the wineStockList where pxRevCol is less than or equal to SMALLER_PX_REV_COL
        defaultWineStockShouldNotBeFound("pxRevCol.lessThanOrEqual=" + SMALLER_PX_REV_COL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPxRevColIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where pxRevCol is less than DEFAULT_PX_REV_COL
        defaultWineStockShouldNotBeFound("pxRevCol.lessThan=" + DEFAULT_PX_REV_COL);

        // Get all the wineStockList where pxRevCol is less than UPDATED_PX_REV_COL
        defaultWineStockShouldBeFound("pxRevCol.lessThan=" + UPDATED_PX_REV_COL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByPxRevColIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where pxRevCol is greater than DEFAULT_PX_REV_COL
        defaultWineStockShouldNotBeFound("pxRevCol.greaterThan=" + DEFAULT_PX_REV_COL);

        // Get all the wineStockList where pxRevCol is greater than SMALLER_PX_REV_COL
        defaultWineStockShouldBeFound("pxRevCol.greaterThan=" + SMALLER_PX_REV_COL);
    }


    @Test
    @Transactional
    public void getAllWineStocksByLastPurchasePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchasePrice equals to DEFAULT_LAST_PURCHASE_PRICE
        defaultWineStockShouldBeFound("lastPurchasePrice.equals=" + DEFAULT_LAST_PURCHASE_PRICE);

        // Get all the wineStockList where lastPurchasePrice equals to UPDATED_LAST_PURCHASE_PRICE
        defaultWineStockShouldNotBeFound("lastPurchasePrice.equals=" + UPDATED_LAST_PURCHASE_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchasePriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchasePrice not equals to DEFAULT_LAST_PURCHASE_PRICE
        defaultWineStockShouldNotBeFound("lastPurchasePrice.notEquals=" + DEFAULT_LAST_PURCHASE_PRICE);

        // Get all the wineStockList where lastPurchasePrice not equals to UPDATED_LAST_PURCHASE_PRICE
        defaultWineStockShouldBeFound("lastPurchasePrice.notEquals=" + UPDATED_LAST_PURCHASE_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchasePriceIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchasePrice in DEFAULT_LAST_PURCHASE_PRICE or UPDATED_LAST_PURCHASE_PRICE
        defaultWineStockShouldBeFound("lastPurchasePrice.in=" + DEFAULT_LAST_PURCHASE_PRICE + "," + UPDATED_LAST_PURCHASE_PRICE);

        // Get all the wineStockList where lastPurchasePrice equals to UPDATED_LAST_PURCHASE_PRICE
        defaultWineStockShouldNotBeFound("lastPurchasePrice.in=" + UPDATED_LAST_PURCHASE_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchasePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchasePrice is not null
        defaultWineStockShouldBeFound("lastPurchasePrice.specified=true");

        // Get all the wineStockList where lastPurchasePrice is null
        defaultWineStockShouldNotBeFound("lastPurchasePrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchasePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchasePrice is greater than or equal to DEFAULT_LAST_PURCHASE_PRICE
        defaultWineStockShouldBeFound("lastPurchasePrice.greaterThanOrEqual=" + DEFAULT_LAST_PURCHASE_PRICE);

        // Get all the wineStockList where lastPurchasePrice is greater than or equal to UPDATED_LAST_PURCHASE_PRICE
        defaultWineStockShouldNotBeFound("lastPurchasePrice.greaterThanOrEqual=" + UPDATED_LAST_PURCHASE_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchasePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchasePrice is less than or equal to DEFAULT_LAST_PURCHASE_PRICE
        defaultWineStockShouldBeFound("lastPurchasePrice.lessThanOrEqual=" + DEFAULT_LAST_PURCHASE_PRICE);

        // Get all the wineStockList where lastPurchasePrice is less than or equal to SMALLER_LAST_PURCHASE_PRICE
        defaultWineStockShouldNotBeFound("lastPurchasePrice.lessThanOrEqual=" + SMALLER_LAST_PURCHASE_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchasePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchasePrice is less than DEFAULT_LAST_PURCHASE_PRICE
        defaultWineStockShouldNotBeFound("lastPurchasePrice.lessThan=" + DEFAULT_LAST_PURCHASE_PRICE);

        // Get all the wineStockList where lastPurchasePrice is less than UPDATED_LAST_PURCHASE_PRICE
        defaultWineStockShouldBeFound("lastPurchasePrice.lessThan=" + UPDATED_LAST_PURCHASE_PRICE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchasePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchasePrice is greater than DEFAULT_LAST_PURCHASE_PRICE
        defaultWineStockShouldNotBeFound("lastPurchasePrice.greaterThan=" + DEFAULT_LAST_PURCHASE_PRICE);

        // Get all the wineStockList where lastPurchasePrice is greater than SMALLER_LAST_PURCHASE_PRICE
        defaultWineStockShouldBeFound("lastPurchasePrice.greaterThan=" + SMALLER_LAST_PURCHASE_PRICE);
    }


    @Test
    @Transactional
    public void getAllWineStocksByLastPurchaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchaseDate equals to DEFAULT_LAST_PURCHASE_DATE
        defaultWineStockShouldBeFound("lastPurchaseDate.equals=" + DEFAULT_LAST_PURCHASE_DATE);

        // Get all the wineStockList where lastPurchaseDate equals to UPDATED_LAST_PURCHASE_DATE
        defaultWineStockShouldNotBeFound("lastPurchaseDate.equals=" + UPDATED_LAST_PURCHASE_DATE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchaseDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchaseDate not equals to DEFAULT_LAST_PURCHASE_DATE
        defaultWineStockShouldNotBeFound("lastPurchaseDate.notEquals=" + DEFAULT_LAST_PURCHASE_DATE);

        // Get all the wineStockList where lastPurchaseDate not equals to UPDATED_LAST_PURCHASE_DATE
        defaultWineStockShouldBeFound("lastPurchaseDate.notEquals=" + UPDATED_LAST_PURCHASE_DATE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchaseDate in DEFAULT_LAST_PURCHASE_DATE or UPDATED_LAST_PURCHASE_DATE
        defaultWineStockShouldBeFound("lastPurchaseDate.in=" + DEFAULT_LAST_PURCHASE_DATE + "," + UPDATED_LAST_PURCHASE_DATE);

        // Get all the wineStockList where lastPurchaseDate equals to UPDATED_LAST_PURCHASE_DATE
        defaultWineStockShouldNotBeFound("lastPurchaseDate.in=" + UPDATED_LAST_PURCHASE_DATE);
    }

    @Test
    @Transactional
    public void getAllWineStocksByLastPurchaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where lastPurchaseDate is not null
        defaultWineStockShouldBeFound("lastPurchaseDate.specified=true");

        // Get all the wineStockList where lastPurchaseDate is null
        defaultWineStockShouldNotBeFound("lastPurchaseDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByDateImportIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where dateImport equals to DEFAULT_DATE_IMPORT
        defaultWineStockShouldBeFound("dateImport.equals=" + DEFAULT_DATE_IMPORT);

        // Get all the wineStockList where dateImport equals to UPDATED_DATE_IMPORT
        defaultWineStockShouldNotBeFound("dateImport.equals=" + UPDATED_DATE_IMPORT);
    }

    @Test
    @Transactional
    public void getAllWineStocksByDateImportIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where dateImport not equals to DEFAULT_DATE_IMPORT
        defaultWineStockShouldNotBeFound("dateImport.notEquals=" + DEFAULT_DATE_IMPORT);

        // Get all the wineStockList where dateImport not equals to UPDATED_DATE_IMPORT
        defaultWineStockShouldBeFound("dateImport.notEquals=" + UPDATED_DATE_IMPORT);
    }

    @Test
    @Transactional
    public void getAllWineStocksByDateImportIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where dateImport in DEFAULT_DATE_IMPORT or UPDATED_DATE_IMPORT
        defaultWineStockShouldBeFound("dateImport.in=" + DEFAULT_DATE_IMPORT + "," + UPDATED_DATE_IMPORT);

        // Get all the wineStockList where dateImport equals to UPDATED_DATE_IMPORT
        defaultWineStockShouldNotBeFound("dateImport.in=" + UPDATED_DATE_IMPORT);
    }

    @Test
    @Transactional
    public void getAllWineStocksByDateImportIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where dateImport is not null
        defaultWineStockShouldBeFound("dateImport.specified=true");

        // Get all the wineStockList where dateImport is null
        defaultWineStockShouldNotBeFound("dateImport.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStocksByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultWineStockShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the wineStockList where imageUrl equals to UPDATED_IMAGE_URL
        defaultWineStockShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultWineStockShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the wineStockList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultWineStockShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultWineStockShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the wineStockList where imageUrl equals to UPDATED_IMAGE_URL
        defaultWineStockShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where imageUrl is not null
        defaultWineStockShouldBeFound("imageUrl.specified=true");

        // Get all the wineStockList where imageUrl is null
        defaultWineStockShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStocksByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where imageUrl contains DEFAULT_IMAGE_URL
        defaultWineStockShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the wineStockList where imageUrl contains UPDATED_IMAGE_URL
        defaultWineStockShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllWineStocksByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        // Get all the wineStockList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultWineStockShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the wineStockList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultWineStockShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWineStockShouldBeFound(String filter) throws Exception {
        restWineStockMockMvc.perform(get("/api/wine-stocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].vintage").value(hasItem(DEFAULT_VINTAGE)))
            .andExpect(jsonPath("$.[*].alcoholLevel").value(hasItem(DEFAULT_ALCOHOL_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].printRun").value(hasItem(DEFAULT_PRINT_RUN)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].physical").value(hasItem(DEFAULT_PHYSICAL)))
            .andExpect(jsonPath("$.[*].purchases").value(hasItem(DEFAULT_PURCHASES)))
            .andExpect(jsonPath("$.[*].sales").value(hasItem(DEFAULT_SALES)))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY)))
            .andExpect(jsonPath("$.[*].pxRevCol").value(hasItem(DEFAULT_PX_REV_COL.doubleValue())))
            .andExpect(jsonPath("$.[*].lastPurchasePrice").value(hasItem(DEFAULT_LAST_PURCHASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastPurchaseDate").value(hasItem(DEFAULT_LAST_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateImport").value(hasItem(DEFAULT_DATE_IMPORT.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));

        // Check, that the count call also returns 1
        restWineStockMockMvc.perform(get("/api/wine-stocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWineStockShouldNotBeFound(String filter) throws Exception {
        restWineStockMockMvc.perform(get("/api/wine-stocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWineStockMockMvc.perform(get("/api/wine-stocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWineStock() throws Exception {
        // Get the wineStock
        restWineStockMockMvc.perform(get("/api/wine-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWineStock() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        int databaseSizeBeforeUpdate = wineStockRepository.findAll().size();

        // Update the wineStock
        WineStock updatedWineStock = wineStockRepository.findById(wineStock.getId()).get();
        // Disconnect from session so that the updates on updatedWineStock are not directly saved in db
        em.detach(updatedWineStock);
        updatedWineStock
            .supplier(UPDATED_SUPPLIER)
            .region(UPDATED_REGION)
            .description(UPDATED_DESCRIPTION)
            .vintage(UPDATED_VINTAGE)
            .alcoholLevel(UPDATED_ALCOHOL_LEVEL)
            .printRun(UPDATED_PRINT_RUN)
            .price(UPDATED_PRICE)
            .physical(UPDATED_PHYSICAL)
            .purchases(UPDATED_PURCHASES)
            .sales(UPDATED_SALES)
            .availability(UPDATED_AVAILABILITY)
            .pxRevCol(UPDATED_PX_REV_COL)
            .lastPurchasePrice(UPDATED_LAST_PURCHASE_PRICE)
            .lastPurchaseDate(UPDATED_LAST_PURCHASE_DATE)
            .dateImport(UPDATED_DATE_IMPORT)
            .imageUrl(UPDATED_IMAGE_URL);
        WineStockDTO wineStockDTO = wineStockMapper.toDto(updatedWineStock);

        restWineStockMockMvc.perform(put("/api/wine-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStockDTO)))
            .andExpect(status().isOk());

        // Validate the WineStock in the database
        List<WineStock> wineStockList = wineStockRepository.findAll();
        assertThat(wineStockList).hasSize(databaseSizeBeforeUpdate);
        WineStock testWineStock = wineStockList.get(wineStockList.size() - 1);
        assertThat(testWineStock.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testWineStock.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testWineStock.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWineStock.getVintage()).isEqualTo(UPDATED_VINTAGE);
        assertThat(testWineStock.getAlcoholLevel()).isEqualTo(UPDATED_ALCOHOL_LEVEL);
        assertThat(testWineStock.getPrintRun()).isEqualTo(UPDATED_PRINT_RUN);
        assertThat(testWineStock.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testWineStock.getPhysical()).isEqualTo(UPDATED_PHYSICAL);
        assertThat(testWineStock.getPurchases()).isEqualTo(UPDATED_PURCHASES);
        assertThat(testWineStock.getSales()).isEqualTo(UPDATED_SALES);
        assertThat(testWineStock.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
        assertThat(testWineStock.getPxRevCol()).isEqualTo(UPDATED_PX_REV_COL);
        assertThat(testWineStock.getLastPurchasePrice()).isEqualTo(UPDATED_LAST_PURCHASE_PRICE);
        assertThat(testWineStock.getLastPurchaseDate()).isEqualTo(UPDATED_LAST_PURCHASE_DATE);
        assertThat(testWineStock.getDateImport()).isEqualTo(UPDATED_DATE_IMPORT);
        assertThat(testWineStock.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingWineStock() throws Exception {
        int databaseSizeBeforeUpdate = wineStockRepository.findAll().size();

        // Create the WineStock
        WineStockDTO wineStockDTO = wineStockMapper.toDto(wineStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWineStockMockMvc.perform(put("/api/wine-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineStock in the database
        List<WineStock> wineStockList = wineStockRepository.findAll();
        assertThat(wineStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWineStock() throws Exception {
        // Initialize the database
        wineStockRepository.saveAndFlush(wineStock);

        int databaseSizeBeforeDelete = wineStockRepository.findAll().size();

        // Delete the wineStock
        restWineStockMockMvc.perform(delete("/api/wine-stocks/{id}", wineStock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WineStock> wineStockList = wineStockRepository.findAll();
        assertThat(wineStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
