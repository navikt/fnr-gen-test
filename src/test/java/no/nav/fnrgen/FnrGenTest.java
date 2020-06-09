package no.nav.fnrgen;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class FnrGenTest {

    @Test
    public void shouldBeAbleToSetAFixedRandomSeed() {
        var seed = 12345L;
        List<String> fnr = FnrGen.generate(seed).limit(2).collect(Collectors.toList());
        assertThat(fnr).hasSize(2);
        assertThat(fnr.get(0)).isEqualTo("13055530224");
        assertThat(fnr.get(1)).isEqualTo("18106144237");
    }

    @Test
    public void twoSeparateInvocationsShouldGiveDifferentFnrs() {
        String fnr_1 = FnrGen.singleFnr();
        String fnr_2 = FnrGen.singleFnr();
        assertThat(fnr_1).isNotEqualTo(fnr_2);
    }

    @Test
    public void shouldSupportGeneratingFromSeedForSingleFnrAsWell() {
        var seed = 12345L;
        String fnr = FnrGen.singleFnr(seed);
        assertThat(fnr).isEqualTo("13055530224");
    }
}
