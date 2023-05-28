package view;

import javax.swing.*;

public class RuleFrame extends JFrame {
    private int WIDTH;
    private int HEIGTH;
    public RuleFrame(int width, int height) {
        setTitle("游戏规则"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel ruleLabel1 = new JLabel("1. 蓝方先手，进行回合制游戏。");
        ruleLabel1.setBounds(10, 10, 400, 20);
        add(ruleLabel1);

        JLabel ruleLabel2 = new JLabel("2. 每个玩家每回合控制一颗棋子只能移动一个格子，只有老鼠可以进");
        ruleLabel2.setBounds(10, 40, 800, 20);
        add(ruleLabel2);

        JLabel ruleLabel3 = new JLabel("  入河里，并且只有狮子和老虎可以跳过河流，但当老鼠在跳河路线");
        ruleLabel3.setBounds(10, 70, 800, 20);
        add(ruleLabel3);
        JLabel ruleLabel4 = new JLabel("  上时，则不可以跳过去。");
        ruleLabel4.setBounds(10, 100, 800, 20);
        add(ruleLabel4);
        JLabel ruleLabel5 = new JLabel("3. 等级排序：象>狮>虎>豹>狼>狗>猫>鼠，鼠可以吃象，同等级主");
        ruleLabel5.setBounds(10, 130, 800, 20);
        add(ruleLabel5);
        JLabel ruleLabel6 = new JLabel("  动捕食被动。");
        ruleLabel6.setBounds(10, 160, 800, 20);
        add(ruleLabel6);
        JLabel ruleLabel7 = new JLabel("4. 进入陷阱等级变为最低，任何棋子可吃。");
        ruleLabel7.setBounds(10, 190, 800, 20);
        add(ruleLabel7);
        JLabel ruleLabel8 = new JLabel("5. 一方棋子吃掉对方奶酪或对方所有棋子胜利。");
        ruleLabel8.setBounds(10, 220, 800, 20);
        add(ruleLabel8);
    }

}
