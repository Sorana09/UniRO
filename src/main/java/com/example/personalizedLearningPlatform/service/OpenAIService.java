package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
@AllArgsConstructor
public class OpenAIService {

    private final String API_KEY = "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";
    private final OkHttpClient client = new OkHttpClient();
    private final UniversityService universityService;

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
                log.error("Error generating description: {}", response.body() != null ? response.body().string() : "No response body");
                throw new IOException("Error generating description: " + response);
            }
        }
    }


    public String generateFaculties(String interests, String cities) throws IOException {
        List<UniversityEntity> universityEntities = universityService.getAllUniversities();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonRequest = new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("messages", new JSONArray()
                        .put(new JSONObject()
                                .put("role", "user")
                                .put("content", String.format("Pe baza următoarelor interese: %s și orașe: %s, recomandă facultăți potrivite din baza de date si ia numele axact asa cum e in baza de date ale " +
                                        "facultatilor si a universitatilor, pe langa asta adauaga o mica descrierea pt alegea facuta.",
                                        interests, cities))))
                .toString();

        RequestBody body = RequestBody.create(jsonRequest, JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

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
                log.error("Error generating faculties: {}", response.body() != null ? response.body().string() : "No response body");
                throw new IOException("Error generating faculties: " + response);
            }
        }
    }

    public String generateInformationForCategories(String categoryName) throws IOException{
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String encodedCategoryName = URLEncoder.encode(categoryName, StandardCharsets.UTF_8.toString());
        String jsonRequest = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"\uD83C\uDF1F Generează o descriere detaliată și atractivă a facultatii %s în limba română. " +
                        "Include informații structurate despre istorie, facultăți, infrastructură și oportunități pentru studenți." +
                        " Folosește un ton profesional și captivant, cu paragrafe bine delimitate pentru lizibilitate." +
                        "Fa totul ca un fel de lista : like foarte organizat: in primul si primul rand include informatii necesare cum ar fi: modul de admitere: diploma de bac sau admitere, cat de grea e admiterea si la ce trebuie sa inveti, unde poti gasi aceste informatii . aceste informatii sa le zicem vitale le vreau sa iasa in evidenta sa fie scrise: tip admitere: examen de admitere sau diploma de bac, nu doar niste paragrafe care sa fie greu de citit, da si site-ul facultatii"+
                        "ca ultim detaliu , afiseazami la final lat si long locatiei fiecariei facultati te rog"+
                        "Adauga si cateva emoji in descriere ca sa para mai interesanta, la fiecare inceput de paragraf, nu doar la final\"}]}"
                , encodedCategoryName);

        RequestBody body = RequestBody.create(jsonRequest, JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        try(Response response = client.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                JSONObject jsonResponse = new JSONObject(response.body().string());
                String content = jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();
                return content.replaceAll("\\n\\n", "\n\n");
            } else {
                log.error("Error generating description: {}", response.body() != null ? response.body().string() : "No response body");
                throw new IOException("Error generating description: " + response);
            }

        }


    }

    public double[] generateInformationForCategoriesLatAndLong(String categoryName) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String encodedCategoryName = URLEncoder.encode(categoryName, StandardCharsets.UTF_8.toString());
        String jsonRequest = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"\uD83C\uDF1F Generează latitudinea și longitudinea facultății %s. ai grija ca datele sa fie corecte, ai grija ca odata le generezi cumva si alta data altfel, pune corect, VEZI CA MEREU GENEREZI ALTE NUMEREM. PUNE CORECT\"}]}"
                , encodedCategoryName);

        RequestBody body = RequestBody.create(jsonRequest, JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                String content = jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();

                content = content.replace("\n", " ").replace("\r", " ").trim();

                Pattern pattern = Pattern.compile("([-+]?[0-9]*\\.?[0-9]+)");
                Matcher matcher = pattern.matcher(content);

                List<Double> coordinates = new ArrayList<>();
                while (matcher.find()) {
                    coordinates.add(Double.parseDouble(matcher.group()));
                }

                if (coordinates.size() >= 2) {
                    return new double[]{coordinates.get(0), coordinates.get(1)};
                } else {
                    throw new IOException("Invalid response format: " + content);
                }
            } else {
                log.error("Error generating coordinates: {}", response.body() != null ? response.body().string() : "No response body");
                throw new IOException("Error generating coordinates: " + response);
            }
        }
    }



    public String generateEntranceMethod(String categoryName) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String encodedCategoryName = URLEncoder.encode(categoryName, StandardCharsets.UTF_8.toString());
        String jsonRequest = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"\uD83C\uDF1F Generează metoda de admitere pentru facultatea %s. " +
                        "Include informații structurate despre modul de admitere, cerințele de admitere și alte detalii relevante pentru studenți.\"}]}"
                , encodedCategoryName);
        RequestBody body = RequestBody.create(jsonRequest, JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
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
                log.error("Error generating entrance method: {}", response.body() != null ? response.body().string() : "No response body");
                throw new IOException("Error generating entrance method: " + response);
            }
        }
    }


}
