package org.yugh.common.util.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.context.annotation.Configuration;
import org.yugh.common.exception.SessionException;

@Configuration
public class UserAuthUtil {


    public String generateToken(String no, String userName, String realName) {
        String token = JwtUtil.generateToken(new JWTInfo(no, userName, realName));
        return token;
    }

    public IJWTInfo getInfoFromToken(String token) {
        try {
            return JwtUtil.validateToken(token);
        } catch (ExpiredJwtException ex) {
            throw new SessionException("User token expired!");
        } catch (SignatureException ex) {
            throw new SessionException("User token signature error!");
        } catch (Exception ex) {
            throw new SessionException("用户token为空");
        }

    }
}
