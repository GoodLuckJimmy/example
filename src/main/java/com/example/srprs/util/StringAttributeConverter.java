package com.example.srprs.util;

import javax.persistence.AttributeConverter;

public class StringAttributeConverter implements AttributeConverter<String, String> {
    private static final String ENCRYPTION_KEY = "r4u7x!%D^axG-P45";

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }

        return AES256Util.encrypt(ENCRYPTION_KEY, attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return AES256Util.decrypt(ENCRYPTION_KEY, dbData);
    }
}
