

import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

@WebServlet(value = "/upload")
public class UploadServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.print("<html><body>");
        out.print("<h3>Hello Servlet</h3>");
        out.print("</body></html>");

         */

        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset= utf-8");

        //String token = req.getHeader("Authorization");
        String username = (String) req.getAttribute("username");
        //判断文件是否可以上传
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        //可以上传,有问题就报异常
        if (isMultipart) {
            //创建一个FileItem工厂，通过工厂创建文件上传核心组件ServletFileUpload对象
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            try {
                //通过核心上传组件解析request请求，获取表单的所有表单项，表单的每一个表单项对应一个FileItem
                List<FileItem> items = upload.parseRequest(req);
                Iterator<FileItem> it = items.iterator();
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        System.out.println("其他类型文件");
                    } else {
                        String filename = item.getName();
                        //  String path = req.getSession().getServletContext().getRealPath("/") + "\\1";
                        String path = "D:\\images\\" + username;
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        file = new File(path, filename);
                        //System.out.println(item.getSize()+"文件大小");
                        InputStream inputStream = item.getInputStream();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int length = 0;
                        //判断流是否已经读取完毕 -1是文件的尾部标识
                        try {
                            while ((length = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, length);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            inputStream.close();
                            fileOutputStream.close();
                            item.delete();
                        }
                        //关闭输入输出流，并删除临时文件
                       // item.write(file);
                      //  System.out.println(filename + "上传成功");
                        String thUrl = null;
                        try {
                             thUrl = ThunbnailatoeUtil.getThunbnailatoePic(path, filename);
                        }catch (IOException e){
                            e.printStackTrace();
                            Token token = new Token("error");
                            String result = new Gson().toJson(token);
                            resp.getWriter().write(result);
                            return;
                        }
                        System.out.println("存好了缩略图" + thUrl);
                        System.out.println(thUrl);
                        PhotoDao photoDao = new PhotoDaoImpl();
                        PhotoService photoService = new PhotoServiceImpl(photoDao);
                        photoService.addPhoto(filename, "/images/" + username + "/" + filename, username, "/images/" + username + "/th/" + filename);
                        Token token = new Token("succeed");
                        String result = new Gson().toJson(token);
                        resp.getWriter().write(result);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}

