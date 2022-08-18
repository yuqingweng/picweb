import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.mysql.cj.Session;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset= utf-8");
        UserDaoImpl us = new UserDaoImpl();
        User user = new User();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        user = us.getUser(name);
        Gson gson = new Gson();
        String result = "";
        if(user != null && password.equals(user.getPassword())){

            // 生成token
            System.out.println("登录成功");
            //String token = TokenUtil.getToken(user.getName());
            Token token = new Token(TokenUtil.getToken(user.getName()));

            System.out.println(token);
             result = gson.toJson(token);
            System.out.println(result);
           // resp.setHeader("token", token);
        }
        else {
            Token token = new Token("error");
             result = gson.toJson(token);
        }
        resp.getWriter().write(result);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}