package top.laumgjyu.gui;

import top.laumgjyu.consts.ImagePaths;
import top.laumgjyu.consts.PokerNumber;
import top.laumgjyu.consts.PokerType;
import top.laumgjyu.structure.PokerStack;
import top.laumgjyu.structure.Storage;
import top.laumgjyu.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;

import static top.laumgjyu.consts.CoordinateConst.*;
import static top.laumgjyu.gui.Button.BUTTON_HEIGHT;
import static top.laumgjyu.gui.Button.BUTTON_WIDTH;
import static top.laumgjyu.gui.Poker.*;

/**
 * Created by lmy on 2018/4/9.
 */
public class Table extends JPanel {
    private static final long serialVersionUID = 4337333559589329143L;

    private PokerStack[] tableStack;  //桌面的七个牌堆

    private PokerStack[] completeStack; //右上角的四个牌堆

    private PokerStack<Poker> deskRestStack;  //取牌的剩余牌堆

    private PokerStack<Poker> deskStack;   //取牌的牌堆

    private LinkedList<Poker> allPokers;  //所有扑克

    public Table() {
        this.setBackground(new Color(0x13670c));
        this.setLayout(null);

        start();
    }

    private void start() {
        initialContainers();
        initialPokers();
        initialPosition();
    }

    private void initialContainers() {
        tableStack = Storage.tableStack;
        completeStack = Storage.completeStack;
        deskStack = Storage.deskStack;
        deskRestStack = Storage.deskRestStack;
        allPokers = new LinkedList<>();

        for (int i = 0; i < 7; i++) {
            tableStack[i] = new PokerStack<Poker>();
            tableStack[i].setX(TABLE_STACKS_X[i]);
            tableStack[i].setY(POKER_HEIGHT + 2 * UP_DOWN_STACKS_SPACE);
            tableStack[i].setStackIndex(i);
        }

        for (int i = 0; i < 4; i++) {
            completeStack[i] = new PokerStack<Poker>();
            completeStack[i].setX(HOLDER_A_X[i]);
            completeStack[i].setY(UP_DOWN_STACKS_SPACE);
            completeStack[i].setStackIndex(-i - 1);
        }

                    /*
        清空之前的stack，以防止new game 按钮被点击时出错
         */
        for (int i = 0; i < 7; i++) {
            tableStack[i].clear();
        }
        for (int i = 0; i < 4; i++) {
            completeStack[i].clear();
        }
        deskRestStack.clear();
        deskStack.clear();

        /*
        由于在项目中不需要这deskStack的坐标，为了之后辨别其与其他的Stack，这里显示的将其初始化为0
         */
        deskRestStack.setX(0);
        deskRestStack.setY(0);
        deskRestStack.setStackIndex(-5);
    }

    private void initialPokers() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                Poker poker = null;

                try {
                    poker = new Poker(PokerType.getPokerType(i), PokerNumber.getPokerNumber(j), false);
                    poker.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, ImagePaths.BACK));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                allPokers.add(poker);
            }
        }

        Random random = new Random();
        for (int i = 0; i < 52; i++) {
            /*
            随机获取一个索引，交换当前索引和随机索引所在的扑克牌的位置
             */
            int index = random.nextInt(52);
            Poker temp = allPokers.get(index);
            allPokers.set(index, allPokers.get(i));
            allPokers.set(i, temp);
        }


        /*
        分别初始化桌面上七个牌堆的每个牌堆
         */
        Poker tmp = null;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < i + 1; j++) {
                tmp = allPokers.removeFirst();
                tmp.setStack(i);
                tableStack[i].push(tmp);
            }
        }


        /*
        初始化左上角牌堆
         */
        for (int i = 0; i < allPokers.size(); i++) {
            tmp = allPokers.get(i);
            tmp.setStack(-5);
            deskRestStack.push(tmp);
        }
        allPokers.clear();
    }

    private void initialPosition() {
        removeAll();  //清空Jpanel上的所有控件

        /*
        初始化各个控件
         */
        initialDeskStackPosition();
        initialHolderA();
        initialTableStacksPosition();
        initialButtons();

        repaint();
    }

    private void initialDeskStackPosition() {
        /*
        在界面上添加左上角牌堆的第一张图片
         */
        setHolderX();

        Poker deskRest = null;
        for (int i = 0; i < deskRestStack.size(); i++) {
            deskRest = deskRestStack.get(i);
            deskRest.setFront(false);
            deskRest.setBounds(HOLDER_X_X, UP_DOWN_STACKS_SPACE, POKER_WIDTH, POKER_HEIGHT);
//            deskRest.setBounds(HOLDER_X_X + TABLE_STACKS_SPACE, UP_DOWN_STACKS_SPACE, POKER_WIDTH, POKER_HEIGHT);
//            String deskFrontType = deskRest.getType().getValue();
//            int deskFrontNumber = deskRest.getNumber().getValue();
//            deskRest.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, deskFrontType, deskFrontNumber));
            deskRest.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, ImagePaths.BACK));
            add(deskRest, 0);
        }

        /*
        添加左上角牌堆剩余的图片
         */
//        Poker deskRest = new Poker();
//        deskRest.setFront(false);
//        deskRest.setType(PokerType.HOLDER);
//        deskRest.setNumber(PokerNumber.HOLDER);
//        deskRest.setBounds(HOLDER_X_X, UP_DOWN_STACKS_SPACE, POKER_WIDTH, POKER_HEIGHT);
//        deskRest.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, ImagePaths.BACK));
//        add(deskRest, 1);
    }

    private void initialHolderA() {
        for (int aHOLDER_A_X : HOLDER_A_X) {
            setHolderA(aHOLDER_A_X);
        }
    }

    private void setHolderX() {
        Poker holderX = new Poker();
        holderX.setFront(false);
        holderX.setType(PokerType.HOLDER);
        holderX.setNumber(PokerNumber.HOLDER);
        holderX.setBounds(HOLDER_X_X, UP_DOWN_STACKS_SPACE, POKER_WIDTH, POKER_HEIGHT);
        holderX.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, ImagePaths.HOLDER_X));
        add(holderX, 0);
    }

    private void setHolderA(int x) {
        Poker holderA = new Poker();
        holderA.setFront(false);
        holderA.setType(PokerType.HOLDER);
        holderA.setNumber(PokerNumber.HOLDER);
        holderA.setBounds(x, UP_DOWN_STACKS_SPACE, POKER_WIDTH, POKER_HEIGHT);
        holderA.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, ImagePaths.HOLDER_A));
        add(holderA);
    }

    private void initialTableStacksPosition() {
        String type;
        int number;

        Poker tablePokerHolder;
        for (int i = 0; i < 7; i++) {
            /*
            初始化下方七个牌堆的占位框
             */
            tablePokerHolder = new Poker();
            tablePokerHolder.setFront(false);
            tablePokerHolder.setType(PokerType.HOLDER);
            tablePokerHolder.setNumber(PokerNumber.HOLDER);
            tablePokerHolder.setIcon(ImageUtil.getImage(POKER_WIDTH, TABLEAU_HEIGHT, ImagePaths.TABLEAU));
            tablePokerHolder.setBounds(TABLE_STACKS_X[i], POKER_HEIGHT + 2 * UP_DOWN_STACKS_SPACE, POKER_WIDTH, TABLEAU_HEIGHT);
            add(tablePokerHolder);

            /*
            初始化下方的每个牌堆
             */
            for (int j = 0; j < tableStack[i].size(); j++) {
                Poker poker = (Poker) tableStack[i].get(j);
                if (j == tableStack[i].size() - 1) {
                    type = poker.getType().getValue();
                    number = poker.getNumber().getValue();
                    poker.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, type, number));
                    poker.setFront(true);

                    tableStack[i].setY(POKER_HEIGHT + 2 * UP_DOWN_STACKS_SPACE + j * 13);
                } else {
                    poker.setFront(false);
                    poker.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, ImagePaths.BACK));
                }
                poker.setBounds(TABLE_STACKS_X[i], POKER_HEIGHT + 2 * UP_DOWN_STACKS_SPACE + j * 13, POKER_WIDTH, POKER_HEIGHT);
                add(poker, 1);
            }
        }
    }

    private void initialButtons() {
        Button newGameButton = new Button();
        newGameButton.setType(Button.NEW_GAME);
        newGameButton.setIcon(ImageUtil.getImage(BUTTON_WIDTH, BUTTON_HEIGHT, ImagePaths.NEW_GAME));
        newGameButton.setBounds(450, 900, BUTTON_WIDTH, BUTTON_HEIGHT);
        newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                start();
            }
        });
        add(newGameButton);

        Button undoButton = new Button();
        undoButton.setType(Button.UNDO);
        undoButton.setIcon(ImageUtil.getImage(BUTTON_WIDTH, BUTTON_HEIGHT, ImagePaths.UNDO));
        undoButton.setBounds(700, 900, BUTTON_WIDTH, BUTTON_HEIGHT);
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO 添加返回事件
            }
        });
        add(undoButton);
    }

}
