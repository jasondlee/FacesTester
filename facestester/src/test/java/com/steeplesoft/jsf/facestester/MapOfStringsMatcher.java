package com.steeplesoft.jsf.facestester;

import org.junit.internal.matchers.TypeSafeMatcher;
import org.hamcrest.Description;

import java.util.Map;

public class MapOfStringsMatcher extends TypeSafeMatcher<Map<String, String>> {
    private String expectedKey;

    public MapOfStringsMatcher(String expectedKey) {
        this.expectedKey = expectedKey;
    }

    public static MapOfStringsMatcher containsKey(String key) {
        return new MapOfStringsMatcher(key);
    }

    public boolean matchesSafely(Map<String, String> map) {
        return map.containsKey(expectedKey);
    }

    public void describeTo(Description description) {
        description
                .appendText("Map containing the key {")
                .appendValue(expectedKey)
                .appendText("}");
    }
}
