package ui;

import requestresult.*;
import serveraccess.ServerFacade;

import java.util.Scanner;

public class ChessClient {

    private String auth;
    public void run() {
        boolean run = true;
        while (run) {
            System.out.print(">>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equals("help")) {
                help();
            }
            else if (line.equals("login")) {
                boolean logedIn = login();
                if (logedIn) {
                    run = new PostLogin(auth).run();
                }
            }
            else if (line.equals("register")) {
                boolean logedIn = register();
                if (logedIn) {
                    run = new PostLogin(auth).run();
                }
            }
            else if (line.equals("quit")) {
                run = false;
                System.out.printf("Exiting program. Thanks for playing%n");
            }
            else {
                System.out.printf("unrecognized command, to get a list of commands type 'help' %n");
            }
        }
    }

    private void help() {
        System.out.printf("register <USERNAME> <PASSWORD> <EMAIL> - to create an account %n" +
                "login <USERNAME> <PASSWORD> - to play chess %n" +
                "quit - exit the program %n" +
                "help - get list of commands %n");
    }

    private boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please enter your username: %n >>> ");
        String username = scanner.nextLine();
        System.out.printf("Please enter your password: %n >>> ");
        String password = scanner.nextLine();
        try {
            LoginResult result = new ServerFacade(8080).login(username, password);
            if (result.message() == null) {
                System.out.println("Logged in as " + result.username());
                auth = result.authToken();
            } else {
                errorPrint(result.message());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Login failed");
            return false;
        }
        return true;
    }

    private boolean register() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please enter your username: %n >>> ");
        String username = scanner.nextLine();
        System.out.printf("Please enter your password: %n >>> ");
        String password = scanner.nextLine();
        System.out.printf("Please enter your email: %n >>> ");
        String email = scanner.nextLine();
        try {
            RegisterResult result = new ServerFacade(8080).register(username, password, email);
            if (result.message() == null) {
                System.out.println("Logged in as " + result.username());
                auth = result.authToken();
            } else {
                errorPrint(result.message());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Register failed");
            return false;
        }
        return true;
    }

    public void errorPrint(String code) {
        switch (code) {
            case "400":
                System.out.println("bad request");
                break;
            case "401":
                System.out.println("unauthorized");
                break;
            case "403":
                System.out.println("already taken");
                break;
            case "500":
                System.out.println("server error");
                break;
        }
    }

}
