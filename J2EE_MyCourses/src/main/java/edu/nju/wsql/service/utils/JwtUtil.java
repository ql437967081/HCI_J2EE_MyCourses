package edu.nju.wsql.service.utils;

import edu.nju.wsql.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.*;

public class JwtUtil {

    private static final SignatureAlgorithm HS256 = SignatureAlgorithm.HS256;
    private static final Key SECRET = MacProvider.generateKey(HS256);
    private static final byte[] SECRET_BYTES = SECRET.getEncoded();
    private static final String BASE_64_SECRET_BYTES = Base64.getEncoder().encodeToString(SECRET_BYTES);

    public static String createJwt(long ttlMillis, User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Map<String, Object> claims = new HashMap<>();
        String subject = user.getEmail();
        claims.put("id", user.getId());
        claims.put("password", user.getPassword());
        claims.put("email", subject);
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(HS256, BASE_64_SECRET_BYTES);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Claims parseJwt(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return Jwts.parser()
                .setSigningKey(BASE_64_SECRET_BYTES)
                .parseClaimsJws(token).getBody();
    }
}
