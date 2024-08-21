package org.hometest.API;

import Utils.Constants;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sendgrid.Client;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class OrderESIMUsingAPI {

    //instantiate required classes
    Client client = new Client();
    Request request = new Request();
    //initialize hashmap used to store all information about token
    HashMap<String, String> hashMap = new HashMap<>();
    SoftAssert softAssert = new SoftAssert();

    public static void main(String[] args) {
    }

    //check expiry of token by converting 'expires_in' seconds - value we get from API -  to LocalDateTime
    //and specific format, then creating local date time from local computer and comparison
    //return false if token is expired
    public boolean isTokenExpired() {
        long epochTimeMillis = Long.parseLong(hashMap.get("expires_in"));
        Instant instant = Instant.ofEpochMilli(epochTimeMillis);

        ZoneId zoneId = ZoneId.systemDefault(); // Use the system default time zone
        LocalDateTime token = instant.atZone(zoneId).toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = token.format(formatter);

        LocalDateTime timenow = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return token.isBefore(timenow);
    }

    @Test
    //get token after posting to the Bearer authorization endpoint
    public String getToken() {
        request.setMethod(Method.POST);
        request.setEndpoint("/v2/token");
        request.setBaseUri(Constants.OAUTH_TOKEN_URL);
        request.addQueryParam("client_id", "974d515d41f86868eccd2d22aae8d10e");
        request.addQueryParam("client_secret", "tILYEqQRq5PnZ5nccQZ1IiVugUWhZN2UveJZ9rVa");
        request.addQueryParam("grant_type", "client_credentials");
        Response response = null;
        try {
            response = client.api(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //save all info to hashmap to be used later;
        JsonElement je = JsonParser.parseString(response.getBody());
        hashMap.put("access_token", je.getAsJsonObject().get("data").getAsJsonObject().get("access_token").getAsString());
        hashMap.put("token", je.getAsJsonObject().get("data").getAsJsonObject().get("token").getAsString());
        hashMap.put("expires_in", String.valueOf(je.getAsJsonObject().get("data").getAsJsonObject().get("expires_in").getAsDouble()));
        softAssert.assertEquals(response.getStatusCode(), 200);
        return je.getAsJsonObject().get("data").getAsJsonObject().get("access_token").getAsString();
    }

    @Test
    //send POST request to /v2/orders endpoint
    //check if token is expired before sending
    public void sendAPIOrder() {
        request.setMethod(Method.POST);
        request.setEndpoint("/v2/orders");
        request.setBaseUri(Constants.OAUTH_TOKEN_URL);
        request.addHeader("Accept", "application/json");
        request.addQueryParam("quantity", "6");
        request.addHeader("Authorization", "Bearer " + hashMap.get("access_token"));
        request.addQueryParam("package_id=", "merhaba-7days-1gb");
        request.addQueryParam("type", "eSIM");
        Response response = null;

        if (isTokenExpired()) {
            try {
                response = client.api(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
