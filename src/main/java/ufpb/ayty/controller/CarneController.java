package ufpb.ayty.controller;

import io.javalin.Javalin;
import ufpb.ayty.dao.CarneDAO;
import ufpb.ayty.model.Carne;
import ufpb.ayty.util.MinIOUtil;
import ufpb.ayty.util.QRCodeUtil;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class CarneController {

    private final CarneDAO carneDAO = new CarneDAO();

    public void registerRoutes(Javalin app) {
        app.get("/carne/list", ctx -> {
            List<Carne> carnes = carneDAO.getAll();
            ctx.render("list.html", Map.of("carnes", carnes));
        });

        app.get("/carne/adicionar", ctx -> ctx.render("add.html"));

        app.post("/carne", ctx -> {
            try {
                // Criar a nova carne a partir dos parâmetros enviados
                Carne novaCarne = new Carne();
                novaCarne.setTipo(ctx.formParam("tipo"));
                novaCarne.setOrigem(ctx.formParam("origem"));
                novaCarne.setCorte(ctx.formParam("corte"));
                novaCarne.setValidade(Date.valueOf(ctx.formParam("validade")));
                novaCarne.setBioma(ctx.formParam("bioma"));
                novaCarne.setIdadeAbate(Integer.parseInt(ctx.formParam("idadeAbate")));
                novaCarne.setMarmoreio(ctx.formParam("marmoreio"));
                novaCarne.setCertificacaoQualidade(ctx.formParam("certificacaoQualidade"));
                novaCarne.setDicasPreparo(ctx.formParam("dicasPreparo"));

                // Adicione aqui qualquer outra informação necessária

                // Gerar o conteúdo do QR Code com todas as informações relevantes
                String qrContent = String.format(
                        "Tipo: %s\nOrigem: %s\nCorte: %s\nValidade: %s\nBioma: %s\nIdade do Abate: %d\nMarmoreio: %s\nCertificação: %s\nDicas de Preparo: %s",
                        novaCarne.getTipo(),
                        novaCarne.getOrigem(),
                        novaCarne.getCorte(),
                        novaCarne.getValidade(),
                        novaCarne.getBioma(),
                        novaCarne.getIdadeAbate(),
                        novaCarne.getMarmoreio(),
                        novaCarne.getCertificacaoQualidade(),
                        novaCarne.getDicasPreparo()
                );

                // Gerar o QR Code como bytes
                byte[] qrCodeBytes = QRCodeUtil.generateQRCodeToBytes(qrContent);

                // Enviar para o MinIO e obter a URL
                String qrCodeFileName = "carne-" + System.currentTimeMillis() + ".png";
                String qrCodeUrl = MinIOUtil.uploadQRCode(qrCodeFileName, qrCodeBytes);

                // Configurar a URL do QR Code na carne
                novaCarne.setQrCodeUrl(qrCodeUrl);

                // Salvar a carne no banco de dados
                carneDAO.saveCarne(novaCarne);

                ctx.redirect("/carne/list");
            } catch (Exception e) {
                ctx.status(500).result("Erro ao adicionar carne: " + e.getMessage());
            }
        });



        app.get("/carne/editar/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Carne carne = carneDAO.getById(id);
                if (carne != null) {
                    ctx.render("update.html", Map.of("carne", carne));
                } else {
                    ctx.status(404).result("Carne não encontrada");
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("ID inválido");
            }
        });

        app.post("/carne/editar/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Carne carne = carneDAO.getById(id);
                if (carne != null) {
                    carne.setTipo(ctx.formParam("tipo"));
                    carne.setOrigem(ctx.formParam("origem"));
                    carne.setCorte(ctx.formParam("corte"));
                    carne.setValidade(Date.valueOf(ctx.formParam("validade")));
                    carne.setBioma(ctx.formParam("bioma"));
                    carne.setIdadeAbate(Integer.parseInt(ctx.formParam("idadeAbate")));
                    carne.setMarmoreio(ctx.formParam("marmoreio"));
                    carne.setCertificacaoQualidade(ctx.formParam("certificacaoQualidade"));

                    carneDAO.updateCarne(carne);
                    ctx.redirect("/carne/list");
                } else {
                    ctx.status(404).result("Carne não encontrada");
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("ID inválido");
            }
        });

        app.get("/carne/excluir/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                boolean success = carneDAO.deleteCarne(id);
                if (success) {
                    ctx.redirect("/carne/list");
                } else {
                    ctx.status(404).result("Carne não encontrada");
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("ID inválido");
            }
        });

    }
}
