package ufpb.ayty;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

public class App {

    public static void main(String[] args) {
        // Configura o Template Engine do Thymeleaf
        TemplateEngine templateEngine = configureThymeleaf();

        // Configura o Javalin e define o Thymeleaf como renderizador de templates
        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinThymeleaf(templateEngine)); // Adiciona o Thymeleaf
        }).start(8000); // Inicia o servidor na porta 8000

        // Define uma rota básica para renderizar o template
        app.get("/", ctx -> {
            ctx.render("index.html", Map.of("message", "Bem-vindo ao Butcher Shop!"));
        });
    }

    // Método para configurar o Template Engine do Thymeleaf
    private static TemplateEngine configureThymeleaf() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/"); // Diretório onde os templates estão localizados
        resolver.setSuffix(".html"); // Sufixo dos arquivos de template
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8"); // Define UTF-8 para suportar acentos e caracteres especiais

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }
}
