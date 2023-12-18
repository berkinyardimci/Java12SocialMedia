package com.socialmedia.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.socialmedia.excepiton.ErrorType;
import com.socialmedia.excepiton.ElasticManagerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JWTTokenManager {
    /*
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    private final Long expirationTime = (1000L * 60 * 5);


    public Optional<String> createToken(Long id) {
        String token = null;
        Date date = new Date(System.currentTimeMillis() + expirationTime);

        try {
            token = JWT.create()
                    .withClaim("id",id)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC512(secretKey));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(token);
    }

    public Optional<Long> getIdFromToken(String token) {

        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();


        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ElasticManagerException(ErrorType.INVALID_TOKEN);
        }
        Long id = decodedJWT.getClaim("id").asLong();
        return Optional.of(id);
    }

    public Optional<String> getRoleFromToken(String token){

        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();

        DecodedJWT decodedJWT = verifier.verify(token);
        if(decodedJWT == null){
            throw new ElasticManagerException(ErrorType.INVALID_TOKEN);
        }
        String role = decodedJWT.getClaim("role").asString();
        return Optional.of(role);
    }

     */

}
