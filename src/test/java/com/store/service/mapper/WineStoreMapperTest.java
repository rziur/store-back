package com.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WineStoreMapperTest {

    private WineStoreMapper wineStoreMapper;

    @BeforeEach
    public void setUp() {
        wineStoreMapper = new WineStoreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wineStoreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wineStoreMapper.fromId(null)).isNull();
    }
}
