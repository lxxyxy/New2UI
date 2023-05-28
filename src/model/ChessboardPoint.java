package model;

/**
 * This class represents positions on the checkerboard, such as (0, 0), (0, 7), and so on
 * Where, the upper left corner is (0, 0), the lower left corner is (7, 0), the upper right corner is (0, 7), and the lower right corner is (7, 7).
 */
public class
ChessboardPoint {
    private final int row;
    private final int col;

    public ChessboardPoint(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public ChessboardPoint getNeighbor(int direction) {
        int rowOffset = 0;
        int colOffset = 0;

        switch (direction) {
            case 0:  // 上方
                rowOffset = -1;
                break;
            case 1:  // 右方
                colOffset = 1;
                break;
            case 2:  // 下方
                rowOffset = 1;
                break;
            case 3:  // 左方
                colOffset = -1;
                break;
        }

        int neighborRow = row + rowOffset;
        int neighborCol = col + colOffset;

        return new ChessboardPoint(neighborRow, neighborCol);
    }

    @Override
    public int hashCode() {
        return row + col;
    }

    @Override
    @SuppressWarnings("ALL")
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ChessboardPoint temp = (ChessboardPoint) obj;
        return (temp.getRow() == this.row) && (temp.getCol() == this.col);
    }

    @Override
    public String toString() {
        return "("+row + ","+col+") " + "on the chessboard is clicked!";
    }
}
