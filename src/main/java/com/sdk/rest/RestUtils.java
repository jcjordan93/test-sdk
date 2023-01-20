package com.sdk.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;

public class RestUtils {

    private static final String BASE_URL = "https://dev-insights-api.rebid.co/collect/%s/%s";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);

    public static void connectToInsights(String eventType, String sourceId, JsonObject jsonObject) {
        try {
            String url = String.format(BASE_URL, eventType, sourceId);
            final HttpPost httpPost = new HttpPost(url);

            final String json = new Gson().toJson(jsonObject);
            final StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            try(CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(httpPost);) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    LOGGER.error("Got invalid status code from service : " + statusCode);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Failed to send request to insights", ex);
        }
    }
}
