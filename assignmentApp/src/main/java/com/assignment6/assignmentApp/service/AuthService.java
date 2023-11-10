package com.assignment6.assignmentApp.service;

import com.assignment6.assignmentApp.service.impl.AuthServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class AuthService implements AuthServiceImpl {
    
    String sunbaseUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private String bearerToken;

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String token) {
        bearerToken = token;
    }

    public boolean loginAndObtainToken(String username, String password) {
        // Make a POST request to the authentication API to obtain the Bearer token
        System.out.println("inside loginandibtain");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set the login credentials
        String authApiUrl = sunbaseUrl;
        String requestJson = "{\"login_id\":\"" + username + "\",\"password\":\"" + password + "\"}";

        // Create an HTTP request entity with the headers
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(authApiUrl, entity, String.class);

        if (response.getStatusCode().value() == 200) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                String accessToken = jsonNode.get("access_token").asText();

                if (accessToken != null && !accessToken.isEmpty()) {
                    setBearerToken("Bearer " + accessToken); // Add "Bearer" prefix to the token
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
