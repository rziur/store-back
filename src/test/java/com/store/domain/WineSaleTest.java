package com.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.store.web.rest.TestUtil;

public class WineSaleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WineSale.class);
        WineSale wineSale1 = new WineSale();
        wineSale1.setId(1L);
        WineSale wineSale2 = new WineSale();
        wineSale2.setId(wineSale1.getId());
        assertThat(wineSale1).isEqualTo(wineSale2);
        wineSale2.setId(2L);
        assertThat(wineSale1).isNotEqualTo(wineSale2);
        wineSale1.setId(null);
        assertThat(wineSale1).isNotEqualTo(wineSale2);
    }
}
