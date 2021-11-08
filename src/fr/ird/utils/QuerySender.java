package fr.ird.utils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import java.util.Map;

public class QuerySender {

    //change this whether you are in production or not
    private HttpURLConnection http;

    public QuerySender() throws Exception {
    }

    /**
     * @param query NOT FORMATTED PLEASE
     * @return Map<String, Object> equivalent to org.json.JsonObject.toMap()
     */
    public Map<String, Object> sendQuery(String query) throws IOException {

        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8.name());
        String getArguments = "?query=" + encoded + "&format=application%2Fsparql-results%2Bjson&timeout=20000";

        http = (HttpURLConnection) new URL("http://sparql.southgreen.fr/" + getArguments).openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "application/sparql-results+json,*/*;q=0.9");
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        http.setConnectTimeout(20000);
//        http.setReadTimeout(20000);
        http.setDoOutput(true);
        http.connect();

        DataOutputStream out = new DataOutputStream(http.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));

        System.out.println(getArguments);

        out.writeBytes(getArguments);
        out.flush();
        out.close();

        int r;
        if ((r = http.getResponseCode()) > 400) throw new ProtocolException("sendQuery() An error has occurred : " + r);

        System.out.println("Response is : " + http.getResponseMessage());

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        System.out.println(response.toString());


        http.disconnect();

        return new JSONObject(response).toMap();
    }
}
