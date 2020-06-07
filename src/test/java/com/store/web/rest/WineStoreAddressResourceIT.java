package com.store.web.rest;

import com.store.StoreApp;
import com.store.domain.WineStoreAddress;
import com.store.domain.WineStore;
import com.store.repository.WineStoreAddressRepository;
import com.store.service.WineStoreAddressService;
import com.store.service.dto.WineStoreAddressDTO;
import com.store.service.mapper.WineStoreAddressMapper;
import com.store.service.dto.WineStoreAddressCriteria;
import com.store.service.WineStoreAddressQueryService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WineStoreAddressResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WineStoreAddressResourceIT {

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LATITUDE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LONGITUDE = new BigDecimal(1 - 1);

    @Autowired
    private WineStoreAddressRepository wineStoreAddressRepository;

    @Autowired
    private WineStoreAddressMapper wineStoreAddressMapper;

    @Autowired
    private WineStoreAddressService wineStoreAddressService;

    @Autowired
    private WineStoreAddressQueryService wineStoreAddressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWineStoreAddressMockMvc;

    private WineStoreAddress wineStoreAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineStoreAddress createEntity(EntityManager em) {
        WineStoreAddress wineStoreAddress = new WineStoreAddress()
            .street(DEFAULT_STREET)
            .postcode(DEFAULT_POSTCODE)
            .city(DEFAULT_CITY)
            .region(DEFAULT_REGION)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return wineStoreAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WineStoreAddress createUpdatedEntity(EntityManager em) {
        WineStoreAddress wineStoreAddress = new WineStoreAddress()
            .street(UPDATED_STREET)
            .postcode(UPDATED_POSTCODE)
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return wineStoreAddress;
    }

    @BeforeEach
    public void initTest() {
        wineStoreAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createWineStoreAddress() throws Exception {
        int databaseSizeBeforeCreate = wineStoreAddressRepository.findAll().size();
        // Create the WineStoreAddress
        WineStoreAddressDTO wineStoreAddressDTO = wineStoreAddressMapper.toDto(wineStoreAddress);
        restWineStoreAddressMockMvc.perform(post("/api/wine-store-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStoreAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the WineStoreAddress in the database
        List<WineStoreAddress> wineStoreAddressList = wineStoreAddressRepository.findAll();
        assertThat(wineStoreAddressList).hasSize(databaseSizeBeforeCreate + 1);
        WineStoreAddress testWineStoreAddress = wineStoreAddressList.get(wineStoreAddressList.size() - 1);
        assertThat(testWineStoreAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testWineStoreAddress.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testWineStoreAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWineStoreAddress.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testWineStoreAddress.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testWineStoreAddress.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createWineStoreAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wineStoreAddressRepository.findAll().size();

        // Create the WineStoreAddress with an existing ID
        wineStoreAddress.setId(1L);
        WineStoreAddressDTO wineStoreAddressDTO = wineStoreAddressMapper.toDto(wineStoreAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWineStoreAddressMockMvc.perform(post("/api/wine-store-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStoreAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineStoreAddress in the database
        List<WineStoreAddress> wineStoreAddressList = wineStoreAddressRepository.findAll();
        assertThat(wineStoreAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWineStoreAddresses() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList
        restWineStoreAddressMockMvc.perform(get("/api/wine-store-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineStoreAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));
    }
    
    @Test
    @Transactional
    public void getWineStoreAddress() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get the wineStoreAddress
        restWineStoreAddressMockMvc.perform(get("/api/wine-store-addresses/{id}", wineStoreAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wineStoreAddress.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()));
    }


    @Test
    @Transactional
    public void getWineStoreAddressesByIdFiltering() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        Long id = wineStoreAddress.getId();

        defaultWineStoreAddressShouldBeFound("id.equals=" + id);
        defaultWineStoreAddressShouldNotBeFound("id.notEquals=" + id);

        defaultWineStoreAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWineStoreAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultWineStoreAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWineStoreAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWineStoreAddressesByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where street equals to DEFAULT_STREET
        defaultWineStoreAddressShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the wineStoreAddressList where street equals to UPDATED_STREET
        defaultWineStoreAddressShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where street not equals to DEFAULT_STREET
        defaultWineStoreAddressShouldNotBeFound("street.notEquals=" + DEFAULT_STREET);

        // Get all the wineStoreAddressList where street not equals to UPDATED_STREET
        defaultWineStoreAddressShouldBeFound("street.notEquals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where street in DEFAULT_STREET or UPDATED_STREET
        defaultWineStoreAddressShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the wineStoreAddressList where street equals to UPDATED_STREET
        defaultWineStoreAddressShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where street is not null
        defaultWineStoreAddressShouldBeFound("street.specified=true");

        // Get all the wineStoreAddressList where street is null
        defaultWineStoreAddressShouldNotBeFound("street.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStoreAddressesByStreetContainsSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where street contains DEFAULT_STREET
        defaultWineStoreAddressShouldBeFound("street.contains=" + DEFAULT_STREET);

        // Get all the wineStoreAddressList where street contains UPDATED_STREET
        defaultWineStoreAddressShouldNotBeFound("street.contains=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByStreetNotContainsSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where street does not contain DEFAULT_STREET
        defaultWineStoreAddressShouldNotBeFound("street.doesNotContain=" + DEFAULT_STREET);

        // Get all the wineStoreAddressList where street does not contain UPDATED_STREET
        defaultWineStoreAddressShouldBeFound("street.doesNotContain=" + UPDATED_STREET);
    }


    @Test
    @Transactional
    public void getAllWineStoreAddressesByPostcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where postcode equals to DEFAULT_POSTCODE
        defaultWineStoreAddressShouldBeFound("postcode.equals=" + DEFAULT_POSTCODE);

        // Get all the wineStoreAddressList where postcode equals to UPDATED_POSTCODE
        defaultWineStoreAddressShouldNotBeFound("postcode.equals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByPostcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where postcode not equals to DEFAULT_POSTCODE
        defaultWineStoreAddressShouldNotBeFound("postcode.notEquals=" + DEFAULT_POSTCODE);

        // Get all the wineStoreAddressList where postcode not equals to UPDATED_POSTCODE
        defaultWineStoreAddressShouldBeFound("postcode.notEquals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByPostcodeIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where postcode in DEFAULT_POSTCODE or UPDATED_POSTCODE
        defaultWineStoreAddressShouldBeFound("postcode.in=" + DEFAULT_POSTCODE + "," + UPDATED_POSTCODE);

        // Get all the wineStoreAddressList where postcode equals to UPDATED_POSTCODE
        defaultWineStoreAddressShouldNotBeFound("postcode.in=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByPostcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where postcode is not null
        defaultWineStoreAddressShouldBeFound("postcode.specified=true");

        // Get all the wineStoreAddressList where postcode is null
        defaultWineStoreAddressShouldNotBeFound("postcode.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStoreAddressesByPostcodeContainsSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where postcode contains DEFAULT_POSTCODE
        defaultWineStoreAddressShouldBeFound("postcode.contains=" + DEFAULT_POSTCODE);

        // Get all the wineStoreAddressList where postcode contains UPDATED_POSTCODE
        defaultWineStoreAddressShouldNotBeFound("postcode.contains=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByPostcodeNotContainsSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where postcode does not contain DEFAULT_POSTCODE
        defaultWineStoreAddressShouldNotBeFound("postcode.doesNotContain=" + DEFAULT_POSTCODE);

        // Get all the wineStoreAddressList where postcode does not contain UPDATED_POSTCODE
        defaultWineStoreAddressShouldBeFound("postcode.doesNotContain=" + UPDATED_POSTCODE);
    }


    @Test
    @Transactional
    public void getAllWineStoreAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where city equals to DEFAULT_CITY
        defaultWineStoreAddressShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the wineStoreAddressList where city equals to UPDATED_CITY
        defaultWineStoreAddressShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where city not equals to DEFAULT_CITY
        defaultWineStoreAddressShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the wineStoreAddressList where city not equals to UPDATED_CITY
        defaultWineStoreAddressShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where city in DEFAULT_CITY or UPDATED_CITY
        defaultWineStoreAddressShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the wineStoreAddressList where city equals to UPDATED_CITY
        defaultWineStoreAddressShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where city is not null
        defaultWineStoreAddressShouldBeFound("city.specified=true");

        // Get all the wineStoreAddressList where city is null
        defaultWineStoreAddressShouldNotBeFound("city.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStoreAddressesByCityContainsSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where city contains DEFAULT_CITY
        defaultWineStoreAddressShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the wineStoreAddressList where city contains UPDATED_CITY
        defaultWineStoreAddressShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where city does not contain DEFAULT_CITY
        defaultWineStoreAddressShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the wineStoreAddressList where city does not contain UPDATED_CITY
        defaultWineStoreAddressShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }


    @Test
    @Transactional
    public void getAllWineStoreAddressesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where region equals to DEFAULT_REGION
        defaultWineStoreAddressShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the wineStoreAddressList where region equals to UPDATED_REGION
        defaultWineStoreAddressShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByRegionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where region not equals to DEFAULT_REGION
        defaultWineStoreAddressShouldNotBeFound("region.notEquals=" + DEFAULT_REGION);

        // Get all the wineStoreAddressList where region not equals to UPDATED_REGION
        defaultWineStoreAddressShouldBeFound("region.notEquals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where region in DEFAULT_REGION or UPDATED_REGION
        defaultWineStoreAddressShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the wineStoreAddressList where region equals to UPDATED_REGION
        defaultWineStoreAddressShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where region is not null
        defaultWineStoreAddressShouldBeFound("region.specified=true");

        // Get all the wineStoreAddressList where region is null
        defaultWineStoreAddressShouldNotBeFound("region.specified=false");
    }
                @Test
    @Transactional
    public void getAllWineStoreAddressesByRegionContainsSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where region contains DEFAULT_REGION
        defaultWineStoreAddressShouldBeFound("region.contains=" + DEFAULT_REGION);

        // Get all the wineStoreAddressList where region contains UPDATED_REGION
        defaultWineStoreAddressShouldNotBeFound("region.contains=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByRegionNotContainsSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where region does not contain DEFAULT_REGION
        defaultWineStoreAddressShouldNotBeFound("region.doesNotContain=" + DEFAULT_REGION);

        // Get all the wineStoreAddressList where region does not contain UPDATED_REGION
        defaultWineStoreAddressShouldBeFound("region.doesNotContain=" + UPDATED_REGION);
    }


    @Test
    @Transactional
    public void getAllWineStoreAddressesByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where latitude equals to DEFAULT_LATITUDE
        defaultWineStoreAddressShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the wineStoreAddressList where latitude equals to UPDATED_LATITUDE
        defaultWineStoreAddressShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where latitude not equals to DEFAULT_LATITUDE
        defaultWineStoreAddressShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the wineStoreAddressList where latitude not equals to UPDATED_LATITUDE
        defaultWineStoreAddressShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultWineStoreAddressShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the wineStoreAddressList where latitude equals to UPDATED_LATITUDE
        defaultWineStoreAddressShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where latitude is not null
        defaultWineStoreAddressShouldBeFound("latitude.specified=true");

        // Get all the wineStoreAddressList where latitude is null
        defaultWineStoreAddressShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultWineStoreAddressShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the wineStoreAddressList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultWineStoreAddressShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultWineStoreAddressShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the wineStoreAddressList where latitude is less than or equal to SMALLER_LATITUDE
        defaultWineStoreAddressShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where latitude is less than DEFAULT_LATITUDE
        defaultWineStoreAddressShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the wineStoreAddressList where latitude is less than UPDATED_LATITUDE
        defaultWineStoreAddressShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where latitude is greater than DEFAULT_LATITUDE
        defaultWineStoreAddressShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the wineStoreAddressList where latitude is greater than SMALLER_LATITUDE
        defaultWineStoreAddressShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllWineStoreAddressesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where longitude equals to DEFAULT_LONGITUDE
        defaultWineStoreAddressShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the wineStoreAddressList where longitude equals to UPDATED_LONGITUDE
        defaultWineStoreAddressShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where longitude not equals to DEFAULT_LONGITUDE
        defaultWineStoreAddressShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the wineStoreAddressList where longitude not equals to UPDATED_LONGITUDE
        defaultWineStoreAddressShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultWineStoreAddressShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the wineStoreAddressList where longitude equals to UPDATED_LONGITUDE
        defaultWineStoreAddressShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where longitude is not null
        defaultWineStoreAddressShouldBeFound("longitude.specified=true");

        // Get all the wineStoreAddressList where longitude is null
        defaultWineStoreAddressShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultWineStoreAddressShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the wineStoreAddressList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultWineStoreAddressShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultWineStoreAddressShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the wineStoreAddressList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultWineStoreAddressShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where longitude is less than DEFAULT_LONGITUDE
        defaultWineStoreAddressShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the wineStoreAddressList where longitude is less than UPDATED_LONGITUDE
        defaultWineStoreAddressShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllWineStoreAddressesByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        // Get all the wineStoreAddressList where longitude is greater than DEFAULT_LONGITUDE
        defaultWineStoreAddressShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the wineStoreAddressList where longitude is greater than SMALLER_LONGITUDE
        defaultWineStoreAddressShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllWineStoreAddressesByWineStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);
        WineStore wineStore = WineStoreResourceIT.createEntity(em);
        em.persist(wineStore);
        em.flush();
        wineStoreAddress.setWineStore(wineStore);
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);
        Long wineStoreId = wineStore.getId();

        // Get all the wineStoreAddressList where wineStore equals to wineStoreId
        defaultWineStoreAddressShouldBeFound("wineStoreId.equals=" + wineStoreId);

        // Get all the wineStoreAddressList where wineStore equals to wineStoreId + 1
        defaultWineStoreAddressShouldNotBeFound("wineStoreId.equals=" + (wineStoreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWineStoreAddressShouldBeFound(String filter) throws Exception {
        restWineStoreAddressMockMvc.perform(get("/api/wine-store-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wineStoreAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));

        // Check, that the count call also returns 1
        restWineStoreAddressMockMvc.perform(get("/api/wine-store-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWineStoreAddressShouldNotBeFound(String filter) throws Exception {
        restWineStoreAddressMockMvc.perform(get("/api/wine-store-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWineStoreAddressMockMvc.perform(get("/api/wine-store-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWineStoreAddress() throws Exception {
        // Get the wineStoreAddress
        restWineStoreAddressMockMvc.perform(get("/api/wine-store-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWineStoreAddress() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        int databaseSizeBeforeUpdate = wineStoreAddressRepository.findAll().size();

        // Update the wineStoreAddress
        WineStoreAddress updatedWineStoreAddress = wineStoreAddressRepository.findById(wineStoreAddress.getId()).get();
        // Disconnect from session so that the updates on updatedWineStoreAddress are not directly saved in db
        em.detach(updatedWineStoreAddress);
        updatedWineStoreAddress
            .street(UPDATED_STREET)
            .postcode(UPDATED_POSTCODE)
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        WineStoreAddressDTO wineStoreAddressDTO = wineStoreAddressMapper.toDto(updatedWineStoreAddress);

        restWineStoreAddressMockMvc.perform(put("/api/wine-store-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStoreAddressDTO)))
            .andExpect(status().isOk());

        // Validate the WineStoreAddress in the database
        List<WineStoreAddress> wineStoreAddressList = wineStoreAddressRepository.findAll();
        assertThat(wineStoreAddressList).hasSize(databaseSizeBeforeUpdate);
        WineStoreAddress testWineStoreAddress = wineStoreAddressList.get(wineStoreAddressList.size() - 1);
        assertThat(testWineStoreAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testWineStoreAddress.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testWineStoreAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWineStoreAddress.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testWineStoreAddress.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testWineStoreAddress.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingWineStoreAddress() throws Exception {
        int databaseSizeBeforeUpdate = wineStoreAddressRepository.findAll().size();

        // Create the WineStoreAddress
        WineStoreAddressDTO wineStoreAddressDTO = wineStoreAddressMapper.toDto(wineStoreAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWineStoreAddressMockMvc.perform(put("/api/wine-store-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wineStoreAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WineStoreAddress in the database
        List<WineStoreAddress> wineStoreAddressList = wineStoreAddressRepository.findAll();
        assertThat(wineStoreAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWineStoreAddress() throws Exception {
        // Initialize the database
        wineStoreAddressRepository.saveAndFlush(wineStoreAddress);

        int databaseSizeBeforeDelete = wineStoreAddressRepository.findAll().size();

        // Delete the wineStoreAddress
        restWineStoreAddressMockMvc.perform(delete("/api/wine-store-addresses/{id}", wineStoreAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WineStoreAddress> wineStoreAddressList = wineStoreAddressRepository.findAll();
        assertThat(wineStoreAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
