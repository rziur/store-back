package com.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineCustomerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineCustomerDTO.class);
        WineCustomerDTO wineCustomerDTO1 = new WineCustomerDTO();
        wineCustomerDTO1.setId(1L);
        WineCustomerDTO wineCustomerDTO2 = new WineCustomerDTO();
        assertThat(wineCustomerDTO1).isNotEqualTo(wineCustomerDTO2);
        wineCustomerDTO2.setId(wineCustomerDTO1.getId());
        assertThat(wineCustomerDTO1).isEqualTo(wineCustomerDTO2);
        wineCustomerDTO2.setId(2L);
        assertThat(wineCustomerDTO1).isNotEqualTo(wineCustomerDTO2);
        wineCustomerDTO1.setId(null);
        assertThat(wineCustomerDTO1).isNotEqualTo(wineCustomerDTO2);
    }
}
