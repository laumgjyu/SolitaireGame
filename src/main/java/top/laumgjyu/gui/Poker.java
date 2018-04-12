package top.laumgjyu.gui;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.laumgjyu.consts.PokerNumber;
import top.laumgjyu.consts.PokerType;
import top.laumgjyu.structure.PokerStack;
import top.laumgjyu.structure.Storage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by lmy on 2018/4/9.
 */
@Getter
@Setter
@ToString
public class Poker extends JLabel implements MouseListener, MouseMotionListener {

    public static final int POKER_WIDTH = 160;  //纸牌的默认宽度
    public static final int POKER_HEIGHT = 225;  //纸牌的默认高度
    public static final int TABLEAU_HEIGHT = 112;  //下方七个牌堆空之后的占位框的高度

    private static final long serialVersionUID = -4413443648767942534L;
    private PokerType type;  //扑克的种类
    private PokerNumber number;  //扑克的数值
    private boolean front;  //是否正面朝上

    /*
    当前扑克属于哪个牌堆
    1-7分别表示下方七个牌堆
    0表示左上角四个牌堆
    -1 -2 -3 -4 -5分别表示上方从右到左的五个牌堆
     */
    private int stack;
    /*
        父类中已经有的属性：
        int x,y;
        int width,length;
        Icon icon;
         */
    private Point currentMousePoint;
    private Point currentPokerPoint;

    public Poker(PokerType type, PokerNumber number, boolean front) {
        this();
        this.type = type;
        this.number = number;
        this.front = front;
    }

    public Poker() {
        addMouseMotionListener(this);
        addMouseListener(this);
    }


    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public boolean getFront() {
        return front;
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        /*
        e.getX()和e.getY()获取事件发生位置与当前扑克label左上角的相对坐标
        这里先获取扑克的绝对坐标（即左上角坐标），然后计算事件发生位置的绝对坐标
         */
        Poker clickedPoker = (Poker) e.getSource();

        if (!clickedPoker.getFront()) return;

        int x = e.getX() + clickedPoker.getX();
        int y = e.getY() + clickedPoker.getY();

        if (!putHere(x, y)) {
            int stackIndex = clickedPoker.getStack();

            if (stackIndex < 0) {
                resetToDeskStack(clickedPoker);
            } else {
                resetToTableStack(clickedPoker, stackIndex);
            }
        }
    }

    private boolean putHere(int x, int y) {
        for (int i = 0; i < Storage.tableStack.length; i++) {
            if (Storage.tableStack[i].include(x, y)) {
                return Storage.put(i, this);
            }
        }
        return false;
    }

    private void resetToTableStack(Poker topPoker, int tableStackIndex) {
        PokerStack tableStack = Storage.tableStack[tableStackIndex];

        int topPokerIndex = tableStack.indexOf(topPoker);

        for (int i = topPokerIndex; i < tableStack.size(); i++) {
            ((Poker) tableStack.get(i)).setLocation(currentPokerPoint.x, currentPokerPoint.y + (i - topPokerIndex) * Storage.SPACE);
        }
    }

    private void resetToDeskStack(Poker clickedPoker) {
        clickedPoker.setLocation(currentPokerPoint.x, currentPokerPoint.y);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Poker currentPoker = (Poker) e.getSource();
        Container parentContainer = currentPoker.getParent();

        if (!currentPoker.getFront()) return;

        Point endPoint = e.getPoint();
        int x = endPoint.x - currentMousePoint.x;
        int y = endPoint.y - currentMousePoint.y;

        int stackIndex = currentPoker.getStack();
        if (stackIndex < 0) {
            moveDeskPoker(parentContainer, currentPoker, x, y);
        } else {
            moveTablePoker(parentContainer, currentPoker, stackIndex, x, y);
        }

    }

    private void moveDeskPoker(Container parentContainer, Poker currentPoker, int x, int y) {
        parentContainer.setComponentZOrder(currentPoker, 0);

        Point currentPokerLocation = currentPoker.getLocation();
        currentPoker.setLocation(currentPokerLocation.x + x, currentPokerLocation.y + y);
    }

    /*
    移动下方七个牌堆的纸牌
     */
    private void moveTablePoker(Container parentContainer, Poker currentPoker, int stackIndex, int x, int y) {
        PokerStack tableStack = Storage.tableStack[stackIndex];
        int currentPokerIndex = tableStack.indexOf(currentPoker);

        /*
        设置要移动的扑克牌的z index
         */
        for (int i = currentPokerIndex; i < tableStack.size(); i++) {
            Poker poker = (Poker) tableStack.get(i);
            parentContainer.setComponentZOrder(poker, 0);
        }

        /*
        为要移动的每个扑克牌设置位移
         */
        for (int i = currentPokerIndex; i < tableStack.size(); i++) {
            Poker poker = (Poker) tableStack.get(i);
            Point currentPokerLocation = poker.getLocation();
            poker.setLocation(currentPokerLocation.x + x, currentPokerLocation.y + y);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.currentMousePoint = e.getPoint();
        Poker poker = (Poker) e.getSource();
        this.currentPokerPoint = poker.getLocation();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //do nothing
    }
}
