package ufpb.ayty;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import ufpb.ayty.model.Carne;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {

    private List<Carne> carnes = new ArrayList<>();

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

        // Inicializar dados de exemplo
        carnes.add(new Carne(1, "Bovina", "Brasil", "Picanha", Date.valueOf("2025-01-20"), "Amazônia", 24, "Alto",
                "Certificação A", "http://example.com/qrcode/1", List.of(), List.of(), List.of(), "Ideal para churrasco"));
        carnes.add(new Carne(2, "Suína", "Brasil", "Costela", Date.valueOf("2025-01-15"), "Cerrado", 18, "Médio",
                "Certificação B", "http://example.com/qrcode/2", List.of(), List.of(), List.of(), "Assar com molho"));

        // Rotas
        app.get("/list", ctx -> {
            ctx.render("list.html", Map.of("carnes", carnes));
        });

        app.get("/carne/adicionar", ctx -> {
            ctx.render("add.html");
        });

        app.post("/carnes", ctx -> {
            Carne novaCarne = new Carne();
            novaCarne.setId(carnes.size() + 1); // Gerar ID sequencial
            novaCarne.setTipo(ctx.formParam("tipo"));
            novaCarne.setOrigem(ctx.formParam("origem"));
            novaCarne.setCorte(ctx.formParam("corte"));
            novaCarne.setValidade(Date.valueOf(ctx.formParam("validade")));
            novaCarne.setBioma(ctx.formParam("bioma"));
            novaCarne.setIdadeAbate(Integer.parseInt(ctx.formParam("idadeAbate")));
            novaCarne.setMarmoreio(ctx.formParam("marmoreio"));
            novaCarne.setCertificacaoQualidade(ctx.formParam("certificacaoQualidade"));
            novaCarne.setDicasPreparo("Dicas padrão");
            carnes.add(novaCarne);
            ctx.redirect("/list");
        });

        app.get("/carne/editar/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Carne carne = carnes.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (carne != null) {
                ctx.render("update.html", Map.of("carne", carne));
            } else {
                ctx.status(404).result("Carne não encontrada");
            }
        });

        app.post("/carnes/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Carne carne = carnes.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (carne != null) {
                carne.setTipo(ctx.formParam("tipo"));
                carne.setOrigem(ctx.formParam("origem"));
                carne.setCorte(ctx.formParam("corte"));
                carne.setValidade(Date.valueOf(ctx.formParam("validade")));
                carne.setBioma(ctx.formParam("bioma"));
                carne.setIdadeAbate(Integer.parseInt(ctx.formParam("idadeAbate")));
                carne.setMarmoreio(ctx.formParam("marmoreio"));
                carne.setCertificacaoQualidade(ctx.formParam("certificacaoQualidade"));
                ctx.redirect("/list");
            } else {
                ctx.status(404).result("Carne não encontrada");
            }
        });

        app.get("/carne/excluir/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            carnes.removeIf(c -> c.getId() == id);
            ctx.redirect("/list");
        });
    }

    public static void main(String[] args) {
        new App().start();
    }
}
