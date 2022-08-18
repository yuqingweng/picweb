import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

import java.io.File;
import java.io.IOException;

public class ThunbnailatoeUtil {
    public static String getThunbnailatoePic(String url,String filename) throws IOException {

        String thunbnailUrl = url+"\\th\\";
        System.out.println(thunbnailUrl);
        File file = new File(thunbnailUrl);
        if (!file.exists()) {
            file.mkdirs();
        }
        Thumbnails.of(new File(url,filename))
                //设置缩略图大小，按等比缩放
                .size(300, 400)
                //.crop(Positions.CENTER)
                //将生成的缩略图写入文件
                .outputFormat("jpg")
                .toFile(new File(thunbnailUrl,filename));
        return thunbnailUrl;
    }
}
