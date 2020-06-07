package com.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WineOfferMapperTest {

    private WineOfferMapper wineOfferMapper;

    @BeforeEach
    public void setUp() {
        wineOfferMapper = new WineOfferMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wineOfferMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wineOfferMapper.fromId(null)).isNull();
    }
}
