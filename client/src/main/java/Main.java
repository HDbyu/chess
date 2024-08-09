import chess.*;
import model.GameData;
import ui.ChessClient;
import ui.Gameplay;

public class Main {
    public static void main(String[] args) {
        System.out.println("♕ Welcome to Chess. Type 'help' for list of commands.");
        new ChessClient().run();
    }
}