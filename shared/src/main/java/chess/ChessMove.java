package chess;

import java.util.Objects; /**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private ChessPosition beginPos;
    private ChessPosition endPos;
    private ChessPiece.PieceType promPiece;
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        beginPos = startPosition;
        endPos = endPosition;
        promPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return beginPos;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPos;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals(beginPos, chessMove.beginPos) && Objects.equals(endPos, chessMove.endPos) && promPiece == chessMove.promPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginPos, endPos, promPiece);
    }

    @Override
    public String toString() {
        return "ChessMove{" +
                "beginPos=" + beginPos +
                ", endPos=" + endPos +
                ", promPiece=" + promPiece +
                '}';
    }
}