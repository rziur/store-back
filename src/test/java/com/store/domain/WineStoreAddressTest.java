package com.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineStoreAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineStoreAddress.class);
        WineStoreAddress wineStoreAddress1 = new WineStoreAddress();
        wineStoreAddress1.setId(1L);
        WineStoreAddress wineStoreAddress2 = new WineStoreAddress();
        wineStoreAddress2.setId(wineStoreAddress1.getId());
        assertThat(wineStoreAddress1).isEqualTo(wineStoreAddress2);
        wineStoreAddress2.setId(2L);
        assertThat(wineStoreAddress1).isNotEqualTo(wineStoreAddress2);
        wineStoreAddress1.setId(null);
        assertThat(wineStoreAddress1).isNotEqualTo(wineStoreAddress2);
    }
}
