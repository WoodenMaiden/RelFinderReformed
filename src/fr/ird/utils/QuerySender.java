package fr.ird.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class QuerySender {

    //change this whether you are in production or not
    private static final boolean isProd = false;
    private static final String address = (isProd) ? "http://127.0.0.1:9090" : "http://sparql.southgreen.fr/";


    /**
     * @param query NOT FORMATTED PLEASE
     * @return Map<String, Object> equivalent to org.json.JsonObject.toMap()
     */
    public static Map<String, Object> sendQuery(String query) throws UnsupportedEncodingException {

        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8.name());

        System.out.println(encoded);

        return null;
    }
}
