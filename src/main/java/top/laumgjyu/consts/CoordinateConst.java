package top.laumgjyu.consts;

/**
 * Created by lmy on 2018/4/12.
 */
public class CoordinateConst {
    public static final int HOLDER_X_X = 70;  //holderX纸牌的x坐标
    public static final int HOLDER_A1_X = 1090;
    public static final int HOLDER_A2_X = 920;
    public static final int HOLDER_A3_X = 750;
    public static final int HOLDER_A4_X = 580;

    public static final int[] HOLDER_A_X = {HOLDER_A4_X, HOLDER_A3_X, HOLDER_A2_X, HOLDER_A1_X};

    public static final int UP_DOWN_STACKS_SPACE = 25;  //上方的五个牌堆的y坐标, 上方五个牌堆与下方七个牌堆y轴上的间距
    public static final int TABLE_STACKS_SPACE = 170;  //下方七个牌堆之间的距离

    public static final int[] TABLE_STACKS_X =
            {HOLDER_X_X, HOLDER_X_X + TABLE_STACKS_SPACE,
                    HOLDER_X_X + 2 * TABLE_STACKS_SPACE, HOLDER_A4_X,
                    HOLDER_A3_X, HOLDER_A2_X, HOLDER_A1_X};  //下方七个牌堆的x坐标
}
