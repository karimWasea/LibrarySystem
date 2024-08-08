//package Librar.Security.UtilTiz;
//
//import io.jsonwebtoken.*;
//import org.springframework.beans.factory.annotation.Value;
//import Librar.Security.UtilTiz.JwtFilter;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    @Value("${jwt.secret}")
//    private String SECRET_KEY;
//
//    @Value("${jwt.expiration}")
//    private long JWT_EXPIRATION;
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    public boolean validateToken(String token, String username) {
//        final String jwtUsername = getUsernameFromToken(token);
//        return (jwtUsername.equals(username) && !isTokenExpired(token));
//    }
//
//    public String getUsernameFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    private boolean isTokenExpired(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration()
//                .before(new Date());
//    }
//}
