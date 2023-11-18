package com.farmdigital.nerddevs.Filters;

import com.farmdigital.nerddevs.Exceptions.ExceptionController.ExceptionResponse;
import com.farmdigital.nerddevs.Exceptions.NoJWtException;
import com.farmdigital.nerddevs.security.JwtServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtServices jwtServices;
    private final UserDetailsService userDetailsService;
private  final ObjectMapper objectMapper;
private final ExceptionResponse exceptionResponse;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {


            String authheader = request.getHeader("Authorization");

            String username;
            String token;
            if (authheader == null || !authheader.startsWith("Bearer")) {
                LOGGER.info("no json token was provided");
                throw new NoJWtException("no authentication token was provided", 401);
            }
            token = authheader.substring(7);
            System.out.println(token);

            username = jwtServices.extractUsername(token);
//        ! search user from the databse
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//    ! validate the token
            if (jwtServices.isTokenValid(token, userDetails)) {
//    ! update the security context
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
           if( ex instanceof  NoJWtException){
               var erroMessage= exceptionResponse
                       .setErrorResponse("no token provided in your request headers" +
                               " , a valid token is required",HttpStatus.UNAUTHORIZED.toString(),"UNAUTHORIZED_REQUEST");
               LOGGER.info(ex.getMessage());
               response.setContentType("application/json");
               response.setStatus(401);
               response.getWriter().write(objectMapper.writeValueAsString(erroMessage));
           }

        }
    }
}
