package com.emirhankarakoc.Authorization.jwt;

import com.emirhankarakoc.Authorization.exceptions.general.BadRequestException;
import com.emirhankarakoc.Authorization.users.User;
import com.emirhankarakoc.Authorization.users.UserRepository;
import com.emirhankarakoc.Authorization.users.UserService;
import com.sun.source.tree.TryTree;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class JWTService {



    public JWTService(CustomConfigService jwtSettingsService, UserRepository repository) {
        this.jwtSettingsService = jwtSettingsService;
        this.repository = repository;
    }
    private final CustomConfigService jwtSettingsService;
    private final UserRepository repository;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("emirhan","karakoc");
        claims.put("denizli","2024");
        return createToken(claims, username);
    }

    public void validateToken(String token) {
        try{
            Date expiration = extractExpiration(token);
            if (expiration.before(new Date())){
                throw new BadRequestException("Token is expired.");
            }
            isUsernameExists(extractUsername(token));
        }
        catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private Date extractExpiration(String token) {
      try {
          Claims claims = Jwts
                  .parserBuilder()
                  .setSigningKey(getSignKey())
                  .build()
                  .parseClaimsJws(token)
                  .getBody();
          return claims.getExpiration();
      }
      catch (ExpiredJwtException e){
          //hadi ya? gercekten de gecmis mi... tuhhh.

          return new Date(System.currentTimeMillis()-1);
      }
    }
    public String extractUsername(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private String createToken(Map<String, Object> claims,String userName) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(jwtSettingsService.getExpirationDate()))// ctrl + left click to variable for change
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSettingsService.getSecretKey()); //ctrl + left click to variable for change
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private void isUsernameExists(String username) {
        if(repository.existsByUsername(username).isEmpty()){
            throw new BadRequestException("Invalid username");
        }
    }

}
