package com.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineStock.class);
        WineStock wineStock1 = new WineStock();
        wineStock1.setId(1L);
        WineStock wineStock2 = new WineStock();
        wineStock2.setId(wineStock1.getId());
        assertThat(wineStock1).isEqualTo(wineStock2);
        wineStock2.setId(2L);
        assertThat(wineStock1).isNotEqualTo(wineStock2);
        wineStock1.setId(null);
        assertThat(wineStock1).isNotEqualTo(wineStock2);
    }
}
