package com.farmdigital.nerddevs.security;

import com.farmdigital.nerddevs.Exceptions.InvalidAuthenticationException;
import com.farmdigital.nerddevs.model.Farmer;
import com.farmdigital.nerddevs.repository.FarmerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServices {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtServices.class);
    private final FarmerRepository farmerRepository;
//    ! create a token

    public String generateAToken(
            Map<String, Object> extraClaim,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extraClaim)


                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 90000 * 60))

                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSignKey() {
        String SECRETE_KEY = "fae1b204e6e3d7f8a9c6b5d8c0a3f2e5b1d4e7f0a2e5c8b4a0d9e8c5f2a1b3e";
        byte[] keyBytes = Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    //  !  create token without claims
    public String generateAToken(UserDetails userDetails) {
        return generateAToken(new HashMap<>(), userDetails);

    }


//    ! extract the username from the token

    public <T> T extractSingleClaim(
            String token, Function<Claims, T> claimsResolver
    ) {
        final Claims claims = exctractAllClaims(token);
        return claimsResolver.apply(claims);

    }
//    ! extract all claims from the token


    private Claims exctractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

// !   extract username from the token

    public String extractUsername(String token) {
        return extractSingleClaim(token, Claims::getSubject);
    }
//    ! validate token

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);


        return (userName.equals(userDetails.getUsername()) && !TokenIsExpired(token));
    }

    //    ! check if the token is expired
    private boolean TokenIsExpired(String token) {
        return extractTokenExpiration(token).before(new Date());
    }


    //    !extract the expiry date from the token
    private Date extractTokenExpiration(String token) {
        return extractSingleClaim(token, Claims::getExpiration);
    }
//    ! check token expiry for a verification email




    public boolean CheckTokenExpiryForAccountVerification(String token,String  email) throws InvalidAuthenticationException {
//            extract the email from the token
//            ! validate if the email exist in our database
        Optional<Farmer> userExist = farmerRepository.findByEmail(email);
//   check if the token is expired
        boolean tokenIsexpired = TokenIsExpired(token);
//! if the token is valid and user exits
        if (!tokenIsexpired && userExist.isPresent()) {
            return true;
        } else {
            throw new InvalidAuthenticationException("invalid login details please try again");
        }


    }

}
