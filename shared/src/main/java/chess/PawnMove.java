package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PawnMove {
    PawnMove() {

    }

    public Collection<ChessMove> checkMove(ChessBoard board, ChessPosition beginPos) {
        Set<ChessMove> moves = new HashSet<ChessMove>();
        if (board.getPiece(beginPos).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if ((beginPos.getRow() == 2)) {
                movesIterate(moves, beginPos, board, 1);
                if (board.getPiece(new ChessPosition(beginPos.getRow() + 1, beginPos.getColumn())) == null) {
                    movesIterate(moves, beginPos, board, 2);
                }
            }
            else {movesIterate(moves, beginPos, board, 1);}
        }
        if (board.getPiece(beginPos).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (beginPos.getRow() == 7) {
                movesIterate(moves, beginPos, board, -1);
                if (board.getPiece(new ChessPosition(beginPos.getRow() - 1, beginPos.getColumn())) == null) {
                    movesIterate(moves, beginPos, board, -2);
                }
            }
            else {movesIterate(moves, beginPos, board, -1);}
        }
        return moves;
    }

    private void movesIterate(Set<ChessMove> moves, ChessPosition first, ChessBoard board, int rowDir) {
        ChessPosition current;
        current = first;
        current = new ChessPosition(current.getRow() + rowDir, current.getColumn());
        if (current.getRow() >= 1 && current.getColumn() >= 1 && current.getRow() <= 8 && current.getColumn() <= 8) {
            if (board.getPiece(current) == null) {
                addCheck(moves, first, board, current);
            }
        }
        if (!(Math.abs(rowDir) == 2)) {
            current = new ChessPosition(current.getRow(), current.getColumn() - 1);
            check(moves, first, board, current);
            current = new ChessPosition(current.getRow(), current.getColumn() + 2);
            check(moves, first, board, current);
        }
    }

    private void check(Set<ChessMove> moves, ChessPosition first, ChessBoard board, ChessPosition current) {
        if (current.getRow() >= 1 && current.getColumn() >= 1 && current.getRow() <= 8 && current.getColumn() <= 8) {
            if (board.getPiece(current) != null && !board.getPiece(current).getTeamColor().equals(board.getPiece(first).getTeamColor())) {
                addCheck(moves, first, board, current);
            }
        }
    }

    private void addCheck(Set<ChessMove> moves, ChessPosition first, ChessBoard board, ChessPosition current) {
        if (current.getRow() == 1 || current.getRow() == 8) {
            moves.add(new ChessMove(first, current, ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(first, current, ChessPiece.PieceType.KNIGHT));
            moves.add(new ChessMove(first, current, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(first, current, ChessPiece.PieceType.ROOK));
        } else {
            moves.add(new ChessMove(first, current, null));
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
