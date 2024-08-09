package ui;

import chess.ChessGame;
import model.GameData;
import requestresult.*;
import serveraccess.ServerFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PostLogin {

    private String auth;
    private Map<Integer, GameData> games = new HashMap<>();

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
                logout();
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
                System.out.printf("Logged out %n");
            }
            else if (line.equals("observe")) {
                observe();
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
                "observe <ID> - allows you to watch a game %n" +
                "logout - logs you out %n" +
                "quit - exits the program %n" +
                "help - get list of commands %n");
    }

    private void list() {
        try {
            ListGamesResult result = new ServerFacade(8080).listGames(auth);
            if (result.message() == null) {
                games.clear();
                for (GameData game : result.games()) {
                    int num = games.size() + 1;
                    games.put(num, game);
                    System.out.printf(num + ":" + game.gameName() +
                            ", Black player: " + game.blackUsername() +
                            ", White player: " + game.whiteUsername() + "%n");
                }
            } else {
                new ChessClient().errorPrint(result.message());
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
            if (result.message() == null) {
                System.out.printf("Game created %n");
            } else {
                new ChessClient().errorPrint(result.message());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void join() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please enter the game number: %n >>> ");
        int num = scanner.nextInt();
        scanner.nextLine();
        int gameID;
        if (games.containsKey(num)) {
            gameID = games.get(num).gameID();
        } else {
            System.out.println("Invalid game number");
            return;
        }
        System.out.printf("Please choose black (b) or white (w): %n >>> ");
        ChessGame.TeamColor color = null;
        String answer = scanner.nextLine();
        if (answer.equals("b")) {
            color = ChessGame.TeamColor.BLACK;
        }
        else if (answer.equals("w")) {
            color = ChessGame.TeamColor.WHITE;
        }
        try {
            JoinGameResult result = new ServerFacade(8080).joinGame(auth, color, gameID);
            if (result.message() == null) {
                System.out.printf("Joined game %n");
                new Gameplay().run(color, games.get(num));
            } else {
                new ChessClient().errorPrint(result.message());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void logout() {
        try {
            LogoutResult result = new ServerFacade(8080).logout(auth);
            if (result.message() != null) {
                new ChessClient().errorPrint(result.message());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void observe() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please enter the game ID: %n >>> ");
        int gameID = Integer.parseInt(scanner.nextLine());
        try {
            System.out.printf(games.get(gameID).toString() + "%n");
        } catch (Exception e) {
            System.out.println("Wrong game ID, please check your game ID");
        }
    }
}
