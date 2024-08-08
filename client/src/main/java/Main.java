import chess.*;
import ui.ChessClient;

public class Main {
    public static void main(String[] args) {
        System.out.println("♕ Welcome to Chess. Type 'help' for list of commands.");
        new ChessClient().run();
    }
}