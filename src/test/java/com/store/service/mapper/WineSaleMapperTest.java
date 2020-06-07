package com.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WineSaleMapperTest {

    private WineSaleMapper wineSaleMapper;

    @BeforeEach
    public void setUp() {
        wineSaleMapper = new WineSaleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wineSaleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wineSaleMapper.fromId(null)).isNull();
    }
}
