package websocketaccess;

import chess.ChessGame;

public interface GameHandler {

    public void updateGame(ChessGame game);
    public void printNotification(String message);
    public void printError(String message);
}
