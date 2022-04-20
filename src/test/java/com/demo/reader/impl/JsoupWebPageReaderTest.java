package com.demo.reader.impl;

import com.demo.reader.WebPageReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsoupWebPageReaderTest {

    @Test
    void shouldReadPageAndGetLinks_whenValidUrlIsPassed() {
        WebPageReader reader = new JsoupWebPageReader();
        List<String> links = reader.readPageAndGetLinks("http://monzo.com");
        assertTrue(links.size() > 0);
    }

    @Test
    void shouldThrowError_whenNullUrlIsSent() {
        WebPageReader reader = new JsoupWebPageReader();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reader.readPageAndGetLinks(null);
        });
    }

    @Test
    void shouldThrowError_whenEmptyUrlIsSent() {
        WebPageReader reader = new JsoupWebPageReader();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reader.readPageAndGetLinks(" ");
        });
    }
}