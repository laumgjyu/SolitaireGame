package top.laumgjyu.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by lmy on 2018/4/10.
 */
public class ImageUtil {
    private static final ClassLoader loader = ImageUtil.class.getClassLoader();

    /**
     * 根据指定的图片的宽高进行指定图片的缩放
     *
     * @param pokerWidth  最终图片的宽度
     * @param pokerHeight 最终图片的高度
     * @param imagesPath  源图片的路径
     * @return
     */
    public static ImageIcon getImage(int pokerWidth, int pokerHeight, String imagesPath) {
        /*
        首先判断资源存在不存在
         */
        URL path = loader.getResource(imagesPath);
        String finalPath = null;
        if (path == null) {
            //如果不存在就对资源路径重构
            finalPath = imagesPath.substring(0, imagesPath.length() - 14 - 1) + ".png";
        } else {
            //如果存在就使用当前路径
            finalPath = imagesPath;
        }


        ImageIcon image = new ImageIcon(loader.getResource(finalPath));
                /*
                将图片按照JLabel的大小进行缩放
                 */
        image.setImage(image.getImage().getScaledInstance(pokerWidth, pokerHeight, Image.SCALE_SMOOTH));
        return image;
    }

    /**
     * 根据传入的扑克的宽高进行缩放并获取图片
     *
     * @param pokerWidth  扑克的宽度
     * @param pokerHeight 扑克的高度
     * @param pokerType   扑克的花色
     * @param pokerNumber 扑克的数值
     * @return
     */
    public static ImageIcon getImage(int pokerWidth, int pokerHeight, String pokerType, int pokerNumber, boolean useHighlight) {
        String imagePath;
        if (useHighlight)
            imagePath = "images/" + pokerType + "_" + pokerNumber + "_highlight.png";
        else
            imagePath = "images/" + pokerType + "_" + pokerNumber + ".png";

        return getImage(pokerWidth, pokerHeight, imagePath);
    }
}
