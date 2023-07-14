package com.natsukashiiz.iiboot.configuration.jwt;

import com.natsukashiiz.iiboot.configuration.jwt.model.Token;
import com.natsukashiiz.iiboot.configuration.jwt.model.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {
    private final JwtProperties jwtProperties;
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public JwtService(JwtProperties jwtProperties, JwtEncoder jwtEncoder, JwtDecoder decoder) {
        this.jwtProperties = jwtProperties;
        this.encoder = jwtEncoder;
        this.decoder = decoder;
    }

    public TokenResponse generateToken(Authentication authentication) {
        Token accessToken = generateAccessToken(authentication);
        Token refreshToken = generateRefreshToken(authentication);
        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessExpire(accessToken.getExpire())
                .refreshToken(refreshToken.getToken())
                .refreshExpire(refreshToken.getExpire())
                .build();
    }

    public Token generateAccessToken(Authentication authentication) {
        return generateToken(authentication, this.jwtProperties.getExpirationMs());
    }

    public Token generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, this.jwtProperties.getExpirationMs() * 2);
    }

    public Token generateToken(Authentication authentication, long expire) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(expire);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(this.jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(UUID.randomUUID().toString())
                .claim("uid", authentication.getUid())
                .claim("name", authentication.getName())
                .claim("email", authentication.getEmail())
                .claim("avatar", authentication.getAvatar())
                .build();
        return Token.builder()
                .token(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
                .expire(expiresAt.toEpochMilli())
                .build();
    }

    public boolean validate(String token) {
        try {
            this.decoder.decode(token);
            return true;
        } catch (JwtException e) {
            log.debug("TokenService-[validate](invalid). token: {}, error: {}", token, e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        Jwt decode = this.decoder.decode(token);
        return decode.getClaim("name");
    }
}
