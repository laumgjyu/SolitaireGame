package top.laumgjyu.gui;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

/**
 * Created by lmy on 2018/4/11.
 */
@Setter
@Getter
public class Button extends JLabel{

    public static final int BUTTON_WIDTH = 180;  //按钮的宽度

    public static final int BUTTON_HEIGHT = 60;  //按钮的高度

    public static final int NEW_GAME = 0;

    public static final int UNDO = 1;

    private int type;  //button的类型， 0为newGame， 1为undo

}
