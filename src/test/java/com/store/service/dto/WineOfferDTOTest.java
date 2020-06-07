package com.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineOfferDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineOfferDTO.class);
        WineOfferDTO wineOfferDTO1 = new WineOfferDTO();
        wineOfferDTO1.setId(1L);
        WineOfferDTO wineOfferDTO2 = new WineOfferDTO();
        assertThat(wineOfferDTO1).isNotEqualTo(wineOfferDTO2);
        wineOfferDTO2.setId(wineOfferDTO1.getId());
        assertThat(wineOfferDTO1).isEqualTo(wineOfferDTO2);
        wineOfferDTO2.setId(2L);
        assertThat(wineOfferDTO1).isNotEqualTo(wineOfferDTO2);
        wineOfferDTO1.setId(null);
        assertThat(wineOfferDTO1).isNotEqualTo(wineOfferDTO2);
    }
}
