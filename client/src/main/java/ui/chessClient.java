package ui;

import java.util.Scanner;

public class chessClient {
    public void run() {
        boolean run = true;
        while (run) {
            System.out.printf("Type your numbers%n>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equals("help")) {
                System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account %n" +
                        "login <USERNAME> <PASSWORD> - to play chess &n" +
                        "quit - exit the program %n" +
                        "help - get list of commands");
            }
        }
    }
}
