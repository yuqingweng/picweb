import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(value = "/register")
public class RegisterServle extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset= utf-8");
        UserDaoImpl us = new UserDaoImpl();
        User user = new User();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Gson gson = new Gson();
        boolean resultData = us.addUser(name,password);
        String result = "";
        if(!resultData){
            Token token = new Token("用户名重复");
             result = gson.toJson(token);
        }else {
            Token token = new Token(TokenUtil.getToken(name));
            result = gson.toJson(token);
        }
        resp.getWriter().write(result);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
