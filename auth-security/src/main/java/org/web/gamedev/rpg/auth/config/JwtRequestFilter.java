package org.web.gamedev.rpg.auth.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.web.gamedev.rpg.auth.model.payload.CustomUserDetails;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {
  private static final String BEARER = "Bearer ";
  @Autowired private JwtTokenUtil jwtTokenUtil;

  private UserDetailsService userDetailsService;

  @Autowired
  @Lazy
  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String username = null;
    String token = getTokenFromRequest(request);
    if (token != null && jwtTokenUtil.validateToken(token)) {
      username = jwtTokenUtil.getUsernameFromToken(token);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      CustomUserDetails userDetails =
          (CustomUserDetails) userDetailsService.loadUserByUsername(username);
      if (jwtTokenUtil.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken upaToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(upaToken);
      }
    }
    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith(BEARER)) {
      return authHeader.substring(BEARER.length());
    }
    return null;
  }
}
