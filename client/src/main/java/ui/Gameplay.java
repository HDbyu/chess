package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;

import static ui.EscapeSequences.*;

public class Gameplay {

    private ChessBoard board;
    public Gameplay() {

    }

    public void run(ChessGame.TeamColor color, GameData game) {
        board = game.game().getBoard();
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_COLOR_BLACK);
        System.out.print("   ");
        System.out.print(" h ");
        System.out.print(" g ");
        System.out.print(" f ");
        System.out.print(" e ");
        System.out.print(" d ");
        System.out.print(" c ");
        System.out.print(" b ");
        System.out.print(" a ");
        System.out.print("   ");
        System.out.print(RESET_BG_COLOR);
        System.out.printf("%n");
        for (int i = 0; i < 8; i++) {

            for (int j = 7; j >= 0; j--) {
                if (j == 7) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(SET_TEXT_COLOR_BLACK);
                    System.out.print(" " + (i + 1) + " ");
                    System.out.print(RESET_BG_COLOR);
                }
                ChessPiece piece = board.getPiece(new ChessPosition(i + 1, j + 1));
                if ((i + j) == 0 || (i + j) % 2 == 0) {
                    System.out.print(SET_BG_COLOR_BLACK);
                } else {
                    System.out.print(SET_BG_COLOR_WHITE);
                }
                if (piece != null) {
                    if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                        System.out.print(SET_TEXT_COLOR_RED);
                    } else {
                        System.out.print(SET_TEXT_COLOR_BLUE);
                    }
                    switch (piece.getPieceType()) {
                        case KING:
                            System.out.print(" K ");
                            break;
                        case QUEEN:
                            System.out.print(" Q ");
                            break;
                        case BISHOP:
                            System.out.print(" B ");
                            break;
                        case ROOK:
                            System.out.print(" R ");
                            break;
                        case KNIGHT:
                            System.out.print(" N ");
                            break;
                        case PAWN:
                            System.out.print(" P ");
                            break;
                    }
                } else {
                    System.out.print("   ");
                }
                if (j == 0) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(SET_TEXT_COLOR_BLACK);
                    System.out.print(" " + (i + 1) + " ");
                    System.out.print(RESET_BG_COLOR);
                }
            }
            System.out.printf("%n");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_COLOR_BLACK);
        System.out.print("   ");
        System.out.print(" h ");
        System.out.print(" g ");
        System.out.print(" f ");
        System.out.print(" e ");
        System.out.print(" d ");
        System.out.print(" c ");
        System.out.print(" b ");
        System.out.print(" a ");
        System.out.print("   ");
        System.out.print(RESET_BG_COLOR);
        System.out.print(RESET_TEXT_COLOR);
        System.out.printf("%n");

        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("                              ");
        System.out.print(RESET_BG_COLOR);
        System.out.printf("%n");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_COLOR_BLACK);
        System.out.print("   ");
        System.out.print(" a ");
        System.out.print(" b ");
        System.out.print(" c ");
        System.out.print(" d ");
        System.out.print(" e ");
        System.out.print(" f ");
        System.out.print(" g ");
        System.out.print(" h ");
        System.out.print("   ");
        System.out.print(RESET_BG_COLOR);
        System.out.printf("%n");
        for (int i = 7; i >= 0; i--) {

            for (int j = 7; j >= 0; j--) {
                if (j == 7) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(SET_TEXT_COLOR_BLACK);
                    System.out.print(" " + (i + 1) + " ");
                    System.out.print(RESET_BG_COLOR);
                }
                ChessPiece piece = board.getPiece(new ChessPosition(i + 1, j + 1));
                if ((i + j) == 0 || (i + j) % 2 == 0) {
                    System.out.print(SET_BG_COLOR_BLACK);
                } else {
                    System.out.print(SET_BG_COLOR_WHITE);
                }
                if (piece != null) {
                    if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                        System.out.print(SET_TEXT_COLOR_RED);
                    } else {
                        System.out.print(SET_TEXT_COLOR_BLUE);
                    }
                    switch (piece.getPieceType()) {
                        case KING:
                            System.out.print(" K ");
                            break;
                        case QUEEN:
                            System.out.print(" Q ");
                            break;
                        case BISHOP:
                            System.out.print(" B ");
                            break;
                        case ROOK:
                            System.out.print(" R ");
                            break;
                        case KNIGHT:
                            System.out.print(" N ");
                            break;
                        case PAWN:
                            System.out.print(" P ");
                            break;
                    }
                } else {
                    System.out.print("   ");
                }
                if (j == 0) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(SET_TEXT_COLOR_BLACK);
                    System.out.print(" " + (i + 1) + " ");
                    System.out.print(RESET_BG_COLOR);
                }
            }
            System.out.printf("%n");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_COLOR_BLACK);
        System.out.print("   ");
        System.out.print(" a ");
        System.out.print(" b ");
        System.out.print(" c ");
        System.out.print(" d ");
        System.out.print(" e ");
        System.out.print(" f ");
        System.out.print(" g ");
        System.out.print(" h ");
        System.out.print("   ");
        System.out.print(RESET_BG_COLOR);
        System.out.print(RESET_TEXT_COLOR);
        System.out.printf("%n");
    }
}
