package test;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by lmy on 2018/4/10.
 */
public class PictureProcessor {

    //Clubs Diamonds Hearts Spades
    private String[] types = {"Clubs", "Diamonds", "Hearts", "Spades"};

    @Test
    public void process() throws IOException {

        for (String type :
                types) {
            for (int i = 1; i <= 13; i++) {
                URL imagePath = this.getClass().getClassLoader().getResource("images/" + type + "_" + i + ".png");

                URL highlight = this.getClass().getClassLoader().getResource("images/Card_Highlight.png");

                pressImage(imagePath.getPath(), highlight.getPath(), "./" + type + "_" + i + "_highlight.png");
            }
        }
    }

    @Test
    public void cut() {
        for (String type :
                types) {
            for (int i = 1; i <= 13; i++) {
                URL imagePath = this.getClass().getClassLoader().getResource("images/" + type + "_" + i + "_highlight.png");
                cut(imagePath.getPath(), "./" + type + "_" + i + "_highlight.png", 18, 18, 214, 300);
            }
        }
    }

    @Test
    public void scale() throws IOException {
        String[] pictures = {"New_Game.png", "Undo.png"};

        for (String picture :pictures) {
            URL imagePath = this.getClass().getClassLoader().getResource("images/" + picture);
            Thumbnails.of(imagePath).scale(0.8).imageType(2).toFile("small_" + picture);
        }

        for (String picture :pictures) {
            URL imagePath = this.getClass().getClassLoader().getResource("images/" + picture);
            Thumbnails.of(imagePath).scale(1.2).imageType(2).toFile("large_" + picture);
        }
    }

    public final static void cut(String srcImageFile, String result,
                                 int x, int y, int width, int height) {
        try {
            // 读取源图像

            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
//                Image image = bi.getScaledInstance(srcWidth, srcHeight,
//                        Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(bi.getSource(),
                                cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图

                g.dispose();
                // 输出为文件

                ImageIO.write(tag, "PNG", new File(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressImage(String pressImg, String srcImageFile, String destImageFile) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null) - 13;
            int height_biao = src_biao.getHeight(null) - 13;
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, "PNG", new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
