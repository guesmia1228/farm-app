package com.farmdigital.nerddevs.Filters;

import com.farmdigital.nerddevs.Exceptions.NoJWtException;
import com.farmdigital.nerddevs.security.JwtServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtServices jwtServices;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final Map<String, Object> errorMessage = new HashMap<>();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {

            String authheader = request.getHeader("Authorization");

            String username;
            String token;
            LOGGER.info(request.getRequestURI());

//            todo handle errors for only apis which are not whitelisted
            if (authheader == null || !authheader.startsWith("Bearer")) {
//                LOGGER.info("no json token was provided");

                filterChain.doFilter(request, response);
                return;

            }
            token = authheader.substring(7);


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
                filterChain.doFilter(request, response);
            } else {
                filterChain.doFilter(request, response);
            }


        } catch (Exception ex) {
            if (ex instanceof NoJWtException) {
                Map<String, Object> erroMessage = setErrorResponse("no token provided in your request headers" +
                                " , a valid token is required", HttpStatus.FORBIDDEN.toString()
                        , "UNAUTHORIZED_REQUEST");
                customServlet(response, erroMessage);
                LOGGER.info(ex.getMessage());

            }

            if (ex instanceof SignatureException) {
                var errorMessage = setErrorResponse(ex.getMessage(), String.valueOf(HttpStatus.FORBIDDEN), "UNAUTHORIZED_REQUEST");
                customServlet(response, errorMessage);
            }

            if (ex instanceof MalformedJwtException) {
                var errorMessage = setErrorResponse(ex.getMessage(), String.valueOf(HttpStatus.FORBIDDEN), "UNAUTHORIZED_REQUEST");
                customServlet(response, errorMessage);
            }
            if (ex instanceof ExpiredJwtException) {
                var errorMessage = setErrorResponse(ex.getMessage(), String.valueOf(HttpStatus.FORBIDDEN), "UNAUTHORIZED_REQUEST");
                customServlet(response, errorMessage);
            }

        }
    }
//    ! function to create a custom servlet and error message

    public Map<String, Object> setErrorResponse(String message, String statusCode, String errorType) {

        errorMessage.put("errorMessage", message);
        errorMessage.put("statusCode", statusCode);
        errorMessage.put("errorType", errorType);
        return errorMessage;
    }

    private void customServlet(HttpServletResponse response, Map<String, Object> error) throws IOException {
        response.setContentType("application/json");
        response.setStatus(403);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
