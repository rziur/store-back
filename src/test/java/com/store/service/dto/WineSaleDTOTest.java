package com.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineSaleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineSaleDTO.class);
        WineSaleDTO wineSaleDTO1 = new WineSaleDTO();
        wineSaleDTO1.setId(1L);
        WineSaleDTO wineSaleDTO2 = new WineSaleDTO();
        assertThat(wineSaleDTO1).isNotEqualTo(wineSaleDTO2);
        wineSaleDTO2.setId(wineSaleDTO1.getId());
        assertThat(wineSaleDTO1).isEqualTo(wineSaleDTO2);
        wineSaleDTO2.setId(2L);
        assertThat(wineSaleDTO1).isNotEqualTo(wineSaleDTO2);
        wineSaleDTO1.setId(null);
        assertThat(wineSaleDTO1).isNotEqualTo(wineSaleDTO2);
    }
}
