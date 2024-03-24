/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class TranslatorDAO implements ITranslatorDAO {
    @Override
   public String translateHadith(String hadith) {
        HttpClient httpClient = HttpClient.newHttpClient();
        JSONObject requestBody = new JSONObject();
        requestBody.put("hadith", hadith);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:5000/translate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                // Successful response
                return parseJsonResponse(response.body());
            } else {
                // Handle error response
                System.err.println("Error: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseJsonResponse(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            return jsonResponse.getString("translation");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
