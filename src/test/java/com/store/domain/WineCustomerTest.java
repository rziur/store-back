package com.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineCustomerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineCustomer.class);
        WineCustomer wineCustomer1 = new WineCustomer();
        wineCustomer1.setId(1L);
        WineCustomer wineCustomer2 = new WineCustomer();
        wineCustomer2.setId(wineCustomer1.getId());
        assertThat(wineCustomer1).isEqualTo(wineCustomer2);
        wineCustomer2.setId(2L);
        assertThat(wineCustomer1).isNotEqualTo(wineCustomer2);
        wineCustomer1.setId(null);
        assertThat(wineCustomer1).isNotEqualTo(wineCustomer2);
    }
}
