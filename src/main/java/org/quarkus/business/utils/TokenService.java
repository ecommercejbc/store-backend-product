package org.quarkus.business.utils;

import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.jwt.Claims;
import org.jboss.logmanager.Logger;
import org.jose4j.jwt.JwtClaims;

import java.util.Arrays;
import java.util.UUID;

@RequestScoped
public class TokenService {

    public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());

    public String generateUserToken(User existingUser) {
        return generateToken(existingUser);
    }

    public String generateServiceToken(User existingUser) {
        return generateToken(existingUser);
    }

    public String generateToken(User existingUser) {
        try {
            JwtClaims jwtClaims = new JwtClaims();
            jwtClaims.setIssuer("DonauTech"); // change to your company
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setSubject(existingUser.email);
            jwtClaims.setClaim(Claims.upn.name(), existingUser.email);
            jwtClaims.setClaim(Claims.preferred_username.name(), existingUser.login); //add more
            jwtClaims.setClaim(Claims.groups.name(), Arrays.asList(existingUser.roles.toArray()));
            jwtClaims.setAudience("using-jwt");
            jwtClaims.setExpirationTimeMinutesInTheFuture(60); // TODO specify how long do you need


            String token = TokenUtils.generateTokenString(jwtClaims);
            LOGGER.info("TOKEN generated: " + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}