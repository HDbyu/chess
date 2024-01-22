package chess;

public class BishopMove extends ChessMove {
    public BishopMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        super(startPosition, endPosition, promotionPiece);
    }
    public Boolean CheckMove(ChessBoard board) {
        if (Math.abs(getStartPosition().getRow() - getEndPosition().getRow()) == Math.abs(getStartPosition().getColumn() - getEndPosition().getColumn())) {
            if (getEndPosition().getColumn() < getStartPosition().getColumn()) {
                if (getEndPosition().getRow() < getStartPosition().getRow()) {
                    for (int i = 0; i < getStartPosition().getRow() - getEndPosition().getRow(); i--) {

                    }
                }
            }
        }
        else return false;
    }
}
