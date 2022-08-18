


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Cai
 * @CreateTime: 2021/11/5
 */

public class PhotoDaoImpl implements PhotoDao {
    /**
     *
     * @param page
     * @param prePage
     * @param uid
     * @return 返回的是photo数据 就是增加信息的
     */
    @Override
    public ArrayList<Photo> getAllPhotos(int page,int prePage,String uid) {
        Connection conn = null;
        FileInputStream in = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Photo> arrs = new ArrayList<>();
        try {
            conn = DBUtil.getConn();
            String sql = "select * from photo"+" where uid = '" + uid +"'"+" order by id desc"+" limit " +page*prePage +","+prePage ;
            System.out.println(sql);
            ps = conn.prepareStatement(sql);

           rs = ps.executeQuery(sql);
            while (rs.next()) {
                Map<String, String> map = new HashMap();
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                String photourl = rs.getString(3);
                String thumurl = rs.getString(5);
                System.out.println("查询: " + id + " " + name + " " + photourl +"");
                Photo photo = new Photo(id,name,photourl,thumurl);
                arrs.add(photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrs;
    }

    @Override
    public Photo getPhotoById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        ResultSet rs = null;
        Integer thisId = null;
        String name = null;
        String photourl = null;
        String thumurl = null;
        try {
            conn = DBUtil.getConn();
            System.out.println("id:" + id);
            String sql = "select * from photo where id = " + id;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                thisId = rs.getInt(1);
                name = rs.getString(2);
                photourl = rs.getString(3);
                thumurl  = rs.getString(4);
                System.out.println(thisId);
                System.out.println(name);
                System.out.println(photourl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Photo(thisId, name, photourl,thumurl);
    }

    @Override
    public Integer photoCount() {
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        ResultSet rs = null;
        int ans = 0;
        try {
            conn = DBUtil.getConn();
            String sql = "select id,name from photo ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                ans++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ans;
    }

    @Override
    public void addPhoto(String name, String photourl,String uid,String thmurl) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConn();
            String sql = "insert into photo (id,name,photourl,uid,thumurl)values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, getMaxId() + 1);//数据库表我弄成自增的了
            ps.setString(2, name);
            ps.setString(3, photourl);
            ps.setString(4, uid);
            ps.setString(5, thmurl);
            int count = ps.executeUpdate();
            if (count > 0) {
                System.out.println("插入成功！");
            } else {
                System.out.println("插入失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Integer getMaxId() {
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        ResultSet rs = null;
        int ans = 0;
        try {
            conn = DBUtil.getConn();
            String sql = "select max(id) from photo";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                ans = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ans;
    }
}
