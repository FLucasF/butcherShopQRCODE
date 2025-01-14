package ufpb.ayty.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Optional;

public class JwtService {

    private static final String SECRET_KEY = "your_secret_key";

    // Gerar token
    public static String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new java.util.Date())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 3600000)) // Expira em 1 hora
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    // Validar token e retornar o JWT decodificado
    private static Optional<DecodedJWT> validateAndDecodeToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            return Optional.of(decodedJWT);
        } catch (JWTVerificationException e) {
            return Optional.empty(); // Retorna vazio se o token for inválido
        }
    }

    // Validar token (forma simples)
    public static boolean validateToken(String token) {
        return validateAndDecodeToken(token).isPresent();
    }

    // Extrair e-mail do token
    public static Optional<String> getUserEmailFromToken(String token) {
        return validateAndDecodeToken(token)
                .map(DecodedJWT::getSubject); // Retorna o "subject" (e-mail) se o token for válido
    }

    // Extrair role do token
    public static Optional<String> getUserRoleFromToken(String token) {
        return validateAndDecodeToken(token)
                .map(jwt -> jwt.getClaim("role").asString()); // Retorna o "role" se o token for válido
    }
}
