package ufpb.ayty.controller;

import io.javalin.Javalin;
import ufpb.ayty.model.User;
import ufpb.ayty.service.JwtService;
import ufpb.ayty.service.UserService;

public class AuthController {
    private final UserService userService = new UserService();

    public void registerRoutes(Javalin app) {
        // Rota inicial
        app.get("/", ctx -> {
            String token = ctx.cookie("token");
            if (token != null && JwtService.validateToken(token)) {
                ctx.redirect("/carne/list");
            } else {
                ctx.redirect("/login");
            }
        });

        // Rota de login
        app.get("/login", ctx -> {
            ctx.render("login.html");
        });

        app.post("/login", ctx -> {
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            String token = userService.authenticate(email, password);
            if (token != null) {
                ctx.cookie("token", token);
                ctx.redirect("/carne/list");
            } else {
                ctx.status(401).result("Credenciais inválidas.");
            }
        });

        // Rota de registro
        app.get("/register", ctx -> {
            ctx.render("register.html");
        });

        app.post("/register", ctx -> {
            String name = ctx.formParam("name");
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");
            String confirmPassword = ctx.formParam("confirmPassword");

            if (!password.equals(confirmPassword)) {
                ctx.status(400).result("As senhas não coincidem.");
                return;
            }

            try {
                User newUser = new User(); // Criar o objeto User
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPassword(password);

                userService.register(newUser); // Passar o objeto User
                ctx.redirect("/login"); // Redireciona para a tela de login
            } catch (Exception e) {
                ctx.status(400).result(e.getMessage());
            }
        });

    }
}
