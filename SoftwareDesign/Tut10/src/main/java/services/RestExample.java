package services;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestExample {
    public static void main(String[] args) {
        try {

            URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(br);

            System.out.println("id = " + jsonObject.get("id"));
            System.out.println("userId = " + jsonObject.get("userId"));
            System.out.println("title = " + jsonObject.get("title"));
            System.out.println("body = " + jsonObject.get("body"));
            System.out.println();
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }
}