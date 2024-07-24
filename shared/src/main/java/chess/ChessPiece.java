package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor color;
    private ChessPiece.PieceType unitType;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        color = pieceColor;
        unitType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return unitType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (PieceType.BISHOP == board.getPiece(myPosition).unitType) {
            BishopMove posMoves = new BishopMove();
            return posMoves.checkMove(board, myPosition);
        }
        if (PieceType.KING == board.getPiece(myPosition).unitType) {
            KingMove posMoves = new KingMove();
            return posMoves.checkMove(board, myPosition);
        }
        if (PieceType.KNIGHT == board.getPiece(myPosition).unitType) {
            KnightMove posMoves = new KnightMove();
            return posMoves.checkMove(board, myPosition);
        }
        if (PieceType.PAWN == board.getPiece(myPosition).unitType) {
            PawnMove posMoves = new PawnMove();
            return posMoves.checkMove(board, myPosition);
        }
        if (PieceType.QUEEN == board.getPiece(myPosition).unitType) {
            QueenMove posMoves = new QueenMove();
            return posMoves.checkMove(board, myPosition);
        }
        if (PieceType.ROOK == board.getPiece(myPosition).unitType) {
            RookMove posMoves = new RookMove();
            return posMoves.checkMove(board, myPosition);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ChessPiece that = (ChessPiece) o;
        return color == that.color && unitType == that.unitType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, unitType);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "color=" + color +
                ", unitType=" + unitType +
                '}';
    }
}
