package chess;

import java.util.*;

public class BishopMove{
    public BishopMove() {

    }
    public Collection<ChessMove> CheckMove(ChessBoard board, ChessPosition beginPos) {
        Set<ChessMove> moves = new HashSet<ChessMove>();
        boolean proceed = true;
        ChessPosition current;
        current = beginPos;
        while (proceed) {
            if (current.getRow() > 0 && current.getColumn() > 0) {
                current = new ChessPosition(current.getRow() -1,current.getColumn() -1);
                if (board.getPiece(current) == null) {
                    moves.add(new ChessMove(beginPos,current,null));
                }
                else if (board.getPiece(current).getTeamColor() != board.getPiece(beginPos).getTeamColor()) {
                    proceed = false;
                    moves.add(new ChessMove(beginPos,current,null));
                }
                else {
                    proceed = false;
                }
            } else proceed = false;
        }
        proceed = true;
        current = beginPos;
        while (proceed) {
            if (current.getRow() > 0 && current.getColumn() < 7) {
                current = new ChessPosition(current.getRow() -1,current.getColumn() +1);
                if (board.getPiece(current) == null) {
                    moves.add(new ChessMove(beginPos,current,null));
                }
                else if (board.getPiece(current).getTeamColor() != board.getPiece(beginPos).getTeamColor()) {
                    proceed = false;
                    moves.add(new ChessMove(beginPos,current,null));
                }
                else {
                    proceed = false;
                }
            }
            else proceed = false;
        }
        proceed = true;
        current = beginPos;
        while (proceed) {
            if (current.getRow() < 7 && current.getColumn() > 0) {
                current = new ChessPosition(current.getRow() +1,current.getColumn() -1);
                if (board.getPiece(current) == null) {
                    moves.add(new ChessMove(beginPos,current,null));
                }
                else if (board.getPiece(current).getTeamColor() != board.getPiece(beginPos).getTeamColor()) {
                    proceed = false;
                    moves.add(new ChessMove(beginPos,current,null));
                }
                else {
                    proceed = false;
                }
            }
            else proceed = false;
        }
        proceed = true;
        current = beginPos;
        while (proceed) {
            if (current.getRow() < 7 && current.getColumn() < 7) {
                current = new ChessPosition(current.getRow() +1,current.getColumn() +1);
                if (board.getPiece(current) == null) {
                    moves.add(new ChessMove(beginPos,current,null));
                }
                else if (board.getPiece(current).getTeamColor() != board.getPiece(beginPos).getTeamColor()) {
                    proceed = false;
                    moves.add(new ChessMove(beginPos,current,null));
                }
                else {
                    proceed = false;
                }
            }
            else proceed = false;
        }
        return moves;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
