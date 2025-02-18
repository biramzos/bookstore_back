package com.example.bookstore_back.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService service;

    @Autowired
    private UserDetailServiceImplementation userDetailServiceImplementation;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        if(request.getHeader("Authorization") != null &&
                request.getHeader("Authorization").length() > 7 &&
                request.getHeader("Authorization").startsWith("Bearer ")){
            token = request.getHeader("Authorization").substring(7);
        } else {
            token = null;
        }
        try{
            if(token != null && service.validation(token)){
                String username = service.parseJwt(token);
                UserDetails userDetails = userDetailServiceImplementation.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            System.err.println("Error "+e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
