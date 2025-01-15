package ufpb.ayty.middleware;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import ufpb.ayty.service.JwtService;

public class AuthMiddleware implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        // Rotas públicas que não precisam de autenticação
        String path = ctx.path();
        if (path.equals("/login") || path.equals("/register") || path.equals("/")) {
            return; // Permite acesso sem autenticação
        }

        // Obtém o token do cookie
        String token = ctx.cookie("token");

        if (token == null || !JwtService.validateToken(token)) {
            // Se o token é inválido ou ausente, redireciona para a página de login
            ctx.redirect("/login");
        }
    }
}
