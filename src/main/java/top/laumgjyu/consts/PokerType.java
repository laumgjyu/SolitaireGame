package top.laumgjyu.consts;

/**
 * Created by lmy on 2018/4/10.
 */
public enum PokerType {
    CLUB("Clubs"),  //梅花
    HEART("Hearts"),   //红桃
    DIAMOND("Diamonds"),   //方片
    SPADE("Spades"),  //黑桃
    HOLDER("Holder"),  //占位符
    HIGHLIGHT("Highlight");  //高亮框

    private String i;

    PokerType() {

    }

    PokerType(String i) {
        this.i = i;
    }

    public String getValue() {
        return i;
    }

    public Color getColor() {
        switch (this.getValue()) {
            case "Clubs":
            case "Spades":
                return Color.BLACK;
            case "Hearts":
            case "Diamonds":
                return Color.RED;
            default:
                return Color.UNKWON;
        }
    }

    public static PokerType getPokerType(int i) throws Exception {
        switch (i) {
            case 1:
                return CLUB;
            case 2:
                return HEART;
            case 3:
                return DIAMOND;
            case 4:
                return SPADE;
            default:
                throw new Exception("找不到索引对应的类型");
        }
    }
    }
