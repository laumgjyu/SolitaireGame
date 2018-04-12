package test;

import org.junit.Test;
import top.laumgjyu.consts.PokerType;

import java.util.Random;

/**
 * Created by lmy on 2018/4/10.
 */
public class TestEnum {

    @Test
    public void enumTest() {
        PokerType type1 = PokerType.CLUB;
        PokerType type2 = PokerType.CLUB;
        System.out.println(type1.equals(type2));
        System.out.println(type1==type2);
    }

    @Test
    public void randomTest() {
        Random random = new Random();
        System.out.println(random.nextInt()%52);
    }
}
