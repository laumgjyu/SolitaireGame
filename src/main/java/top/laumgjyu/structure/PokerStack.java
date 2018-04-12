package top.laumgjyu.structure;

import lombok.Getter;
import lombok.Setter;
import top.laumgjyu.gui.Poker;

import java.util.Stack;

/**
 * Created by lmy on 2018/4/11.
 */
@Setter
@Getter
public class PokerStack<E> extends Stack<E> {

    private static final long serialVersionUID = 1937986750562222287L;
    private int x;  //扑克堆的横坐标
    private int y;  //扑克堆的纵坐标


    public boolean include(int x, int y) {

        return x >= this.x && x <= this.x + Poker.POKER_WIDTH
                &&
                y >= this.y && y <= this.y + Poker.POKER_HEIGHT;
    }
}
