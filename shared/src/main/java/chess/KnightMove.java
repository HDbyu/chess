package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class KnightMove {

    KnightMove() {

    }

    public Collection<ChessMove> CheckMove(ChessBoard board, ChessPosition beginPos) {
        Set<ChessMove> moves = new HashSet<ChessMove>();
        movesIterate(moves, beginPos, board, 2,-1);
        movesIterate(moves, beginPos, board, 2,1);
        movesIterate(moves, beginPos, board, -2,1);
        movesIterate(moves, beginPos, board, -2,-1);
        movesIterate(moves, beginPos, board, 1,2);
        movesIterate(moves, beginPos, board, -1,2);
        movesIterate(moves, beginPos, board, 1,-2);
        movesIterate(moves, beginPos, board, -1,-2);
        return moves;
    }

    private void movesIterate(Set<ChessMove> moves, ChessPosition first, ChessBoard board, int rowDir, int colDir) {
        ChessPosition current;
        current = first;
        current = new ChessPosition(current.getRow() + rowDir, current.getColumn() + colDir);
        if (current.getRow() >= 1 && current.getColumn() >= 1 && current.getRow() <= 8 && current.getColumn() <= 8) {
            if (board.getPiece(current) == null) {
                moves.add(new ChessMove(first, current, null));
            } else if (!board.getPiece(current).getTeamColor().equals(board.getPiece(first).getTeamColor())) {
                moves.add(new ChessMove(first, current, null));
            }
        }
    }
}
