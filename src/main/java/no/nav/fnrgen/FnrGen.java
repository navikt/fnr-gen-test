package no.nav.fnrgen;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Random;
import java.util.stream.Stream;

public class FnrGen {
    private static final int START_YEAR = LocalDate.now().minusYears(120).getYear();
    private static final Random SECURE_RANDOM = new SecureRandom();

    /**
     * Generates a stream of valid Fnrs using the classwide SecureRandom instance
     * - http://www.fnrinfo.no/
     * @return a stream of valid fnrs represented as strings
     */

    public static Stream<String> generate() {
        return generate(SECURE_RANDOM).map(Fnr::toString);
    }

    /**
     * Generates a stream of valid fnrs from given seed using java.util.Random
     * @param seed - for instantiating random
     * @return a stream of valid fnrs represented as strings
     */
    public static Stream<String> generate(long seed) {
        Random random = new Random(seed);
        return generate(random).map(Fnr::toString);
    }

    /**
     * Generates a single valid fnr using the classwide SecureRandom instance
     * @return a valid fnr
     */
    public static String singleFnr() {
        return generate().findFirst().orElseThrow(() -> new IllegalStateException("Couldn't find a valid fnr"));
    }

    /**
     * Generates a single valid fnr using the provided seed for initializing
     * a java.util.Random instance
     * @param seed for instantiating Random
     * @return single valid fnr
     */
    public static String singleFnr(long seed) {
        return generate(seed).findFirst().orElseThrow(() -> new IllegalStateException("Couldn't find a valid fnr"));
    }

    private static Stream<Fnr> generate(Random random) {
        return Stream.generate(() -> singleFnr(random)).filter(Fnr::isValid);
    }
    private static Fnr singleFnr(Random random) {
        Year year = Year.of(START_YEAR + random.nextInt(120));
        Month month = Month.of(random.nextInt(12) + 1);
        int day;
        switch (month) {
            case FEBRUARY:
                if (year.isLeap()) {
                    day = random.nextInt(29) + 1;
                } else {
                    day = random.nextInt(28) + 1;
                }
                break;
            case JANUARY:
            case MARCH:
            case MAY:
            case JULY:
            case AUGUST:
            case OCTOBER:
            case DECEMBER:
                day = random.nextInt(31) + 1;
                break;
            default:
                day = random.nextInt(30) + 1;
        }
        LocalDate birthDay = LocalDate.of(year.getValue(), month, day);
        return new Fnr(birthDay, random);
    }
}
