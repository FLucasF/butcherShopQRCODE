package ufpb.ayty.middleware;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import ufpb.ayty.service.JwtService;

import java.util.Optional;

public class AuthMiddleware implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        // Obtém o token do cabeçalho Authorization
        String token = ctx.header("Authorization");

        // Verifica se o token está presente e possui o formato correto
        if (token == null || !token.startsWith("Bearer ")) {
            ctx.status(401).result("Token não encontrado ou inválido.");
            return; // Interrompe o pipeline
        }

        // Remove o prefixo "Bearer " do token
        token = token.replace("Bearer ", "");

        // Valida o token e extrai o email do usuário
        Optional<String> userEmail = JwtService.getUserEmailFromToken(token);

        if (userEmail.isEmpty()) {
            ctx.status(401).result("Token inválido ou expirado.");
            return; // Interrompe o pipeline
        }

        // Adiciona o email do usuário no contexto para uso posterior
        ctx.attribute("userEmail", userEmail.get());
    }
}
