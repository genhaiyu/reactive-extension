package org.yugh.common.util.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;
import org.yugh.common.exception.PermissionException;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static final String SECRET = "qazwsx123444$#%#()*&& asdaswwi1235 ?;!@#kmmmpom in***xx**&";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_AUTH = "x-authorization";

    public static String generateToken(IJWTInfo jwtInfo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("no", jwtInfo.getId());
        map.put("userName", jwtInfo.getUniqueName());
        map.put("realName", StringUtils.isEmpty(jwtInfo.getRealName()) ? "" : jwtInfo.getRealName());
        String jwt = Jwts.builder()
                .setSubject("user info").setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        String finalJwt = TOKEN_PREFIX + " " + jwt;
        return finalJwt;
    }

    public static IJWTInfo validateToken(String token) {
        if (token != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String no = (String) (body.get("no"));
            String userName = (String) (body.get("userName"));
            String realName = (String) (body.get("realName"));

            if (StringUtils.isEmpty(userName)) {
                throw new PermissionException("user is error, please check");
            }
            return new JWTInfo(no, userName, realName);
        } else {
            throw new PermissionException("token is error, please check");
        }
    }

    public static void main(String[] args) {

        String BearerToken = generateToken(new JWTInfo("377869","yugenhai","余根海"));

        IJWTInfo jwtInfo = validateToken(BearerToken);

        System.out.println(jwtInfo);
    }

}
