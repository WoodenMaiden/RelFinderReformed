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

            //int i = 0;
            /*
            while (i < 14){
                System.out.println(in.readLine());
                ++i;
            }
            */

            String getArgument = in.readLine().split(" ")[1].substring(1);

            getArgument = URLDecoder.decode(getArgument, StandardCharsets.UTF_8.name());
            System.out.println(getArgument);

            Map <String, Object> nodes = new JSONObject(getArgument).toMap();

            QuerySender.sendQuery("Hello world!");



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