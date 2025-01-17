package org.studymanager.main.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service for generating, validating, and parsing JWT tokens.
 * Provides functionality to create tokens, extract claims, and validate their authenticity.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@Service
public class JwtTokenService {

  @Value("${jwt.secret}")
  private String jwtTokenSecret;

  @Value("${jwt.token-expiration}")
  private long jwtTokenExpiration;

  /**
   * Extracts the email (subject) from the provided JWT token.
   *
   * @param token the JWT token
   * @return the email contained in the token
   */
  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts the expiration date from the provided JWT token.
   *
   * @param token the JWT token
   * @return the expiration date of the token
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Checks if the provided JWT token is expired.
   *
   * @param token the JWT token
   * @return true if the token is expired, false otherwise
   */
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts a specific claim from the provided JWT token using a claim resolver function.
   *
   * @param token         the JWT token
   * @param claimResolver the function to resolve the claim
   * @param <T>           the type of the claim
   * @return the resolved claim
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  /**
   * Extracts all claims from the provided JWT token.
   *
   * @param token the JWT token
   * @return the claims contained in the token
   */
  public Claims extractAllClaims(String token) {
    SecretKey secretKey = Keys.hmacShaKeyFor(jwtTokenSecret.getBytes(StandardCharsets.UTF_8));
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Generates a JWT token for the given user details.
   *
   * @param userDetails the user details
   * @return the generated JWT token
   */
  public String generateToken(UserDetails userDetails) {
    HashMap<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails);
  }

  /**
   * Creates a JWT token with the specified claims and user details.
   *
   * @param claims      the claims to include in the token
   * @param userDetails the user details
   * @return the created JWT token
   */
  public String createToken(Map<String, Object> claims, UserDetails userDetails) {
    SecretKey secretKey = Keys.hmacShaKeyFor(jwtTokenSecret.getBytes(StandardCharsets.UTF_8));

    return Jwts.builder()
            .subject(userDetails.getUsername())
            .claims(claims)
            .claim("authorities", userDetails.getAuthorities())
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + jwtTokenExpiration))
            .compact();
  }

  /**
   * Validates the provided JWT token against the given user details.
   * Checks if the token is not expired and if the username matches.
   *
   * @param token       the JWT token
   * @param userDetails the user details
   * @return true if the token is valid, false otherwise
   */
  public Boolean isTokenValid(String token, UserDetails userDetails) {
    String username = extractEmail(token);
    return (!isTokenExpired(token) && userDetails.getUsername().equals(username));
  }


}
