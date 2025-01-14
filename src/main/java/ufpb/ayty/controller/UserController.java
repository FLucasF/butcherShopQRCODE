package ufpb.ayty.controller;

import io.javalin.Javalin;
import ufpb.ayty.model.User;
import ufpb.ayty.service.UserService;

public class UserController {

    private final UserService userService = new UserService();

    public void registerRoutes(Javalin app) {
        app.post("/users/register", ctx -> {
            String name = ctx.formParam("name");
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            if (name == null || email == null || password == null) {
                ctx.status(400).result("Nome, email e senha são obrigatórios");
                return;
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            userService.register(user);
            ctx.status(201).result("Usuário registrado com sucesso");
        });
    }
}
