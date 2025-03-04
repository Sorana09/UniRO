package com.example.personalizedLearningPlatform.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ViewUniversityMetric {
    private final MeterRegistry meterRegistry;

    public void registerViewForUniversity(Integer universityId){
        Counter.builder("total_views_university")
                .tags("university", universityId.toString())
                .register(meterRegistry)
                .increment();
    }

}
