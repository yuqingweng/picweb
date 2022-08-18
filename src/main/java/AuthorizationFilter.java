import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(filterName = "AuthorizationFilte",urlPatterns = {"*"})
public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType("application/json;charset= utf-8");
        System.out.println("拦截到了请求");
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Authorization");
        JSONObject jsonObject = new JSONObject();
        if (token != null) {
            try {
                String username = TokenUtil.verifyToken(token);
                if (username == null) {
                    jsonObject.put("err", "用户失效, 请重新登陆");
                    jsonObject.put("errcode", "998");
                    response.getWriter().print(jsonObject.toJSONString());
                } else {
                    // 验证成功
                    req.setAttribute("username", username);
                    //放行
                    chain.doFilter(req, response);
                }
            } catch (Exception e) {
                jsonObject.put("err", e.getMessage());
                jsonObject.put("errcode", "999");
                response.getWriter().print(jsonObject.toJSONString());
            }
        } else {
            String requestUri = ((HttpServletRequest) request).getRequestURI();
            if(requestUri.contains("/login")){
                System.out.println("登录界面放行");
                chain.doFilter(req,response);
            }else if(requestUri.contains("/register")){
                System.out.println("注册界面放行");
                chain.doFilter(req,response);
            }
            else if(requestUri.contains(".jpg") ){
                System.out.println("静态资源放行");
                chain.doFilter(req,response);
            }
            else {
                jsonObject.put("err", "尚未登录, 请登陆");
                jsonObject.put("errcode", "998");
                response.getWriter().print(jsonObject.toJSONString());
            }
        }


    }
}
