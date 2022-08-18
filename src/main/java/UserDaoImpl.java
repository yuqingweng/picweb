import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
/*

插入失败需要返回一个量通知用户
 */

public class UserDaoImpl implements UserDao{
    @Override
    public boolean addUser(String name, String password) {
        Connection conn = null;
        FileInputStream in = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConn();
            String sql = "insert into user (name,password)values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,password);
            int count = ps.executeUpdate();
            if(count > 0){
                System.out.println("用户创建成功");
            }else {
                System.out.println("用户创建失败");
                return false;
            }
        } catch (Exception e) {
           Throwable cause = e.getCause();
            if (cause instanceof java.sql.SQLIntegrityConstraintViolationException) {
                return false;
            }
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
        return true;
    }

    @Override
    public User getUser(String name) {
        Connection conn = null;
        FileInputStream in = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = new User();
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM user WHERE name = '"+name+"'";
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if(rs.next()) {
                user.setName(name);
                user.setPassword(rs.getString("password"));
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
        return user;
    }

}
