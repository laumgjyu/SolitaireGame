package test;

import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by lmy on 2018/4/10.
 */
public class PictureProcessor {

    @Test
    public void process() throws IOException {
        String fileName = this.getClass().getClassLoader().getResource("images").getFile();
        File dir = new File(fileName);
        String[] files = dir.list();

        for (String file :
                files) {

            Thumbnails.of(fileName + "/" + file)
                    .size(71, 96)
                    .keepAspectRatio(false)
                    .outputFormat("png")
                    .outputQuality(1)
                    .toFile(fileName + "/" + file);

        }


    }
}
