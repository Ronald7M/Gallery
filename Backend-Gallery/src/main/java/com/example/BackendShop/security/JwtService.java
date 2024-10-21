package com.example.BackendShop.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.BackendShop.dto.CredentialDTO;
import com.example.BackendShop.exception.InvalidToken;
import com.example.BackendShop.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final static  String ROLE="role";

    @Value("${security.jwtSecret}")
    private String jwtSecret;

    @Value("${security.jwtExpirationMs}")
    private int jwtExpirationMs;

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public void setJwtExpirationMs(int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }

    @Autowired
    private UserService userService;

    public String generateJwtToken(CredentialDTO credential,String role) {
        return generateTokenFromEmail(credential.getEmail(),role);
    }

    public String getSubject(String token){
        return decode(token).getSubject();
    }
    public String getRole(String token){
        return decode(token).getClaim(ROLE).asString();
    }



    public boolean validateJwtToken(String token) throws InvalidToken {
        try {
            SecretKey key = getSecretKey();
            Jwts.parser().verifyWith(key).build().parse(token);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            throw new InvalidToken("JWT was expired or incorrect");
        } catch (ExpiredJwtException e) {
            throw new InvalidToken("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new InvalidToken("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            throw new InvalidToken("JWT token compact of handler are invalid.");
        }catch (SignatureException e) {
            throw new InvalidToken("Invalid JWT signature");
        }
    }

    private String generateTokenFromEmail(String email, String role) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationMs);

        SecretKey key = getSecretKey();

        return Jwts.builder()
                .subject(email)
                .claim(ROLE, role)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private DecodedJWT decode(String token) {
        return JWT.decode(token);
    }

}
