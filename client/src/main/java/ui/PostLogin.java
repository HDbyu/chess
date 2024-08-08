package ui;

import chess.ChessGame;
import model.GameData;
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
            else if (line.equals("join")) {
                join();
            }
            else if (line.equals("logout")) {
                logout();
                run = false;
                System.out.printf("Logging out %n");
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
                "help - get list of commands %n");
    }

    private void list() {
        Scanner scanner = new Scanner(System.in);
        try {
            ListGamesResult result = new ServerFacade(8080).listGames(auth);
            for (GameData game : result.games()) {
                System.out.printf(game.gameName() + ":" + game.gameID() +
                        ", Black player: " + game.blackUsername() +
                        ", White player: " + game.whiteUsername() + "%n");
            }
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
            System.out.println("Game created with ID " + result.gameID() + "%n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void join() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please enter the game ID: %n >>> ");
        int gameID = Integer.parseInt(scanner.nextLine());
        System.out.printf("Please choose black (b) or white (w): %n >>> ");
        ChessGame.TeamColor color = null;
        if (scanner.nextLine().equals("b")) {
            color = ChessGame.TeamColor.BLACK;
        }
        else if (scanner.nextLine().equals("w")) {
            color = ChessGame.TeamColor.WHITE;
        }
        try {
            JoinGameResult result = new ServerFacade(8080).joinGame(auth, color, gameID);
            System.out.printf("Joined game %n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void logout() {
        try {
            LogoutResult result = new ServerFacade(8080).logout(auth);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
