package top.laumgjyu.structure;

import top.laumgjyu.consts.PokerType;
import top.laumgjyu.gui.Poker;
import top.laumgjyu.util.ImageUtil;

import static top.laumgjyu.gui.Poker.POKER_HEIGHT;
import static top.laumgjyu.gui.Poker.POKER_WIDTH;

/**
 * Created by lmy on 2018/4/11.
 */
public class Storage {
    public static final int SPACE = 40;  //同一牌堆翻开的牌之间的间距
    public static PokerStack[] tableStack = new PokerStack[7];  //桌面的七个牌堆
    public static PokerStack[] completeStack = new PokerStack[4]; //右上角的四个牌堆
    public static PokerStack<Poker> deskStack = new PokerStack<>();  //取牌的牌堆

    public static boolean put(int newStackIndex, Poker poker) {
        //put poker into this stack
        PokerStack oldStack;
        if (poker.getStack() < 0) {
            oldStack = deskStack;
        } else {
            oldStack = tableStack[poker.getStack()];
        }

        PokerStack newStack = tableStack[newStackIndex];

        int newStackSize = newStack.size();

        Poker newStackFront = (Poker) newStack.get(newStackSize - 1);

        PokerType newStackFrontType = newStackFront.getType();
        int newStackFrontNumber = newStackFront.getNumber().getValue();

        //return if current poker's color equals the new stack front poker's color
        if (poker.getType().getColor() == newStackFrontType.getColor()) return false;

        //return if current poker's number not equals the new stack front poker's number minus 1
        if (poker.getNumber().getValue() != newStackFrontNumber - 1) return false;

        putOnNewStack(newStack, oldStack, poker);

        turnOldStack(oldStack, poker);

        return true;
    }

    private static void putOnNewStack(PokerStack newStack, PokerStack oldStack, Poker poker) {
        int pokerToAddIndex = oldStack.indexOf(poker);

        Poker pokerToAdd = null;
        for (int i = pokerToAddIndex; i < oldStack.size(); i++) {
            pokerToAdd = (Poker) oldStack.get(i);

            newStack.add(newStack.size(), pokerToAdd);

            pokerToAdd.setStack(newStack.getStackIndex());

            newStack.setY(newStack.getY() + SPACE);
            pokerToAdd.setLocation(newStack.getX(), newStack.getY());
        }

    }

    private static void turnOldStack(PokerStack oldStack, Poker poker) {
        int pokerToDeleteIndex = oldStack.indexOf(poker);

        /*
        移除移动之后的旧的堆栈里的纸牌
         */
        while (pokerToDeleteIndex < oldStack.size()) {
            oldStack.remove(pokerToDeleteIndex);
        }

        /*
        如果移除之后牌堆为空，直接返回
         */
        if (oldStack.size() <= 0) return;

        /*
        如果oldStack为tableStack，将移除后的堆栈的最后一张纸牌显示出来
         */
        Poker front = (Poker) oldStack.get(oldStack.size() - 1);
        if (oldStack.getX() != 0 && oldStack.getY() != 0) {
            String type = front.getType().getValue();
            int number = front.getNumber().getValue();
            front.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, type, number));
            front.setFront(true);

            oldStack.setY(front.getLocation().y);
        } else {
            poker.getParent().add(front);
        }
    }
}
