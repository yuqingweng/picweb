



import java.util.ArrayList;
import java.util.Map;

/**
 * @Description: 图片业务实现类
 * @Author: Cai
 * @CreateTime: 2021/11/5
 */

public class PhotoServiceImpl implements PhotoService {
    private PhotoDao photoDao;

    public PhotoServiceImpl(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }


    @Override
    public ArrayList<Photo> getAllPhotos(int page,int prePage,String uid) {
        return photoDao.getAllPhotos(page,prePage,uid);
    }

    @Override
    public void addPhoto(String name, String photourl,String uid,String thumurl) {
        photoDao.addPhoto(name, photourl,uid,thumurl);
    }

    @Override
    public Photo selectPhotoById(Integer id) {
        return photoDao.getPhotoById(id);
    }
}


