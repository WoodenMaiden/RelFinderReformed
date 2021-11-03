package fr.ird;

public class Main {

    private static void printHelp() {
        System.out.println("usage : rfr [command]");
        System.out.println("\ncommand:");
        System.out.println("    run : run an instance of the server");
        System.out.println("    version : print server's version");
        System.out.println("    help : print this help");

    }

    public static void main(String[] args) {

        switch (args[0]) {
            case "run" :
                System.out.println("Running!");
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
