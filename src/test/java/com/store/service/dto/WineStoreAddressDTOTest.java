package com.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineStoreAddressDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineStoreAddressDTO.class);
        WineStoreAddressDTO wineStoreAddressDTO1 = new WineStoreAddressDTO();
        wineStoreAddressDTO1.setId(1L);
        WineStoreAddressDTO wineStoreAddressDTO2 = new WineStoreAddressDTO();
        assertThat(wineStoreAddressDTO1).isNotEqualTo(wineStoreAddressDTO2);
        wineStoreAddressDTO2.setId(wineStoreAddressDTO1.getId());
        assertThat(wineStoreAddressDTO1).isEqualTo(wineStoreAddressDTO2);
        wineStoreAddressDTO2.setId(2L);
        assertThat(wineStoreAddressDTO1).isNotEqualTo(wineStoreAddressDTO2);
        wineStoreAddressDTO1.setId(null);
        assertThat(wineStoreAddressDTO1).isNotEqualTo(wineStoreAddressDTO2);
    }
}
