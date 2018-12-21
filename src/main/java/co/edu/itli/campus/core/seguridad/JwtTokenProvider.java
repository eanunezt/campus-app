package co.edu.itli.campus.core.seguridad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
/**
 * Created by rajeevkumarsingh on 19/08/17.
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);


    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        
        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put(IConstants.VAR_USER_ID, userPrincipal.getId());
        claims.put(IConstants.VAR_USER_PASS, userPrincipal.getPassword());
        claims.put(IConstants.VAR_AUTHOS, userPrincipal.getAuthorities().stream().map(auth-> auth.getAuthority()).collect(Collectors.toList())
        		/*roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).
        filter(Objects::nonNull).collect(Collectors.toList())*/);

        Date validity = new Date(now.getTime() + jwtExpirationInMs);//864_000_000;

        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, jwtSecret)//
            .compact();

      /*  return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();*/
    }
    
  /* public String createToken(String username, List<Rol> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, jwtSecret)//
            .compact();
      }*/

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.get(IConstants.VAR_USER_ID).toString());
    }
    
    public UserDetails getUserPrincipalFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        ArrayList<String> auths= (ArrayList<String>) claims.get(IConstants.VAR_AUTHOS);
        List<GrantedAuthority> authorities=auths
        		.stream()
        		.map(s -> new SimpleGrantedAuthority(s.toString()))
        		.collect(Collectors.toList());
        //List<GrantedAuthority> authorities=Arrays.asList(claims.get(VAR_AUTHOS));
        
       return  new UserPrincipal(
        		Long.parseLong(claims.get(IConstants.VAR_USER_ID).toString()),
               "",
               claims.getSubject(),
                "",
                claims.get(IConstants.VAR_USER_PASS).toString(),
                authorities                
        );
    }
    
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
      }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
    
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
          return bearerToken.substring(7, bearerToken.length());
        }
        return null;
      }
}