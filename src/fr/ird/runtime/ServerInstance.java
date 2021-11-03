package fr.ird.runtime;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.regex.Pattern;

public class ServerInstance {

    private ServerSocket serverSocket;

    private Integer MAX_CLIENTS = -1; // -1 for unlimited or "none"
    private Integer PORT = 9090;

    private static final List KNOWN_FLAGS = new ArrayList<String>(Arrays.asList(new String[]{"-p", "-c", "-h"}));

    private static void printHelp() {
        System.out.println("usage : rfr run [options]");
        System.out.println("\noptions:");
        System.out.println("    -p port : specify on which port the server runs, default is 9090");
        System.out.println("    -c maxclients|none : specify how many clients can be connected, default is none");
        System.out.println("    -h : print this help");
        }

    private static Map<String, Object> parseArguments(String[] runArgs) {
        Map<String, Object> options = new HashMap<>();

        int f = 1;
        int index;

        try {
            while (f < runArgs.length) {

                if (runArgs[f].equals(KNOWN_FLAGS.get(2))) {
                    printHelp();
                    System.exit(0);
                }

                if ((index = KNOWN_FLAGS.lastIndexOf(runArgs[f])) != -1 /*&& index < KNOWN_FLAGS.size() - 2*/)
                    //we pass the next argument with ++f because it is the value
                    if (index == 0) options.put("PORT", Integer.parseInt(runArgs[++f]));
                    else options.put("MAX", Integer.parseInt(runArgs[++f]));

                ++f;
            }
        } catch (Exception e) {
            e.printStackTrace();
            printHelp();
            System.exit(1);
        }

        System.out.println(options);
        return options;
    };

    public ServerInstance(String[] runArgs) throws IOException {
        Map params = parseArguments(runArgs);
        if (params.containsKey("PORT")) PORT =  Math.abs((Integer) params.get("PORT"));
        if (params.containsKey("MAX")) MAX_CLIENTS =  Math.abs((Integer) params.get("MAX"));

        System.out.println("PORT="+PORT);
        System.out.println("MAX_CLIENTS="+MAX_CLIENTS);

        serverSocket = new ServerSocket(PORT);
    }

    @Override
    public String toString() {
        return "ServerInstance{" +
                "serverSocket=" + serverSocket +
                ", MAX_CLIENTS=" + MAX_CLIENTS +
                ", PORT=" + PORT +
                '}';
    }
}
