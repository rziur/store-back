package com.store.web.rest;

import com.store.StoreApp;
import com.store.domain.WineSale;
import com.store.domain.WineCustomer;
import com.store.domain.WineStock;
import com.store.domain.WineStore;
import com.store.repository.WineSaleRepository;
import com.store.service.WineSaleService;
import com.store.service.dto.WineSaleDTO;
import com.store.service.mapper.WineSaleMapper;
import com.store.service.dto.WineSaleCriteria;
import com.store.service.WineSaleQueryService;

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
 * Integration tests for the {@link WineSaleResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WineSaleResourceIT {

    private static final String DEFAULT_SHIPPING_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHIPPING_AMOUNT = 1;
    private static final Integer UPDATED_SHIPPING_AMOUNT = 2;
    private static final Integer SMALLER_SHIPPING_AMOUNT = 1 - 1;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;
    private static final Float SMALLER_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_TOTAL = 1F;
    private static final Float UPDATED_TOTAL = 2F;
    private static final Float SMALLER_TOTAL = 1F - 1F;

    private static final Integer DEFAULT_STATE = 1;
    private static final Integer UPDATED_STATE = 2;
    private static final Integer SMALLER_STATE = 1 - 1;

    @Autowired
    private WineSaleRepository wineSaleRepository;

    @Autowired
    private WineSaleMapper wineSaleMapper;

    @Autowired
    private WineSaleService wineSaleService;

    @Autowired
    private WineSaleQueryService wineSaleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWineSaleMockMvc;

    private WineSale wineSale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineSale createEntity(EntityManager em) {
        WineSale wineSale = new WineSale()
            .shippingDescription(DEFAULT_SHIPPING_DESCRIPTION)
            .shippingAmount(DEFAULT_SHIPPING_AMOUNT)
            .discount(DEFAULT_DISCOUNT)
            .total(DEFAULT_TOTAL)
            .state(DEFAULT_STATE);
        return wineSale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineSale createUpdatedEntity(EntityManager em) {
        WineSale wineSale = new WineSale()
            .shippingDescription(UPDATED_SHIPPING_DESCRIPTION)
            .shippingAmount(UPDATED_SHIPPING_AMOUNT)
            .discount(UPDATED_DISCOUNT)
            .total(UPDATED_TOTAL)
            .state(UPDATED_STATE);
        return wineSale;
    }

    @BeforeEach
    public void initTest() {
        wineSale = createEntity(em);
    }

    @Test
    @Transactional
    public void createWineSale() throws Exception {
        int databaseSizeBeforeCreate = wineSaleRepository.findAll().size();
        // Create the WineSale
        WineSaleDTO wineSaleDTO = wineSaleMapper.toDto(wineSale);
        restWineSaleMockMvc.perform(post("/api/wine-sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineSaleDTO)))
            .andExpect(status().isCreated());

        // Validate the WineSale in the database
        List<WineSale> wineSaleList = wineSaleRepository.findAll();
        assertThat(wineSaleList).hasSize(databaseSizeBeforeCreate + 1);
        WineSale testWineSale = wineSaleList.get(wineSaleList.size() - 1);
        assertThat(testWineSale.getShippingDescription()).isEqualTo(DEFAULT_SHIPPING_DESCRIPTION);
        assertThat(testWineSale.getShippingAmount()).isEqualTo(DEFAULT_SHIPPING_AMOUNT);
        assertThat(testWineSale.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testWineSale.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testWineSale.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createWineSaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wineSaleRepository.findAll().size();

        // Create the WineSale with an existing ID
        wineSale.setId(1L);
        WineSaleDTO wineSaleDTO = wineSaleMapper.toDto(wineSale);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWineSaleMockMvc.perform(post("/api/wine-sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineSaleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineSale in the database
        List<WineSale> wineSaleList = wineSaleRepository.findAll();
        assertThat(wineSaleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWineSales() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList
        restWineSaleMockMvc.perform(get("/api/wine-sales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineSale.getId().intValue())))
            .andExpect(jsonPath("$.[*].shippingDescription").value(hasItem(DEFAULT_SHIPPING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shippingAmount").value(hasItem(DEFAULT_SHIPPING_AMOUNT)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getWineSale() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get the wineSale
        restWineSaleMockMvc.perform(get("/api/wine-sales/{id}", wineSale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wineSale.getId().intValue()))
            .andExpect(jsonPath("$.shippingDescription").value(DEFAULT_SHIPPING_DESCRIPTION))
            .andExpect(jsonPath("$.shippingAmount").value(DEFAULT_SHIPPING_AMOUNT))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }


    @Test
    @Transactional
    public void getWineSalesByIdFiltering() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        Long id = wineSale.getId();

        defaultWineSaleShouldBeFound("id.equals=" + id);
        defaultWineSaleShouldNotBeFound("id.notEquals=" + id);

        defaultWineSaleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWineSaleShouldNotBeFound("id.greaterThan=" + id);

        defaultWineSaleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWineSaleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWineSalesByShippingDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingDescription equals to DEFAULT_SHIPPING_DESCRIPTION
        defaultWineSaleShouldBeFound("shippingDescription.equals=" + DEFAULT_SHIPPING_DESCRIPTION);

        // Get all the wineSaleList where shippingDescription equals to UPDATED_SHIPPING_DESCRIPTION
        defaultWineSaleShouldNotBeFound("shippingDescription.equals=" + UPDATED_SHIPPING_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingDescription not equals to DEFAULT_SHIPPING_DESCRIPTION
        defaultWineSaleShouldNotBeFound("shippingDescription.notEquals=" + DEFAULT_SHIPPING_DESCRIPTION);

        // Get all the wineSaleList where shippingDescription not equals to UPDATED_SHIPPING_DESCRIPTION
        defaultWineSaleShouldBeFound("shippingDescription.notEquals=" + UPDATED_SHIPPING_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingDescription in DEFAULT_SHIPPING_DESCRIPTION or UPDATED_SHIPPING_DESCRIPTION
        defaultWineSaleShouldBeFound("shippingDescription.in=" + DEFAULT_SHIPPING_DESCRIPTION + "," + UPDATED_SHIPPING_DESCRIPTION);

        // Get all the wineSaleList where shippingDescription equals to UPDATED_SHIPPING_DESCRIPTION
        defaultWineSaleShouldNotBeFound("shippingDescription.in=" + UPDATED_SHIPPING_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingDescription is not null
        defaultWineSaleShouldBeFound("shippingDescription.specified=true");

        // Get all the wineSaleList where shippingDescription is null
        defaultWineSaleShouldNotBeFound("shippingDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineSalesByShippingDescriptionContainsSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingDescription contains DEFAULT_SHIPPING_DESCRIPTION
        defaultWineSaleShouldBeFound("shippingDescription.contains=" + DEFAULT_SHIPPING_DESCRIPTION);

        // Get all the wineSaleList where shippingDescription contains UPDATED_SHIPPING_DESCRIPTION
        defaultWineSaleShouldNotBeFound("shippingDescription.contains=" + UPDATED_SHIPPING_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingDescription does not contain DEFAULT_SHIPPING_DESCRIPTION
        defaultWineSaleShouldNotBeFound("shippingDescription.doesNotContain=" + DEFAULT_SHIPPING_DESCRIPTION);

        // Get all the wineSaleList where shippingDescription does not contain UPDATED_SHIPPING_DESCRIPTION
        defaultWineSaleShouldBeFound("shippingDescription.doesNotContain=" + UPDATED_SHIPPING_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllWineSalesByShippingAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingAmount equals to DEFAULT_SHIPPING_AMOUNT
        defaultWineSaleShouldBeFound("shippingAmount.equals=" + DEFAULT_SHIPPING_AMOUNT);

        // Get all the wineSaleList where shippingAmount equals to UPDATED_SHIPPING_AMOUNT
        defaultWineSaleShouldNotBeFound("shippingAmount.equals=" + UPDATED_SHIPPING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingAmount not equals to DEFAULT_SHIPPING_AMOUNT
        defaultWineSaleShouldNotBeFound("shippingAmount.notEquals=" + DEFAULT_SHIPPING_AMOUNT);

        // Get all the wineSaleList where shippingAmount not equals to UPDATED_SHIPPING_AMOUNT
        defaultWineSaleShouldBeFound("shippingAmount.notEquals=" + UPDATED_SHIPPING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingAmountIsInShouldWork() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingAmount in DEFAULT_SHIPPING_AMOUNT or UPDATED_SHIPPING_AMOUNT
        defaultWineSaleShouldBeFound("shippingAmount.in=" + DEFAULT_SHIPPING_AMOUNT + "," + UPDATED_SHIPPING_AMOUNT);

        // Get all the wineSaleList where shippingAmount equals to UPDATED_SHIPPING_AMOUNT
        defaultWineSaleShouldNotBeFound("shippingAmount.in=" + UPDATED_SHIPPING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingAmount is not null
        defaultWineSaleShouldBeFound("shippingAmount.specified=true");

        // Get all the wineSaleList where shippingAmount is null
        defaultWineSaleShouldNotBeFound("shippingAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingAmount is greater than or equal to DEFAULT_SHIPPING_AMOUNT
        defaultWineSaleShouldBeFound("shippingAmount.greaterThanOrEqual=" + DEFAULT_SHIPPING_AMOUNT);

        // Get all the wineSaleList where shippingAmount is greater than or equal to UPDATED_SHIPPING_AMOUNT
        defaultWineSaleShouldNotBeFound("shippingAmount.greaterThanOrEqual=" + UPDATED_SHIPPING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingAmount is less than or equal to DEFAULT_SHIPPING_AMOUNT
        defaultWineSaleShouldBeFound("shippingAmount.lessThanOrEqual=" + DEFAULT_SHIPPING_AMOUNT);

        // Get all the wineSaleList where shippingAmount is less than or equal to SMALLER_SHIPPING_AMOUNT
        defaultWineSaleShouldNotBeFound("shippingAmount.lessThanOrEqual=" + SMALLER_SHIPPING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingAmount is less than DEFAULT_SHIPPING_AMOUNT
        defaultWineSaleShouldNotBeFound("shippingAmount.lessThan=" + DEFAULT_SHIPPING_AMOUNT);

        // Get all the wineSaleList where shippingAmount is less than UPDATED_SHIPPING_AMOUNT
        defaultWineSaleShouldBeFound("shippingAmount.lessThan=" + UPDATED_SHIPPING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByShippingAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where shippingAmount is greater than DEFAULT_SHIPPING_AMOUNT
        defaultWineSaleShouldNotBeFound("shippingAmount.greaterThan=" + DEFAULT_SHIPPING_AMOUNT);

        // Get all the wineSaleList where shippingAmount is greater than SMALLER_SHIPPING_AMOUNT
        defaultWineSaleShouldBeFound("shippingAmount.greaterThan=" + SMALLER_SHIPPING_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllWineSalesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where discount equals to DEFAULT_DISCOUNT
        defaultWineSaleShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the wineSaleList where discount equals to UPDATED_DISCOUNT
        defaultWineSaleShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where discount not equals to DEFAULT_DISCOUNT
        defaultWineSaleShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the wineSaleList where discount not equals to UPDATED_DISCOUNT
        defaultWineSaleShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultWineSaleShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the wineSaleList where discount equals to UPDATED_DISCOUNT
        defaultWineSaleShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where discount is not null
        defaultWineSaleShouldBeFound("discount.specified=true");

        // Get all the wineSaleList where discount is null
        defaultWineSaleShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineSalesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where discount is greater than or equal to DEFAULT_DISCOUNT
        defaultWineSaleShouldBeFound("discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the wineSaleList where discount is greater than or equal to UPDATED_DISCOUNT
        defaultWineSaleShouldNotBeFound("discount.greaterThanOrEqual=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where discount is less than or equal to DEFAULT_DISCOUNT
        defaultWineSaleShouldBeFound("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the wineSaleList where discount is less than or equal to SMALLER_DISCOUNT
        defaultWineSaleShouldNotBeFound("discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where discount is less than DEFAULT_DISCOUNT
        defaultWineSaleShouldNotBeFound("discount.lessThan=" + DEFAULT_DISCOUNT);

        // Get all the wineSaleList where discount is less than UPDATED_DISCOUNT
        defaultWineSaleShouldBeFound("discount.lessThan=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllWineSalesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where discount is greater than DEFAULT_DISCOUNT
        defaultWineSaleShouldNotBeFound("discount.greaterThan=" + DEFAULT_DISCOUNT);

        // Get all the wineSaleList where discount is greater than SMALLER_DISCOUNT
        defaultWineSaleShouldBeFound("discount.greaterThan=" + SMALLER_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllWineSalesByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where total equals to DEFAULT_TOTAL
        defaultWineSaleShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the wineSaleList where total equals to UPDATED_TOTAL
        defaultWineSaleShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllWineSalesByTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where total not equals to DEFAULT_TOTAL
        defaultWineSaleShouldNotBeFound("total.notEquals=" + DEFAULT_TOTAL);

        // Get all the wineSaleList where total not equals to UPDATED_TOTAL
        defaultWineSaleShouldBeFound("total.notEquals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllWineSalesByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultWineSaleShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the wineSaleList where total equals to UPDATED_TOTAL
        defaultWineSaleShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllWineSalesByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where total is not null
        defaultWineSaleShouldBeFound("total.specified=true");

        // Get all the wineSaleList where total is null
        defaultWineSaleShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineSalesByTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where total is greater than or equal to DEFAULT_TOTAL
        defaultWineSaleShouldBeFound("total.greaterThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the wineSaleList where total is greater than or equal to UPDATED_TOTAL
        defaultWineSaleShouldNotBeFound("total.greaterThanOrEqual=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllWineSalesByTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where total is less than or equal to DEFAULT_TOTAL
        defaultWineSaleShouldBeFound("total.lessThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the wineSaleList where total is less than or equal to SMALLER_TOTAL
        defaultWineSaleShouldNotBeFound("total.lessThanOrEqual=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllWineSalesByTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where total is less than DEFAULT_TOTAL
        defaultWineSaleShouldNotBeFound("total.lessThan=" + DEFAULT_TOTAL);

        // Get all the wineSaleList where total is less than UPDATED_TOTAL
        defaultWineSaleShouldBeFound("total.lessThan=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllWineSalesByTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where total is greater than DEFAULT_TOTAL
        defaultWineSaleShouldNotBeFound("total.greaterThan=" + DEFAULT_TOTAL);

        // Get all the wineSaleList where total is greater than SMALLER_TOTAL
        defaultWineSaleShouldBeFound("total.greaterThan=" + SMALLER_TOTAL);
    }


    @Test
    @Transactional
    public void getAllWineSalesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where state equals to DEFAULT_STATE
        defaultWineSaleShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the wineSaleList where state equals to UPDATED_STATE
        defaultWineSaleShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllWineSalesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where state not equals to DEFAULT_STATE
        defaultWineSaleShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the wineSaleList where state not equals to UPDATED_STATE
        defaultWineSaleShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllWineSalesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where state in DEFAULT_STATE or UPDATED_STATE
        defaultWineSaleShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the wineSaleList where state equals to UPDATED_STATE
        defaultWineSaleShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllWineSalesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where state is not null
        defaultWineSaleShouldBeFound("state.specified=true");

        // Get all the wineSaleList where state is null
        defaultWineSaleShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineSalesByStateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where state is greater than or equal to DEFAULT_STATE
        defaultWineSaleShouldBeFound("state.greaterThanOrEqual=" + DEFAULT_STATE);

        // Get all the wineSaleList where state is greater than or equal to UPDATED_STATE
        defaultWineSaleShouldNotBeFound("state.greaterThanOrEqual=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllWineSalesByStateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where state is less than or equal to DEFAULT_STATE
        defaultWineSaleShouldBeFound("state.lessThanOrEqual=" + DEFAULT_STATE);

        // Get all the wineSaleList where state is less than or equal to SMALLER_STATE
        defaultWineSaleShouldNotBeFound("state.lessThanOrEqual=" + SMALLER_STATE);
    }

    @Test
    @Transactional
    public void getAllWineSalesByStateIsLessThanSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where state is less than DEFAULT_STATE
        defaultWineSaleShouldNotBeFound("state.lessThan=" + DEFAULT_STATE);

        // Get all the wineSaleList where state is less than UPDATED_STATE
        defaultWineSaleShouldBeFound("state.lessThan=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllWineSalesByStateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        // Get all the wineSaleList where state is greater than DEFAULT_STATE
        defaultWineSaleShouldNotBeFound("state.greaterThan=" + DEFAULT_STATE);

        // Get all the wineSaleList where state is greater than SMALLER_STATE
        defaultWineSaleShouldBeFound("state.greaterThan=" + SMALLER_STATE);
    }


    @Test
    @Transactional
    public void getAllWineSalesByWineCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);
        WineCustomer wineCustomer = WineCustomerResourceIT.createEntity(em);
        em.persist(wineCustomer);
        em.flush();
        wineSale.setWineCustomer(wineCustomer);
        wineSaleRepository.saveAndFlush(wineSale);
        Long wineCustomerId = wineCustomer.getId();

        // Get all the wineSaleList where wineCustomer equals to wineCustomerId
        defaultWineSaleShouldBeFound("wineCustomerId.equals=" + wineCustomerId);

        // Get all the wineSaleList where wineCustomer equals to wineCustomerId + 1
        defaultWineSaleShouldNotBeFound("wineCustomerId.equals=" + (wineCustomerId + 1));
    }


    @Test
    @Transactional
    public void getAllWineSalesByWineStockIsEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);
        WineStock wineStock = WineStockResourceIT.createEntity(em);
        em.persist(wineStock);
        em.flush();
        wineSale.setWineStock(wineStock);
        wineSaleRepository.saveAndFlush(wineSale);
        Long wineStockId = wineStock.getId();

        // Get all the wineSaleList where wineStock equals to wineStockId
        defaultWineSaleShouldBeFound("wineStockId.equals=" + wineStockId);

        // Get all the wineSaleList where wineStock equals to wineStockId + 1
        defaultWineSaleShouldNotBeFound("wineStockId.equals=" + (wineStockId + 1));
    }


    @Test
    @Transactional
    public void getAllWineSalesByWineStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);
        WineStore wineStore = WineStoreResourceIT.createEntity(em);
        em.persist(wineStore);
        em.flush();
        wineSale.setWineStore(wineStore);
        wineSaleRepository.saveAndFlush(wineSale);
        Long wineStoreId = wineStore.getId();

        // Get all the wineSaleList where wineStore equals to wineStoreId
        defaultWineSaleShouldBeFound("wineStoreId.equals=" + wineStoreId);

        // Get all the wineSaleList where wineStore equals to wineStoreId + 1
        defaultWineSaleShouldNotBeFound("wineStoreId.equals=" + (wineStoreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWineSaleShouldBeFound(String filter) throws Exception {
        restWineSaleMockMvc.perform(get("/api/wine-sales?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineSale.getId().intValue())))
            .andExpect(jsonPath("$.[*].shippingDescription").value(hasItem(DEFAULT_SHIPPING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shippingAmount").value(hasItem(DEFAULT_SHIPPING_AMOUNT)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));

        // Check, that the count call also returns 1
        restWineSaleMockMvc.perform(get("/api/wine-sales/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWineSaleShouldNotBeFound(String filter) throws Exception {
        restWineSaleMockMvc.perform(get("/api/wine-sales?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWineSaleMockMvc.perform(get("/api/wine-sales/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWineSale() throws Exception {
        // Get the wineSale
        restWineSaleMockMvc.perform(get("/api/wine-sales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWineSale() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        int databaseSizeBeforeUpdate = wineSaleRepository.findAll().size();

        // Update the wineSale
        WineSale updatedWineSale = wineSaleRepository.findById(wineSale.getId()).get();
        // Disconnect from session so that the updates on updatedWineSale are not directly saved in db
        em.detach(updatedWineSale);
        updatedWineSale
            .shippingDescription(UPDATED_SHIPPING_DESCRIPTION)
            .shippingAmount(UPDATED_SHIPPING_AMOUNT)
            .discount(UPDATED_DISCOUNT)
            .total(UPDATED_TOTAL)
            .state(UPDATED_STATE);
        WineSaleDTO wineSaleDTO = wineSaleMapper.toDto(updatedWineSale);

        restWineSaleMockMvc.perform(put("/api/wine-sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineSaleDTO)))
            .andExpect(status().isOk());

        // Validate the WineSale in the database
        List<WineSale> wineSaleList = wineSaleRepository.findAll();
        assertThat(wineSaleList).hasSize(databaseSizeBeforeUpdate);
        WineSale testWineSale = wineSaleList.get(wineSaleList.size() - 1);
        assertThat(testWineSale.getShippingDescription()).isEqualTo(UPDATED_SHIPPING_DESCRIPTION);
        assertThat(testWineSale.getShippingAmount()).isEqualTo(UPDATED_SHIPPING_AMOUNT);
        assertThat(testWineSale.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testWineSale.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testWineSale.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingWineSale() throws Exception {
        int databaseSizeBeforeUpdate = wineSaleRepository.findAll().size();

        // Create the WineSale
        WineSaleDTO wineSaleDTO = wineSaleMapper.toDto(wineSale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWineSaleMockMvc.perform(put("/api/wine-sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineSaleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineSale in the database
        List<WineSale> wineSaleList = wineSaleRepository.findAll();
        assertThat(wineSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWineSale() throws Exception {
        // Initialize the database
        wineSaleRepository.saveAndFlush(wineSale);

        int databaseSizeBeforeDelete = wineSaleRepository.findAll().size();

        // Delete the wineSale
        restWineSaleMockMvc.perform(delete("/api/wine-sales/{id}", wineSale.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WineSale> wineSaleList = wineSaleRepository.findAll();
        assertThat(wineSaleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
