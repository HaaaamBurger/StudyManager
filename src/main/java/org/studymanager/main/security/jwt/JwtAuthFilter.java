package org.studymanager.main.security.jwt;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.studymanager.main.security.config.CustomUserDetailsService;

/**
 * JwtAuthFilter is a custom implementation of {@link OncePerRequestFilter}
 * that processes each HTTP request
 * to validate the JSON Web Token (JWT) sent in the "Authorization" header.
 *
 * <p>This filter is responsible for:
 * <ul>
 *   <li>Extracting and validating the JWT token.</li>
 *   <li>Loading the corresponding user details.</li>
 *   <li>Setting the authentication context if the token is valid.</li>
 * </ul>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private static final String TOKEN_BEARER_NAME = "Bearer ";
  private static final String AUTHORIZATION_HEADER = "Authorization";

  public final JwtTokenService jwtTokenService;
  public final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {
    String header = request.getHeader(AUTHORIZATION_HEADER);
    if (header == null || !header.startsWith(TOKEN_BEARER_NAME)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = header.substring(TOKEN_BEARER_NAME.length());
    if (token != null) {
      try {
        jwtTokenService.extractExpiration(token);
      } catch (SignatureException | MalformedJwtException ex) {
        log.error("[JWT_EXCEPTION]: {}", ex.getMessage());
        filterChain.doFilter(request, response);
        return;
      }
      String userEmail = jwtTokenService.extractEmail(token);
      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails;
        try {
          userDetails = customUserDetailsService.loadUserByUsername(userEmail);
        } catch (UsernameNotFoundException ex) {
          filterChain.doFilter(request, response);
          return;
        }
        Boolean isTokenValid = jwtTokenService.isTokenValid(token, userDetails);
        if (isTokenValid) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
          );
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        log.info("[JWT]: Token validated successfully!");
      }
    }
    filterChain.doFilter(request, response);
  }
}
