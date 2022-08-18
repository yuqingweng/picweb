public interface UserDao {

    boolean addUser(String name, String password);
   User getUser(String name);
}
