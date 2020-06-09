package no.nav.fnrgen;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class FnrTest {

    private final Fnr f = new Fnr(LocalDate.of(1999, 12, 31), 567);

    @Test
    public void shouldBeAbleToCreateControlNumbers() {
        assertThat(f.getFirstControl()).isEqualTo(1);
        assertThat(f.getSecondControl()).isEqualTo(5);
        assertThat(f.toString()).isEqualTo("31129956715");
    }

    @Test
    public void shouldBeAbleToRecreateFromString() {

        assertThat(f.getFirstControl()).isEqualTo(1);
        assertThat(f.getSecondControl()).isEqualTo(5);
        assertThat(f.toString()).isEqualTo("31129956715");
        Fnr n = Fnr.fromString(f.toString());
        assertThat(n.toString()).isEqualTo(f.toString());
    }

    @Test
    public void ignoresControlNumbersInInputAndRecreatesthem() {
        Fnr f = Fnr.fromString("31129956779");
        assertThat(f.getFirstControl()).isEqualTo(1);
        assertThat(f.getSecondControl()).isEqualTo(5);
    }

    @Test
    public void generatesIndividualNumberFromAge() {
        var birth = LocalDate.of(1990, 2, 1);
        Fnr oldOne = new Fnr(birth);
        assertThat(oldOne.getIndividualNumber()).isLessThan(500);
        var youngDate = LocalDate.of(2001, 2, 1);
        Fnr youngOne = new Fnr(youngDate);
        assertThat(youngOne.getIndividualNumber()).isGreaterThanOrEqualTo(500);
    }

    @Test
    public void lastDigitOfIndividualNumberRevealsGender() {
        assertThat(f.isFemale()).isFalse();
        var female = Fnr.fromString("31129936211");
        assertThat(female.isFemale()).isTrue();
    }

}
