package top.laumgjyu.structure;

/**
 * Created by lmy on 2018/4/13.
 */
public enum StackCode {
    TABLE_STACK_1(0),
    TABLE_STACK_2(1),
    TABLE_STACK_3(2),
    TABLE_STACK_4(3),
    TABLE_STACK_5(4),
    TABLE_STACK_6(5),
    TABLE_STACK_7(6),

    COMPLETE_STACK_1(-1),
    COMPLETE_STACK_2(-2),
    COMPLETE_STACK_3(-3),
    COMPLETE_STACK_4(-4),

    DESK_STACK(-5),
    DESK_REST_STACK(-6);

    private int code;

    StackCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StackCode getStackCode(int i) {
        for (StackCode stackCode :
                StackCode.values()) {
            if (stackCode.getCode() == i) {
                return stackCode;
            }
        }
        return null;
    }
}
