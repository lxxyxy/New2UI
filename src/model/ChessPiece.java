package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (!this.getOwner().equals(target.getOwner())) {
            if (this.rank == 1 && target.rank == 8) {
                return true;
            } else if (this.rank == 8 && target.rank == 1) {
                return false;
            } else if (this.rank < target.rank) {
                return false;
            } else return true;
        } else return false;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
