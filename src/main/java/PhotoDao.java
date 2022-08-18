

import java.util.ArrayList;
import java.util.Map;



public interface PhotoDao {

    /**
     * 返回ArrayList<Map<String,String>>,方便转成json传到前端
     * @return
     */
    ArrayList<Photo> getAllPhotos(int page,int prePage,String uid);

    /**
     * 添加photo到数据库中
     * @param name
     * @param photourl
     */
    void addPhoto(String name, String photourl,String uid,String thumurl);

    /**
     * 获得最大的id
     * @return
     */
    Integer getMaxId();

    /**
     * 根据id查找图片
     * @param id
     * @return
     */
    Photo getPhotoById(Integer id);

    /**
     * 获得数据库中图片的数量
     * @return
     */
    Integer photoCount();
}


