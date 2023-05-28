import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.StartFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame startFrame = new StartFrame(490, 750);
            startFrame.setVisible(true);


        });
    }
}
