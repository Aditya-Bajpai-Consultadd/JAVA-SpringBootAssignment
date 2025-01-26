package com.javaAssignment.Task2.configs;

import com.javaAssignment.Task2.service.JwtService;
import com.javaAssignment.Task2.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.core.ApplicationContext;
import org.aspectj.apache.bcel.util.ClassPath;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private MyUserDetailsService service;
    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaders = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeaders!= null && authHeaders.startsWith("Bearer")){
            token = authHeaders.substring(7);
            username = jwtService.extractUserName(token);
        }

        if(username!= null && SecurityContextHolder.getContext().getAuthentication()== null){

         UserDetails userDetails = service.loadUserByUsername(username);

         if(jwtService.validateToken(token,userDetails)){
             UsernamePasswordAuthenticationToken authToken =
                     new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
             authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
             SecurityContextHolder.getContext().setAuthentication(authToken);
         }
        }
        filterChain.doFilter(request,response);
    }
}
