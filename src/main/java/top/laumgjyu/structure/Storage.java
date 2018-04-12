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

    public static boolean put(int newStackIndex, int x, int y, Poker poker) {
        //put poker into this stack
        PokerStack oldStack = tableStack[poker.getStack()];
        PokerStack newStack = tableStack[newStackIndex];

        int newStackSize = newStack.size();

        Poker newStackFront = (Poker) newStack.get(newStackSize - 1);

        PokerType newStackFrontType = newStackFront.getType();
        int newStackFrontNumber = newStackFront.getNumber().getValue();

        //return if current poker's color equals the new stack front poker's color
        if (poker.getType().getColor() == newStackFrontType.getColor()) return false;

        //return if current poker's number not equals the new stack front poker's number minus 1
        if (poker.getNumber().getValue() != newStackFrontNumber - 1) return false;

        putOnNewStack(newStack, poker);
        poker.setStack(newStackIndex);

        turnOldStack(oldStack, poker);

        return true;
    }

    private static void turnOldStack(PokerStack oldStack, Poker poker) {
        oldStack.remove(poker);
        if (oldStack.size() > 0) {
            Poker front = (Poker) oldStack.get(oldStack.size() - 1);
            String type = poker.getType().getValue();
            int number = poker.getNumber().getValue();
            front.setIcon(ImageUtil.getImage(POKER_WIDTH, POKER_HEIGHT, type, number));
            front.setFront(true);
        }
    }

    private static void putOnNewStack(PokerStack newStack, Poker poker) {
        newStack.add(newStack.size(), poker);
        newStack.setY(newStack.getY() + SPACE);

        poker.setLocation(newStack.getX(), newStack.getY());
    }
}
