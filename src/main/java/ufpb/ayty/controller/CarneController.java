package ufpb.ayty.controller;

import io.javalin.Javalin;
import ufpb.ayty.model.Carne;
import ufpb.ayty.service.CarneService;

import java.util.List;

public class CarneController {
    private final CarneService carneService = new CarneService();

    public void registerRoutes(Javalin app) {
        app.post("/carnes", ctx -> {
            Carne carne = new Carne();
            carne.setTipo(ctx.formParam("tipo"));
            carne.setOrigem(ctx.formParam("origem"));
            carne.setCorte(ctx.formParam("corte"));
            carne.setValidade(java.sql.Date.valueOf(ctx.formParam("validade")));
            carne.setBioma(ctx.formParam("bioma"));
            carne.setIdadeAbate(Integer.parseInt(ctx.formParam("idadeAbate")));
            carne.setMarmoreio(ctx.formParam("marmoreio"));
            carne.setCertificacaoQualidade(ctx.formParam("certificacaoQualidade"));

            Carne createdCarne = carneService.createCarne(carne);

            ctx.result("Carne cadastrada com sucesso! ID: " + createdCarne.getId());
        });
    }
}
