package view;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ElephantChessComponent extends ChessComponent {
    public ElephantChessComponent(PlayerColor owner, int size) {
        super(owner,size);

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //大象图片
        if(owner.getColor().equals(Color.RED)) {
            Image elephantBlueImage = Toolkit.getDefaultToolkit().getImage("resource\\Elephant-red .png");
            g2.drawImage(elephantBlueImage, 0, 0, getWidth(), getHeight(), null);
        }else{
            Image elephantRedImage = Toolkit.getDefaultToolkit().getImage("resource\\Elephant-blue.png");
            g2.drawImage(elephantRedImage, 0, 0, getWidth(), getHeight(), null);
        }
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.YELLOW);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
        repaint();
    }
}
