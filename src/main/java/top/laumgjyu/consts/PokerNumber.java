package top.laumgjyu.consts;

/**
 * Created by lmy on 2018/4/10.
 */
public enum PokerNumber {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    J(11),
    Q(12),
    K(13),
    A(1),
    HOLDER(0);

    private int i;

    PokerNumber() {

    }

    PokerNumber(int i) {
        this.i = i;
    }

    public static PokerNumber getPokerNumber(int i) throws Exception {
        switch (i) {
            case 1:
                return A;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            case 6:
                return SIX;
            case 7:
                return SEVEN;
            case 8:
                return EIGHT;
            case 9:
                return NINE;
            case 10:
                return TEN;
            case 11:
                return J;
            case 12:
                return Q;
            case 13:
                return K;
            default:
                throw new Exception("找不到索引对应的数值");
        }
    }

    public int getValue() {
        return i;
    }
}
