package com.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineStoreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineStore.class);
        WineStore wineStore1 = new WineStore();
        wineStore1.setId(1L);
        WineStore wineStore2 = new WineStore();
        wineStore2.setId(wineStore1.getId());
        assertThat(wineStore1).isEqualTo(wineStore2);
        wineStore2.setId(2L);
        assertThat(wineStore1).isNotEqualTo(wineStore2);
        wineStore1.setId(null);
        assertThat(wineStore1).isNotEqualTo(wineStore2);
    }
}
