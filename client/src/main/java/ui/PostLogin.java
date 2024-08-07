package ui;

import requestresult.*;
import serveraccess.ServerFacade;

import java.util.Scanner;

public class PostLogin {

    private String auth;

    public PostLogin(String auth) {
        this.auth = auth;
    }

    public boolean run() {
        boolean run = true;
        while (run) {
            System.out.print(">>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equals("help")) {
                help();
            }
            else if (line.equals("quit")) {
                run = false;
                System.out.printf("Exiting program. Thanks for playing%n");
                return false;
            }
            else if (line.equals("list")) {
                list();
            }
            else if (line.equals("create")) {
                create();
            }
            else {
                System.out.printf("unrecognized command, to get a list of commands type 'help' %n");
            }
        }
        return true;
    }

    private void help() {
        System.out.printf("create <NAME> - creates a new game %n" +
                "list - Lists all available games %n" +
                "join <ID> [WHITE|BLACK] - adds you to a game %n" +
                "logout - logs you out %n" +
                "quit - exits the program %n" +
                "help - get list of commands");
    }

    private void list() {
        Scanner scanner = new Scanner(System.in);
        try {
            ListGamesResult result = new ServerFacade(8080).listGames(auth);
            System.out.println(result.games().size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void create() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please enter the name of the game: %n >>> ");
        String name = scanner.nextLine();
        try {
            CreateGameResult result = new ServerFacade(8080).createGame(auth, name);
            System.out.println("Game created with ID " + result.gameID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
