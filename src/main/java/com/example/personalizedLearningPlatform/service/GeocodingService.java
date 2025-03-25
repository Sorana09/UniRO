package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.UniversityRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GeocodingService {

    private static final String GOOGLE_GEOCODE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String GOOGLE_API_KEY = "";
    private final UniversityRepository universityRepository;

    public double[] getCoordinates(String name, String location) {
        try {
            String query = name + ", " + location + ", Romania";
            String encodedQuery = URLEncoder.encode(query, "UTF-8");

            String url = GOOGLE_GEOCODE_URL + encodedQuery + "&key=" + GOOGLE_API_KEY;
            log.info("Query URL for geocoding: {}", url);

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            log.info("API Response: {}", response);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonArray = objectMapper.readTree(response);

            if (jsonArray.has("results") && jsonArray.get("results").size() > 0) {
                JsonNode locationData = jsonArray.get("results").get(0).get("geometry").get("location");

                double latitude = locationData.get("lat").asDouble();
                double longitude = locationData.get("lng").asDouble();

                return new double[]{latitude, longitude};
            } else {
                log.warn("No results found for the location: {}", query);
            }
        } catch (Exception e) {
            log.error("Error getting coordinates for {}: {}", name, e.getMessage());
        }

        return new double[]{0, 0};
    }

    public void updateUniversityCoordinates() {
        List<UniversityEntity> universities = universityRepository.findAll();

        for (UniversityEntity university : universities) {

            String name = university.getName();
            String location = university.getLocation();

            double[] coordinates = getCoordinates(name, location);

            if (coordinates[0] != 0 && coordinates[1] != 0) {
                universityRepository.updateCoordinates(university.getId(), coordinates[0], coordinates[1]);
                log.info("Updated coordinates for {}. Lat: {}, Lon: {}", name, coordinates[0], coordinates[1]);
            } else {
                log.warn("Could not find coordinates for university: {}", name);
            }
        }
    }
}
