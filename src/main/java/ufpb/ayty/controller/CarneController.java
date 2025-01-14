package ufpb.ayty.controller;

import io.javalin.Javalin;
import ufpb.ayty.dao.CarneDAO;
import ufpb.ayty.model.Carne;
import ufpb.ayty.service.CarneService;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class CarneController {
    private final CarneDAO carneDAO = new CarneDAO(); // DAO para acesso ao banco de dados
    private final CarneService carneService = new CarneService();

    public void registerRoutes(Javalin app) {
        // Rota para listar carnes
        app.get("/carne/list", ctx -> {
            List<Carne> carnes = carneDAO.getAll(); // Recuperar carnes do banco de dados
            ctx.render("list.html", Map.of("carnes", carnes));
        });

        // Rota para exibir formulário de adicionar carne
        app.get("/carne/adicionar", ctx -> {
            ctx.render("add.html");
        });

        // Rota para processar o formulário de adicionar carne
        app.post("/carne", ctx -> {
                    Carne novaCarne = new Carne();
                    novaCarne.setTipo(ctx.formParam("tipo"));
                    novaCarne.setOrigem(ctx.formParam("origem"));
                    novaCarne.setCorte(ctx.formParam("corte"));
                    novaCarne.setValidade(java.sql.Date.valueOf(ctx.formParam("validade")));
                    novaCarne.setBioma(ctx.formParam("bioma"));
                    novaCarne.setIdadeAbate(Integer.parseInt(ctx.formParam("idadeAbate")));
                    novaCarne.setMarmoreio(ctx.formParam("marmoreio"));
                    novaCarne.setCertificacaoQualidade(ctx.formParam("certificacaoQualidade"));

                    // Usar o serviço para criar a carne
                    carneService.createCarne(novaCarne);

                    ctx.redirect("/carne/list");
        });

        // Rota para exibir formulário de edição de carne
        app.get("/carne/editar/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Carne carne = carneDAO.getById(id); // Buscar carne pelo ID
            if (carne != null) {
                ctx.render("update.html", Map.of("carne", carne));
            } else {
                ctx.status(404).result("Carne não encontrada");
            }
        });

        // Rota para processar a edição de carne
        app.post("/carne/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Carne carne = carneDAO.getById(id); // Buscar carne pelo ID
            if (carne != null) {
                carne.setTipo(ctx.formParam("tipo"));
                carne.setOrigem(ctx.formParam("origem"));
                carne.setCorte(ctx.formParam("corte"));
                carne.setValidade(Date.valueOf(ctx.formParam("validade")));
                carne.setBioma(ctx.formParam("bioma"));
                carne.setIdadeAbate(Integer.parseInt(ctx.formParam("idadeAbate")));
                carne.setMarmoreio(ctx.formParam("marmoreio"));
                carne.setCertificacaoQualidade(ctx.formParam("certificacaoQualidade"));

                carneDAO.updateCarne(carne); // Atualizar a carne no banco de dados
                ctx.redirect("/carne/list");
            } else {
                ctx.status(404).result("Carne não encontrada");
            }
        });

        // Rota para excluir carne
        app.get("/carne/excluir/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean success = carneDAO.deleteCarne(id); // Excluir carne pelo ID
            if (success) {
                ctx.redirect("/carne/list");
            } else {
                ctx.status(404).result("Carne não encontrada para exclusão");
            }
        });
    }
}
