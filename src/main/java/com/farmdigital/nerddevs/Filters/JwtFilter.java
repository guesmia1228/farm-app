package com.farmdigital.nerddevs.Filters;

import com.farmdigital.nerddevs.security.JwtServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter  extends OncePerRequestFilter {
    private final JwtServices jwtServices;
   private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String  authheader=request.getHeader("Authorization");

         String username;
         String token;
        if(authheader==null||!authheader.startsWith("Bearer")){
           filterChain.doFilter(request,response);
           return;
        }
token=authheader.substring(7);
        System.out.println(token);

        username=jwtServices.extractUsername(token);
//        ! search user from the databse
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
//    ! validate the token
if(jwtServices.isTokenValid(token,userDetails))   {
//    ! update the security context
    UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
    );
    authenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
    );
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

}

filterChain.doFilter(request,response);
    }
}
