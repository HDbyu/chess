package ui;

import requestresult.LoginResult;
import serveraccess.ServerFacade;

import java.util.Scanner;

public class chessClient {
    public void run() {
        boolean run = true;
        while (run) {
            System.out.print(">>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equals("help")) {
                System.out.printf("register <USERNAME> <PASSWORD> <EMAIL> - to create an account %n" +
                        "login <USERNAME> <PASSWORD> - to play chess &n" +
                        "quit - exit the program %n" +
                        "help - get list of commands %n");
            }
            else if (line.equals("login")) {
                try {
                    LoginResult result = new ServerFacade().login(scanner.next(), scanner.next());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.printf("unrecognized command, to get a list of commands type 'help' %n");
            }
        }
    }
}
