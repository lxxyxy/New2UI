package controller;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class winFrame extends JFrame {
    private int WIDTH;
    private int HEIGTH;
    public winFrame(int width, int height, PlayerColor playerColor){
        setTitle("胜利"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel ruleLabel1 = new JLabel(String.format("恭喜 %s 获得胜利",playerColor));
        ruleLabel1.setBounds(10, 10, 400, 20);
        add(ruleLabel1);
    }
}
