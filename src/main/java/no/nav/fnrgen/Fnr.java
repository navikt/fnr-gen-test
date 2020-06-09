package no.nav.fnrgen;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Fnr {
    private final List<Integer> firstControlNumbers = List.of(3, 7, 6, 1, 8, 9, 4, 5, 2, 1);
    private final List<Integer> secondControlNumbers = List.of(5, 4, 3, 2, 7, 6, 5, 4, 3, 2, 1);
    private static final DateTimeFormatter fnrFormatter = DateTimeFormatter.ofPattern("ddMMyy");
    private static final DateTimeFormatter ageParser = DateTimeFormatter.ofPattern("ddMMyyyy");
    private final LocalDate birthDate;
    private final int individualNumber;
    private final int firstControl;
    private final int secondControl;

    Fnr(LocalDate birthDate) {
        this(birthDate, new Random());
    }

    Fnr(LocalDate birthDate, Random random) {
        this.birthDate = birthDate;
        this.individualNumber = calculateIndividualNumber(random);
        this.firstControl = firstChecksum();
        this.secondControl = secondChecksum();
    }

    Fnr(LocalDate birthDate, int individualNumber) {
        this.birthDate = birthDate;
        this.individualNumber = individualNumber;
        this.firstControl = firstChecksum();
        this.secondControl = secondChecksum();
    }

    static Fnr fromString(String fnr) {
        if (fnr.length() != 11) throw new IllegalArgumentException("Fnrs must be exactly 11 chars long");
        String birthDate = fnr.substring(0, 6);
        String individual = fnr.substring(6, 9);
        int i = Integer.parseInt(individual, 10);
        LocalDate bDay;
        if (i < 500) {
            bDay = LocalDate.parse(fnr.substring(0, 4) + "19" + fnr.substring(4, 6), ageParser);
        } else {
            bDay = LocalDate.parse(birthDate, fnrFormatter);
            if (bDay.isAfter(LocalDate.now())) {
                bDay = bDay.minusYears(200);
            }
        }
        return new Fnr(bDay, i);
    }

    LocalDate getBirthDate() {
        return birthDate;
    }

    int getIndividualNumber() {
        return individualNumber;
    }

    int calculateIndividualNumber(Random random) {
        int soFar = random.nextInt(500);
        if ((birthDate.getYear() / 100) % 2 == 0) {
            soFar += 500;
        }
        return soFar;
    }

    boolean isFemale() {
        return individualNumber % 2 == 0;
    }

    int getFirstControl() {
        return firstControl;
    }

    int getSecondControl() {
        return secondControl;
    }


    private int firstChecksum() {
        var sumSoFar = zip(fixedPart(), firstControlNumbers, (a, b) -> a * b).sum();
        return 11 - (sumSoFar % 11);
    }

    private int secondChecksum() {
        var control = fixedPart();
        control.add(firstControl);
        var sumSoFar = zip(control, secondControlNumbers, (a, b) -> a * b).sum();
        return 11 - (sumSoFar % 11);
    }

    boolean isValid() {
        if (toString().length() != 11) return false;
        var first = zip(asNumbers(), firstControlNumbers, (n, c) -> n * c).sum();
        var second = zip(asNumbers(), secondControlNumbers, (n, c) -> n * c).sum();
        return first % 11 == 0 && second % 11 == 0;
    }

    private List<Integer> fixedPart() {
        var fixed = new ArrayList<>(birthNumbers());
        fixed.add(individualNumber / 100);
        fixed.add((individualNumber % 100) / 10);
        fixed.add(individualNumber % 10);
        return fixed;
    }

    private List<Integer> asNumbers() {
        var fnr = new ArrayList<>(fixedPart());
        fnr.add(firstControl);
        fnr.add(secondControl);
        return fnr;
    }

    private List<Integer> birthNumbers() {
        var f = formattedBirthday();
        return f.chars().mapToObj(l -> l - '0').collect(Collectors.toList());
    }

    private String formattedBirthday() {
        return birthDate.format(fnrFormatter);
    }

    private IntStream zip(List<Integer> first, List<Integer> second, BiFunction<Integer, Integer, Integer> f) {
        return IntStream.range(0, Math.min(first.size(), second.size())).map(idx -> f.apply(first.get(idx), second.get(idx)));
    }

    public String toString() {
        return asNumbers().stream().map(Object::toString).collect(Collectors.joining());
    }
}
