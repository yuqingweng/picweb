

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


public class JDBCServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo 存储到数据库里面
        PhotoDao photoDao = new PhotoDaoImpl();
        PhotoService photoService = new PhotoServiceImpl(photoDao);

        String filename = (String) req.getAttribute("filename");
        String filepath = (String) req.getAttribute("filepath");
        String fileurl = (String) req.getAttribute("fileurl");

       // photoService.addPhoto(filename, fileurl);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

