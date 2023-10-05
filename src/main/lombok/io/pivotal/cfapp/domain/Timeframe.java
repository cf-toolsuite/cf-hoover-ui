package io.pivotal.cfapp.domain;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Timeframe {

    IN_LAST_DAY(0, "in-last-day"),
    BETWEEN_ONE_DAY_AND_TWO_DAYS(1, "between-one-day-and-two-days"),
    BETWEEN_TWO_DAYS_AND_ONE_WEEK(2, "between-two-days-and-one-week"),
    BETWEEN_ONE_WEEK_AND_TWO_WEEKS(3, "between-one-week-and-two-weeks"),
    BETWEEN_TWO_WEEKS_AND_ONE_MONTH(4, "between-two-weeks-and-one-month"),
    BETWEEN_ONE_MONTH_AND_THREE_MONTHS(5, "between-one-month-and-three-months"),
    BETWEEN_THREE_MONTHS_AND_SIX_MONTHS(6, "between-three-months-and-six-months"),
    BETWEEN_SIX_MONTHS_AND_ONE_YEAR(7, "between-six-months-and-one-year"),
    BEYOND_ONE_YEAR(8, "beyond-one-year");

    private final Integer ordinal;
    private final String key;

    public static Map<String, Long> sort(Map<String, Long> items) {
        Map<String, Long> result = new LinkedHashMap<>();
        Arrays
            .asList(values())
                .forEach(t -> result.put(t.getKey(), items.get(t.getKey())));
        return result;
    }
}