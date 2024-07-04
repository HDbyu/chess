package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor turn = TeamColor.WHITE;
    ChessBoard myBoard = new ChessBoard();
    ChessBoard safeBoard = new ChessBoard();

    public ChessGame() {
        myBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> moves = myBoard.getPiece(startPosition).pieceMoves(myBoard, startPosition);
        moves = ReduceMoves(moves);
        return moves;
    }

    private Collection<ChessMove> ReduceMoves(Collection<ChessMove> moves) {
        boolean changeMade = true;
        saveBoard();
        while (changeMade) {
            changeMade = false;
            for (ChessMove obj : moves) {
                recallBoard();
                myBoard.addPiece(obj.getEndPosition(), myBoard.getPiece(obj.getStartPosition()));
                myBoard.addPiece(obj.getStartPosition(), null);
                if (isInCheck(myBoard.getPiece(obj.getEndPosition()).getTeamColor())) {
                    moves.remove(obj);
                    changeMade = true;
                    break;
                }
            }
        }
        recallBoard();
        return moves;
    }

    private void saveBoard() {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (myBoard.getPiece(new ChessPosition(i,j)) != null) {
                    safeBoard.addPiece(new ChessPosition(i, j), myBoard.getPiece(new ChessPosition(i, j)));
                }
                else if (safeBoard.getPiece(new ChessPosition(i,j)) != null) {
                    safeBoard.addPiece(new ChessPosition(i,j), null);
                }
            }
        }
    }
    private void recallBoard() {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (safeBoard.getPiece(new ChessPosition(i,j)) != null) {
                    myBoard.addPiece(new ChessPosition(i, j), safeBoard.getPiece(new ChessPosition(i, j)));
                }
                else if (myBoard.getPiece(new ChessPosition(i,j)) != null) {
                    myBoard.addPiece(new ChessPosition(i,j), null);
                }
            }
        }
    }

    /**
     * Makes a move
     * in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        if (moves.contains(move) && myBoard.getPiece(move.getStartPosition()).getTeamColor() == turn) {
            myBoard.addPiece(move.getEndPosition(), myBoard.getPiece(move.getStartPosition()));
            myBoard.addPiece(move.getStartPosition(),null);
            turn = turn == TeamColor.WHITE? TeamColor.BLACK:TeamColor.WHITE;
            if (move.getPromotionPiece() != null) {
                myBoard.addPiece(move.getEndPosition(), new ChessPiece(myBoard.getPiece(move.getEndPosition()).getTeamColor(), move.getPromotionPiece()));
            }
        }
        else throw new InvalidMoveException();
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        int kingRow = 0;
        int kingCol = 0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (myBoard.getPiece(new ChessPosition(i,j)) != null &&
                    myBoard.getPiece(new ChessPosition(i,j)).getTeamColor() == teamColor &&
                    myBoard.getPiece(new ChessPosition(i,j)).getPieceType() == ChessPiece.PieceType.KING) {
                    kingRow = i;
                    kingCol = j;
                }
            }
        }
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (myBoard.getPiece(new ChessPosition(i,j)) != null &&
                        myBoard.getPiece(new ChessPosition(i,j)).getTeamColor() != teamColor) {
                    Collection<ChessMove> checkMoves = myBoard.getPiece(new ChessPosition(i,j)).pieceMoves(myBoard,new ChessPosition(i,j));
                    if (checkMoves.contains(new ChessMove(new ChessPosition(i,j), new ChessPosition(kingRow,kingCol), null)) ||
                            checkMoves.contains(new ChessMove(new ChessPosition(i,j), new ChessPosition(kingRow,kingCol), ChessPiece.PieceType.QUEEN)) ||
                            checkMoves.contains(new ChessMove(new ChessPosition(i,j), new ChessPosition(kingRow,kingCol), ChessPiece.PieceType.BISHOP)) ||
                            checkMoves.contains(new ChessMove(new ChessPosition(i,j), new ChessPosition(kingRow,kingCol), ChessPiece.PieceType.ROOK)) ||
                            checkMoves.contains(new ChessMove(new ChessPosition(i,j), new ChessPosition(kingRow,kingCol), ChessPiece.PieceType.KNIGHT))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && iteratePieces(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && iteratePieces(teamColor);
    }

    private boolean iteratePieces(TeamColor teamColor) {
        Collection<ChessMove> posMove = new HashSet<>();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (myBoard.getPiece(new ChessPosition(i,j)) != null &&
                        myBoard.getPiece(new ChessPosition(i,j)).getTeamColor() == teamColor) {
                    posMove.addAll(validMoves(new ChessPosition(i,j)));
                    if (!posMove.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return posMove.isEmpty();
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        myBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return myBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return turn == chessGame.turn && Objects.equals(myBoard, chessGame.myBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, myBoard);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "color=" + turn +
                ", myBoard=" + myBoard +
                '}';
    }
}
