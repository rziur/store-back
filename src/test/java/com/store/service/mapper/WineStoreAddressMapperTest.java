package com.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WineStoreAddressMapperTest {

    private WineStoreAddressMapper wineStoreAddressMapper;

    @BeforeEach
    public void setUp() {
        wineStoreAddressMapper = new WineStoreAddressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wineStoreAddressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wineStoreAddressMapper.fromId(null)).isNull();
    }
}
