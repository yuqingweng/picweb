


import java.util.ArrayList;
import java.util.Map;


public interface PhotoService {

    ArrayList<Photo> getAllPhotos(int page,int prePage,String uid);

    /**
     * 添加图片到数据库
     * @param name
     * @param photourl
     */
    void addPhoto(String name, String photourl,String uid,String thumurl);

    /**
     * 根据id查找图片
     * @param id
     * @return
     */
    Photo selectPhotoById(Integer id);
}

