package executor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    private RequestUtils() {
    }


    public static String sendGETRequest(String sUrl, String apiKey, String contentType){
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(sUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if(contentType == null) contentType = "application/json";
            conn.setRequestProperty("Content-Type", contentType);
            if(apiKey != null){
                conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            }
            conn.setConnectTimeout(5000);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())))) {
                String output;
                while ((output = br.readLine()) != null) {
                    result.append(output);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result.toString();
    }

    public static String sendPOSTRequest(String sUrl, String body, String contentType){
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(sUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            if(contentType == null) contentType = "application/json";
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(8000);

            conn.setDoOutput(true);
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
            } catch (Exception e){
                logger.info("{}", e.getMessage());
                e.printStackTrace();
            }

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())))) {
                String output;
                while ((output = br.readLine()) != null) {
                    result.append(output);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result.toString();
    }


}

