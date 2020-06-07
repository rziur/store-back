package com.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineStockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineStockDTO.class);
        WineStockDTO wineStockDTO1 = new WineStockDTO();
        wineStockDTO1.setId(1L);
        WineStockDTO wineStockDTO2 = new WineStockDTO();
        assertThat(wineStockDTO1).isNotEqualTo(wineStockDTO2);
        wineStockDTO2.setId(wineStockDTO1.getId());
        assertThat(wineStockDTO1).isEqualTo(wineStockDTO2);
        wineStockDTO2.setId(2L);
        assertThat(wineStockDTO1).isNotEqualTo(wineStockDTO2);
        wineStockDTO1.setId(null);
        assertThat(wineStockDTO1).isNotEqualTo(wineStockDTO2);
    }
}
