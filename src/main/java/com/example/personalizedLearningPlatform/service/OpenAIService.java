package com.example.personalizedLearningPlatform.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@AllArgsConstructor
public class OpenAIService {

    private final String API_KEY = "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";
    private final OkHttpClient client = new OkHttpClient();

    public String generateDescription(String universityName) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String encodedUniversityName = URLEncoder.encode(universityName, StandardCharsets.UTF_8.toString());
        String jsonRequest = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"\uD83C\uDF1F Generează o descriere detaliată și atractivă a universității %s în limba română. " +
                        "Include informații structurate despre istorie, facultăți, infrastructură și oportunități pentru studenți." +
                        " Folosește un ton profesional și captivant, cu paragrafe bine delimitate pentru lizibilitate." +
                        "Adauga si cateva emoji in descriere ca sa para mai interesanta, la fiecare inceput de paragraf, nu doar la final\"}]}"
                , encodedUniversityName);

        RequestBody body = RequestBody.create(jsonRequest, JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        // Log the request details
        log.info("Sending request to OpenAI API: {}", request);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                String content = jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();

                return content.replaceAll("\\n\\n", "\n\n");
            } else {
                // Log the response body for debugging
                log.error("Error generating description: {}", response.body() != null ? response.body().string() : "No response body");
                throw new IOException("Error generating description: " + response);
            }
        }
    }


    public String generateFaculties(String interests, String cities) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonRequest = new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("messages", new JSONArray()
                        .put(new JSONObject()
                                .put("role", "user")
                                .put("content", String.format("Pe baza următoarelor interese: %s și orașe: %s, recomandă facultăți potrivite din baza de date si ia numele axact asa cum e in baza de date ale facultatilor si a universitatilor, pe langa asta adauaga o mica descrierea pt alegea facuta.", interests, cities))))
                .toString();

        RequestBody body = RequestBody.create(jsonRequest, JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        // Log the request details
        log.info("Sending request to OpenAI API: {}", request);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                String content = jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();
                return content;
            } else {
                // Log the response body for debugging
                log.error("Error generating faculties: {}", response.body() != null ? response.body().string() : "No response body");
                throw new IOException("Error generating faculties: " + response);
            }
        }
    }


}
