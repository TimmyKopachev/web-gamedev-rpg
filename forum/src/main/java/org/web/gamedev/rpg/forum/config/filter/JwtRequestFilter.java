package org.web.gamedev.rpg.forum.config.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.web.gamedev.rpg.forum.config.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Service
public class JwtRequestFilter extends OncePerRequestFilter {
    private final static String BEARER = "Bearer ";
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService userDetailsService;

    @Autowired
    @Lazy
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;

        String jwt = null;

        if (authHeader != null && authHeader.startsWith(BEARER)) {
            jwt = authHeader.substring(BEARER.length());
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (Exception ex) {
                System.out.println("Token is invalid " + ex.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
