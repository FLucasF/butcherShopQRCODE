package ufpb.ayty;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import ufpb.ayty.controller.CarneController;

public class App {

    public void start() {
        // Configurar o Thymeleaf
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Configurar o Javalin para usar Thymeleaf
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.directory = "/public";
                staticFileConfig.location = Location.CLASSPATH;
            });
            config.fileRenderer(new JavalinThymeleaf(templateEngine));
        }).start(7000);

        // Registrar rotas
        CarneController carneController = new CarneController();
        carneController.registerRoutes(app);
    }

    public static void main(String[] args) {
        new App().start();
    }
}
