package top.laumgjyu.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lmy on 2018/4/10.
 */
public class ImageUtil {
    private static final ClassLoader loader = ImageUtil.class.getClassLoader();

    public static ImageIcon getImage(int pokerWidth, int pokerHeight,String imagesPath) {
        ImageIcon image = new ImageIcon(loader.getResource(imagesPath));
                /*
                将图片按照JLabel的大小进行缩放
                 */
        image.setImage(image.getImage().getScaledInstance(pokerWidth, pokerHeight, Image.SCALE_SMOOTH));
        return image;
    }

    public static ImageIcon getImage(int pokerWidth, int pokerHeight, String pokerType, int pokerNumber) {
        String imagePath = "images/" + pokerType + "_" + pokerNumber + ".png";
        return getImage(pokerWidth, pokerHeight, imagePath);
    }
}
