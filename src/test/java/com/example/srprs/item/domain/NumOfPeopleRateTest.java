package com.example.srprs.item.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NumOfPeopleRateTest {

    static Stream<Arguments> costTestParameters() {
        // basic price 100_000
        return Stream.of(
                Arguments.of(1, BigDecimal.valueOf(100_000)),
                Arguments.of(2, BigDecimal.valueOf(100_000)),
                Arguments.of(3, BigDecimal.valueOf(200_000)),
                Arguments.of(4, BigDecimal.valueOf(200_000)),
                Arguments.of(5, BigDecimal.valueOf(300_000)),
                Arguments.of(6, BigDecimal.valueOf(300_000)),
                Arguments.of(7, BigDecimal.valueOf(400_000)),
                Arguments.of(8, BigDecimal.valueOf(400_000)),
                Arguments.of(9, BigDecimal.valueOf(500_000)),
                Arguments.of(10, BigDecimal.valueOf(500_000))
        );
    }


    @ParameterizedTest
    @MethodSource("costTestParameters")
    void cost(int numOfPeople, BigDecimal expectedPrice) {
       NumOfPeopleRate numOfPeopleRate = new NumOfPeopleRate(new AccommodationPrice(),numOfPeople);
       assertThat(numOfPeopleRate.cost()).isEqualByComparingTo(expectedPrice);
    }

}