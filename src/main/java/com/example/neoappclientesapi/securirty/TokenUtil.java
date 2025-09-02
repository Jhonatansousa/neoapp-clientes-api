package com.example.neoappclientesapi.securirty;

import com.example.neoappclientesapi.dto.TokenDataDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class TokenUtil {

    public static final String ISSUER = "NeoApp_Clientes_API";
    public static final long EXPIRATION_TIME = 7*24*60*60*1000;
    public static final String SECRET_KEY = "0123456789012345678901234567890123456789";

    public static AuthToken encodeToken(TokenDataDTO userData) {
        try {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            String jwtToken = Jwts.builder()
                    .subject(userData.cpf())
                    .issuer(ISSUER)
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key)
                    .compact();

            return new AuthToken(jwtToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Authentication decodeToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null) {
                token = token.replace("Bearer ", "");
                SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
                Claims claims = (Claims) parser.parse(token).getPayload();

                String subject = claims.getSubject();
                String issuer = claims.getIssuer();
                Date exp = claims.getExpiration();

                if (issuer.equals(ISSUER) && !subject.isEmpty() && exp.after(new Date(System.currentTimeMillis()))) {
                    return new UsernamePasswordAuthenticationToken(subject, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
