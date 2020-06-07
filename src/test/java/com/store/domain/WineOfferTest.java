package com.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineOfferTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineOffer.class);
        WineOffer wineOffer1 = new WineOffer();
        wineOffer1.setId(1L);
        WineOffer wineOffer2 = new WineOffer();
        wineOffer2.setId(wineOffer1.getId());
        assertThat(wineOffer1).isEqualTo(wineOffer2);
        wineOffer2.setId(2L);
        assertThat(wineOffer1).isNotEqualTo(wineOffer2);
        wineOffer1.setId(null);
        assertThat(wineOffer1).isNotEqualTo(wineOffer2);
    }
}
