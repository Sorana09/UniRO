package com.example.personalizedLearningPlatform.metrics;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ViewUniversityMetric {
    private final Meter meter;

    private static final AttributeKey<String> UNIVERSITY_ID_ATTRIBUTE = AttributeKey.stringKey("university");

    public void registerViewForUniversity(Integer universityId) {
        LongCounter counter = meter.counterBuilder("university.views")
                .setDescription("Views per university")
                .setUnit("{view}")
                .build();

        counter.add(1L, Attributes.of(UNIVERSITY_ID_ATTRIBUTE, universityId.toString()));
    }

    //TODO: add more metrics for universities and categories



}
