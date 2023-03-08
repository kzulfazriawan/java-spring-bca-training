package com.springdemo.config;

import com.springdemo.securities.UserSecurityDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class JWTTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UserSecurityDetail userSecurityDetail;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader("Authorization");

        if (Objects.nonNull(headerAuth) && headerAuth.startsWith("Bearer ")) {
            String jwtToken = headerAuth.substring(7);
            if(!jwtToken.isEmpty() && !jwtToken.isBlank() && jwtTokenUtil.validateToken(jwtToken)) {
                String email = jwtTokenUtil.getDataFromToken(jwtToken);

                UserDetails userDetails = userSecurityDetail.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Cache-Control");

        filterChain.doFilter(request, response);
    }
}
