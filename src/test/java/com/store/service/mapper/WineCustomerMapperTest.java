package com.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WineCustomerMapperTest {

    private WineCustomerMapper wineCustomerMapper;

    @BeforeEach
    public void setUp() {
        wineCustomerMapper = new WineCustomerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wineCustomerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wineCustomerMapper.fromId(null)).isNull();
    }
}
