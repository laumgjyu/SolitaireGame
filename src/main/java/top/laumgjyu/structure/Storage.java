package top.laumgjyu.structure;

import top.laumgjyu.consts.PokerNumber;
import top.laumgjyu.consts.PokerType;
import top.laumgjyu.gui.Poker;
import top.laumgjyu.util.ImageUtil;

import static top.laumgjyu.consts.CoordinateConst.UP_DOWN_STACKS_SPACE;
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
    public static PokerStack<Poker> deskRestStack = new PokerStack<>(); //取牌的剩余牌堆

    public static PokerStack getPokerStackByStackCode(StackCode stackCode) {
        switch (stackCode) {
            case TABLE_STACK_1:
                return tableStack[0];
            case TABLE_STACK_2:
                return tableStack[1];
            case TABLE_STACK_3:
                return tableStack[2];
            case TABLE_STACK_4:
                return tableStack[3];
            case TABLE_STACK_5:
                return tableStack[4];
            case TABLE_STACK_6:
                return tableStack[5];
            case TABLE_STACK_7:
                return tableStack[6];
            case COMPLETE_STACK_1:
                return completeStack[0];
            case COMPLETE_STACK_2:
                return completeStack[1];
            case COMPLETE_STACK_3:
                return completeStack[2];
            case COMPLETE_STACK_4:
                return completeStack[3];
            case DESK_STACK:
                return deskStack;
            case DESK_REST_STACK:
                return deskRestStack;
            default:
                return null;
        }
    }

    public static boolean put(StackCode newStackCode, Poker poker) {
        //put poker into this stack
        PokerStack oldStack = getPokerStackByStackCode(poker.getStackCode());

        PokerStack newStack = getPokerStackByStackCode(newStackCode);

        int newStackSize = newStack.size();

        /*
       如果stackCode小于零，这里就意味着右上角的四个牌堆
         */
        if (newStackCode.getCode() < 0 && newStackCode.getCode() >= -4) {
            if (!checkToCompleteStack(newStack, poker))
                return false;
        } else {

        /*
        如果新牌堆的容量不为0
         */
            if (newStackSize != 0) {
                if (!checkToTableStack(newStack, poker))
                    return false;
            }
        }

        putOnNewStack(newStack, oldStack, poker);

        turnOldStack(oldStack, poker);

        return true;
    }

    private static boolean checkToCompleteStack(PokerStack newStack, Poker pokerToPut) {

        int newStackSize = newStack.size();

        if (newStackSize == 0) {
            if (pokerToPut.getNumber() != PokerNumber.A) {
                return false;
            } else {
                return true;
            }
        } else {

            Poker newStackFront = (Poker) newStack.get(newStackSize - 1);

            PokerType newStackFrontType = newStackFront.getType();
            int newStackFrontNumber = newStackFront.getNumber().getValue();

            //return if current poker's color equals the new stack front poker's color
            if (pokerToPut.getType().getColor() != newStackFrontType.getColor()) return false;

            //return if current poker's number not equals the new stack front poker's number minus 1
            if (pokerToPut.getNumber().getValue() != newStackFrontNumber + 1) return false;

            PokerStack oldStack = getPokerStackByStackCode(pokerToPut.getStackCode());
            int pokerIndex = oldStack.indexOf(pokerToPut);
            if (pokerIndex != oldStack.size() - 1) return false;

            return true;
        }
    }

    private static boolean checkToTableStack(PokerStack newStack, Poker pokerToPut) {
        Poker newStackFront = (Poker) newStack.get(newStack.size() - 1);

        PokerType newStackFrontType = newStackFront.getType();
        int newStackFrontNumber = newStackFront.getNumber().getValue();

        //return if current poker's color equals the new stack front poker's color
        if (pokerToPut.getType().getColor() == newStackFrontType.getColor()) return false;

        //return if current poker's number not equals the new stack front poker's number minus 1
        if (pokerToPut.getNumber().getValue() != newStackFrontNumber - 1) return false;

        return true;
    }

    private static void putOnNewStack(PokerStack newStack, PokerStack oldStack, Poker poker) {
        int pokerToAddIndex = oldStack.indexOf(poker);

        Poker pokerToAdd = null;
        for (int i = pokerToAddIndex; i < oldStack.size(); i++) {
            pokerToAdd = (Poker) oldStack.get(i);

            if (newStack.size() != 0 && newStack.getStackCode().getCode() >= 0) {
                newStack.setY(newStack.getY() + SPACE);
            }

            newStack.add(newStack.size(), pokerToAdd);

            pokerToAdd.setStackCode(newStack.getStackCode());

            pokerToAdd.setLocation(newStack.getX(), newStack.getY());

            /*
            如果这种扑克是从deskStack牌堆上移动过来的，只需将当前的扑克放到tableStack上，当前扑克之后的扑克保持不动
             */
            if (oldStack.getStackCode()==StackCode.DESK_STACK) break;
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
        if (oldStack.size() <= 0) {
            oldStack.setY(POKER_HEIGHT + 2 * UP_DOWN_STACKS_SPACE);
            return;
        }

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
