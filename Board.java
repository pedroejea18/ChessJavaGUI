import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board {
    // List to store all the chess pieces
    public static LinkedList<Piece> ps = new LinkedList<>();
    // The currently selected chess piece
    public static Piece selectedPiece = null;

    public static void main(String[] args) throws IOException {
        // Load the image containing all chess piece images
        BufferedImage all = ImageIO.read(new File("chess.png"));
        // Array to store individual images for each chess piece
        Image imgs[] = new Image[12];
        int ind = 0;
        // Extract individual images from the combined image
        for (int y = 0; y < 400; y += 200) {
            for (int x = 0; x < 1200; x += 200) {
                imgs[ind] = all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }

        // Create chess pieces for the initial setup
        Piece brook = new Piece(0, 0, false, "rook", ps);
        Piece bknight = new Piece(1, 0, false, "knight", ps);
        Piece bbishop = new Piece(2, 0, false, "bishop", ps);
        Piece bqueen = new Piece(3, 0, false, "queen", ps);
        Piece bking = new Piece(4, 0, false, "king", ps);
        Piece bbishop2 = new Piece(5, 0, false, "bishop", ps);
        Piece bknight2 = new Piece(6, 0, false, "knight", ps);
        Piece brook2 = new Piece(7, 0, false, "rook", ps);
        Piece bpawn1 = new Piece(1, 1, false, "pawn", ps);
        Piece bpawn2 = new Piece(2, 1, false, "pawn", ps);
        Piece bpawn3 = new Piece(3, 1, false, "pawn", ps);
        Piece bpawn4 = new Piece(4, 1, false, "pawn", ps);
        Piece bpawn5 = new Piece(5, 1, false, "pawn", ps);
        Piece bpawn6 = new Piece(6, 1, false, "pawn", ps);
        Piece bpawn7 = new Piece(7, 1, false, "pawn", ps);
        Piece bpawn8 = new Piece(0, 1, false, "pawn", ps);

        Piece wrook = new Piece(0, 7, true, "rook", ps);
        Piece wkinght = new Piece(1, 7, true, "knight", ps);
        Piece wbishop = new Piece(2, 7, true, "bishop", ps);
        Piece wqueen = new Piece(3, 7, true, "queen", ps);
        Piece wking = new Piece(4, 7, true, "king", ps);
        Piece wbishop2 = new Piece(5, 7, true, "bishop", ps);
        Piece wkight2 = new Piece(6, 7, true, "knight", ps);
        Piece wrook2 = new Piece(7, 7, true, "rook", ps);
        Piece wpawn1 = new Piece(1, 6, true, "pawn", ps);
        Piece wpawn2 = new Piece(2, 6, true, "pawn", ps);
        Piece wpawn3 = new Piece(3, 6, true, "pawn", ps);
        Piece wpawn4 = new Piece(4, 6, true, "pawn", ps);
        Piece wpawn5 = new Piece(5, 6, true, "pawn", ps);
        Piece wpawn6 = new Piece(6, 6, true, "pawn", ps);
        Piece wpawn7 = new Piece(7, 6, true, "pawn", ps);
        Piece wpawn8 = new Piece(0, 6, true, "pawn", ps);

        // Create the main JFrame
        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        // Create a JPanel for drawing the chess board and pieces
        JPanel pn = new JPanel() {
            @Override
            public void paint(Graphics g) {
                // Draw the chess board
                boolean white = true;
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        if (white) {
                            g.setColor(new Color(235, 235, 208));
                        } else {
                            g.setColor(new Color(119, 148, 85));
                        }
                        g.fillRect(x * 64, y * 64, 64, 64);
                        white = !white;
                    }
                    white = !white;
                }

                // Draw the chess pieces on the board
                for (Piece p : ps) {
                    int ind = 0;
                    if (p.name.equalsIgnoreCase("king")) {
                        ind = 0;
                    }
                    if (p.name.equalsIgnoreCase("queen")) {
                        ind = 1;
                    }
                    if (p.name.equalsIgnoreCase("bishop")) {
                        ind = 2;
                    }
                    if (p.name.equalsIgnoreCase("knight")) {
                        ind = 3;
                    }
                    if (p.name.equalsIgnoreCase("rook")) {
                        ind = 4;
                    }
                    if (p.name.equalsIgnoreCase("pawn")) {
                        ind = 5;
                    }
                    if (!p.isWhite) {
                        ind += 6;
                    }
                    g.drawImage(imgs[ind], p.x, p.y, this);
                }
            }
        };

        // Add the JPanel to the JFrame
        frame.add(pn);

        // Add mouse motion and mouse click listeners for piece dragging and selection
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedPiece != null) {
                    selectedPiece.x = e.getX() - 32;
                    selectedPiece.y = e.getY() - 32;
                    frame.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedPiece = getPiece(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedPiece.move(e.getX() / 64, e.getY() / 64);
                frame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        // Set JFrame properties and make it visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Helper method to get the chess piece at a specific location
    public static Piece getPiece(int x, int y) {
        int xp = x / 64;
        int yp = y / 64;
        for (Piece p : ps) {
            if (p.xp == xp && p.yp == yp) {
                return p;
            }
        }
        return null;
    }
}
