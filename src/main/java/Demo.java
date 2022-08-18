import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        String url = "D:\\images\\wyqwyqwyq1\\IMG_20220109_143340_658.jpg";
        StringBuilder sb = new StringBuilder();
        String tmep = url.substring(0,url.lastIndexOf("\\")) +"\\t"+url.substring(url.lastIndexOf("\\"));

        System.out.println(tmep);
        Thumbnails.of(new File("D:\\images\\PXL_20220728_131011871.jpg"))
                //设置缩略图大小，按等比缩放
                .size(200, 200)
                //将生成的缩略图写入文件
                .toFile(new File("D:\\images\\thumbnail.jpg"));
    }
}
