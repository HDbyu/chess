package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private int rowData;
    private int colData;
    public ChessPosition(int row, int col) {
        rowData = row ;
        colData = col ;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return rowData;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return colData;
    }

    public String getColumnLetter() {
        return switch (colData) {
            case 1 -> "a";
            case 2 -> "b";
            case 3 -> "c";
            case 4 -> "d";
            case 5 -> "e";
            case 6 -> "f";
            case 7 -> "g";
            case 8 -> "h";
            default -> null;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ChessPosition that = (ChessPosition) o;
        return rowData == that.rowData && colData == that.colData;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowData, colData);
    }

    @Override
    public String toString() {
        return "ChessPosition{" +
                "rowData=" + rowData +
                ", colData=" + colData +
                '}';
    }
}
