package com.example.srprs.util;

import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EuckrEncoder {
    private EuckrEncoder() {
    }

    @SneakyThrows
    public static String toUtf(String euckr) {
        CharBuffer cbuffer = CharBuffer.wrap((new String(euckr.getBytes(), "EUC-KR")).toCharArray());

        Charset utf8charset = StandardCharsets.UTF_8;

        ByteBuffer bbuffer = utf8charset.encode(cbuffer);

        return new String(bbuffer.array());
    }
}
