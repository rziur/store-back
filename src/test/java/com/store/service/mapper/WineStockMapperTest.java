package com.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WineStockMapperTest {

    private WineStockMapper wineStockMapper;

    @BeforeEach
    public void setUp() {
        wineStockMapper = new WineStockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wineStockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wineStockMapper.fromId(null)).isNull();
    }
}
