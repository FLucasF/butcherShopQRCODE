package ufpb.ayty;
import io.javalin.Javalin;
import ufpb.ayty.controller.CarneController;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public"); // Serve arquivos estáticos da pasta "public"
        }).start(8080);

        // Registra as rotas do controlador de carne
        new CarneController().registerRoutes(app);
    }
}
