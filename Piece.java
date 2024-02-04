import java.util.LinkedList;

public class Piece {
    int xp;                // Initial X position on the board (column)
    int yp;                // Initial Y position on the board (row)
    int x;                 // Current X position for drawing
    int y;                 // Current Y position for drawing
    boolean isWhite;       // Is the piece white or not
    LinkedList<Piece> ps;  // List of all chess pieces on the board
    String name;           // Type of the chess piece

    boolean hasMoved = false;  // Indicates whether the piece has moved from its initial position

    public Piece(int xp, int yp, boolean isWhite, String n, LinkedList<Piece> ps) {
        this.xp = xp;
        this.yp = yp;
        x = xp * 64;
        y = yp * 64;
        this.isWhite = isWhite;
        this.ps = ps;
        name = n;
        ps.add(this);
    }

    public void move(int xp, int yp) {
        // Method to move the chess piece to a new position on the board

        // Check if the destination square is occupied by a piece of the same color
        Piece destinationPiece = Board.getPiece(xp * 64, yp * 64);
        if (destinationPiece != null && destinationPiece.isWhite == isWhite) {
            x = this.xp * 64;
            y = this.yp * 64;
            return;
        }

        // Specific movement checks for different chess pieces
        if (name.equalsIgnoreCase("pawn")) {
            int deltaY = yp - this.yp;
            
            if (!hasMoved && deltaY == 2 && !isWhite) {
                hasMoved = true;
            } else if (!hasMoved && deltaY == -2 && isWhite) {
                hasMoved = true;
            } else if (deltaY == 1 && !isWhite) {
                hasMoved = true;
            } else if (deltaY == -1 && isWhite) {
                hasMoved = true;
            } else {
                x = this.xp * 64;
                y = this.yp * 64;
                return;
            }
        }

        if (name.equalsIgnoreCase("knight")) {
            int deltaY = Math.abs(yp - this.yp);
            int deltaX = Math.abs(xp - this.xp);

            if ((deltaY == 2 && deltaX == 1) || (deltaY == 1 && deltaX == 2)){
                hasMoved = true;
            }
            else {
                x = this.xp * 64;
                y = this.yp * 64;
                return;
            }
        }
        
        if (name.equalsIgnoreCase("rook")) {
            int deltaY = Math.abs(yp - this.yp);
            int deltaX = Math.abs(xp - this.xp);
        
            if (deltaX > 0 && deltaY == 0 || deltaY > 0 && deltaX == 0) {
                hasMoved = true;
            } else {
                x = this.xp * 64;
                y = this.yp * 64;
                return;
            }
        }
        
        if (name.equalsIgnoreCase("bishop")){
            int deltaY = Math.abs(yp - this.yp);
            int deltaX = Math.abs(xp - this.xp);

            if (deltaY == deltaX){
                hasMoved = true;
            }
            else {
                // Movimiento no válido
                x = this.xp * 64;
                y = this.yp * 64;
                return;
            }
        }

        if (name.equalsIgnoreCase("queen")){
            int deltaY = Math.abs(yp - this.yp);
            int deltaX = Math.abs(xp - this.xp);

            if (deltaX == deltaY || deltaX > 0 && deltaY == 0 || deltaY > 0 && deltaX == 0){
                hasMoved = true;
            }
            else {
                x = this.xp * 64;
                y = this.yp * 64;
                return;
            }
        }

        if (name.equalsIgnoreCase("king")) {
            int deltaY = Math.abs(yp - this.yp);
            int deltaX = Math.abs(xp - this.xp);
        
            if ((deltaX == 1 && deltaY == 0) || (deltaX == 0 && deltaY == 1) || (deltaX == 1 && deltaY == 1)) {
                hasMoved = true;
            } else {
                x = this.xp * 64;
                y = this.yp * 64;
                return;
            }
        }

        // Check if the destination square is occupied by an opponent's piece
        if (destinationPiece != null && destinationPiece.isWhite != isWhite) {
            destinationPiece.kill();
        }

        // Update the position of the piece
        this.xp = xp;
        this.yp = yp;
        x = xp * 64;
        y = yp * 64;
    }

    public void kill() {
        // Remove the piece from the list when it is captured
        ps.remove(this);
    }
}
