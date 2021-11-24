package com.example.srprs.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringAttributeConverterTest {

    @Test
    void stringAttributeConverter() {
        StringAttributeConverter converter = new StringAttributeConverter();
        String word = "abc";
        String encoded = converter.convertToDatabaseColumn(word);
        String pure = converter.convertToEntityAttribute(encoded);

        assertThat(word).isEqualTo(pure);
    }
}