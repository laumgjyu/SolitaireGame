package top.laumgjyu;

import top.laumgjyu.gui.Table;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lmy on 2018/4/9.
 */
public class Starter extends JFrame {

    private final int WINDOW_WIDTH = 1320;

    private final int WINDOW_HEIGHT = 1000;

    private Starter() {
        this.setTitle("Solitaire Game");
        this.setContentPane(new Table());
        this.setResizable(false);
        this.setVisible(true);
        this.setAutoRequestFocus(true);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);    // 最大化
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
        设置启动时的位置
         */
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - WINDOW_WIDTH) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - WINDOW_HEIGHT) / 2;
        this.setLocation(w, h - 20);
    }

    public static void main(String[] args) {
        new Starter();
    }
}
