package fr.ird;

import fr.ird.runtime.ServerInstance;

import java.io.IOException;

public class Main {

    private static void printHelp() {
        System.out.println("usage : rfr command [options]");
        System.out.println("\ncommand:");
        System.out.println("    run : run an instance of the server");
        System.out.println("    version : print server's version");
        System.out.println("    help : print this help");
        System.out.println("\noptions depends from command, use -h to get more information about said command");
    }

    public static void main(String[] args) throws Exception {

        switch (args[0]) {
            case "run" :
                System.out.println("Running!");
                ServerInstance server = new ServerInstance(args);
                System.out.println(server.toString());
                server.run();
                break;

            case "version" :
                System.out.println("RelFinderReformed 1.0.0 beta");
                break;

            case "help" :
                printHelp();
                break;

            default:
                printHelp();
                System.exit(1);
                break;
        }


        System.exit(0);
    }
}
