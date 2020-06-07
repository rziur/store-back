package com.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineStoreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineStoreDTO.class);
        WineStoreDTO wineStoreDTO1 = new WineStoreDTO();
        wineStoreDTO1.setId(1L);
        WineStoreDTO wineStoreDTO2 = new WineStoreDTO();
        assertThat(wineStoreDTO1).isNotEqualTo(wineStoreDTO2);
        wineStoreDTO2.setId(wineStoreDTO1.getId());
        assertThat(wineStoreDTO1).isEqualTo(wineStoreDTO2);
        wineStoreDTO2.setId(2L);
        assertThat(wineStoreDTO1).isNotEqualTo(wineStoreDTO2);
        wineStoreDTO1.setId(null);
        assertThat(wineStoreDTO1).isNotEqualTo(wineStoreDTO2);
    }
}
