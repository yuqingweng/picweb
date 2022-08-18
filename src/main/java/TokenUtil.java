import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token工具包
 */
public class TokenUtil {

    public static final String secretKey = "abcdefghijklmnopqrstuvwxyz";
    //过期时间 2h小时
    private static final long EXPIRE_TIME = 1000L * 60 * 60 *24*30;
    //剩余15天的时候刷新
    private static final long REFRESH_TIME = 1000L * 60 * 60 *24*15 ;
    // 生成token
    public static String getToken(String user){
        HashMap<String,String> data = new HashMap<>();
        data.put("user",user);
        data.put("isAdmin","true");
        String token = getJWTWithHMAC256("login", data, secretKey);
        return token;
    }
    // 验证Token是否有用户名
    public static String verifyToken(String token) throws Exception {
        if (verifyTokenWithHMAC256(token,secretKey)){
            try{
                //提取数据
                DecodedJWT decode = JWT.decode(token);
                String userName = decode.getClaim("user").asString();
                // 获取过期时间
                Date date = decode.getExpiresAt();
                Date currDate = new Date(new Date().getTime());
                if ( currDate.after(date) ) {
                    return null;
                }else{
                    return userName;
                }
            }catch (JWTDecodeException e){
                throw new Exception("解析token失败");
            }
        }else {
            return null;
        }
    }

    public static String getJWTWithHMAC256(String subject, Map<String, String> payload, String secretKey){
        //指定JWT所使用的签名算法
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        //支持链式调用
        JWTCreator.Builder token = JWT.create()//创建token
                .withIssuer("com.wyq")//指定签发人
                .withSubject(subject)//指定主体数据
                .withExpiresAt(new Date(new Date().getTime()+EXPIRE_TIME));
        //添加负载数据
        for (String key: payload.keySet()) {
            token.withClaim(key,payload.get(key));
        }
        return token.sign(algorithm);
    }
    // 验证token是否有效
    public static boolean verifyTokenWithHMAC256(String token,String secretKey){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("com.wyq")
                    .build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e){
            return false;
        }
    }
    public static String refreshToken(String token) throws Exception {
        if (verifyTokenWithHMAC256(token,secretKey)){
            try{
                //提取数据
                DecodedJWT decode = JWT.decode(token);
                String userName = decode.getClaim("user").asString();
                // 获取过期时间
                Date date = decode.getExpiresAt();
                Date currDate = new Date(new Date().getTime()+REFRESH_TIME);
                if ( currDate.after(date) ) {
                    return getToken(userName);
                }else{
                    return null;
                }
            }catch (JWTDecodeException e){
                throw new Exception("解析token失败");
            }
        }else {
            return null;
        }
    }

}

