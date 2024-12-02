package com.example.personalizedLearningPlatform.sqlMethods;

import java.util.Map;
import java.util.StringJoiner;

public class SQLMethod {
    public static String getAll(Map<String, Object> params) {
        StringJoiner joiner = new StringJoiner(" AND ", " WHERE ", "");

        params.forEach((k, v) -> {
            joiner.add(k + " = ?");
        });

        return (params.size() > 0) ? joiner.toString() : "";

    }
}
