package fr.ird.runtime;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import fr.ird.utils.QuerySender;
import org.json.*;

public class Job implements Runnable{

    private final Socket sockClient;

    public Job(Socket client) {
        this.sockClient = client;
    }

    @Override
    public void run() {
        try {

            System.out.println("Got request from" + sockClient.getInetAddress().toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
            PrintWriter out = new PrintWriter(sockClient.getOutputStream());

            String getArgument = in.readLine().split(" ")[1].substring(1);

            getArgument = URLDecoder.decode(getArgument, StandardCharsets.UTF_8.name());

            Map <String, Object> nodes = new JSONObject(getArgument).toMap();

            //sample query, TODO Change it to something useful
            new QuerySender().sendQuery("BASE <http://www.southgreen.fr/agrold/>\n" +
                    "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX vocab:<vocabulary/>\n" +
                    "\n" +
                    "SELECT DISTINCT ?s ?p ?o\n" +
                    "WHERE { \n" +
                    " ?s ?p ?o .\n" +
                    "  FILTER ( $s = <http://www.openlinksw.com/virtrdf-data-formats#default-iid>)\n" +
                    "}");



            out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: application/sparql-results+json");
            out.println("Access-Control-Allow-Origin: null");
            out.println("");
            out.flush();

            sockClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}