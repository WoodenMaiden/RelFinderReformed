package fr.ird.runtime;

import java.io.*;
import java.net.Socket;

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

            System.out.println(in);

            //TODO


            out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: text/html");
            out.println("Access-Control-Allow-Origin: null");
            out.println("");
            out.flush();

            sockClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}