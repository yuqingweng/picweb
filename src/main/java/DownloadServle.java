



import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(value = "/download")
public class DownloadServle extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入下载");;
        resp.setContentType("application/json;charset= utf-8");
       String token = req.getHeader("Authorization");
        String username = (String) req.getAttribute("username");
        String refershToken = TokenUtil.refreshToken(req.getHeader("Authorization"));
        System.out.println(refershToken);
        System.out.println(token);
        int page = Integer.parseInt(req.getParameter("currentPage"));
        System.out.println(page);

        int perPage = Integer.parseInt(req.getParameter("perPage"));
        System.out.println(perPage);
        PhotoDao photoDao = new PhotoDaoImpl();
        PhotoService photoService = new PhotoServiceImpl(photoDao);
        ArrayList<Photo> items = photoService.getAllPhotos(page-1,perPage,username);

        JsonArray ja = new JsonArray();
        for(int i = 0;i < items.size();i++){
            JsonObject jsonObject = new Gson().toJsonTree(items.get(i)).getAsJsonObject();
          //  System.out.println(jsonObject);
            ja.add(jsonObject);
        }
        System.out.println(ja);
        JsonObject oj = new JsonObject();
        oj.add("items",ja);
        oj.addProperty("token",refershToken);
        System.out.println(oj);
        PrintWriter printWriter = null;

        try {
             printWriter = resp.getWriter();
            printWriter.write(oj.toString());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(printWriter != null){
                printWriter.close();
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doGet(req, resp);
    }
}
