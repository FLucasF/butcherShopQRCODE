package ufpb.ayty.service;

import ufpb.ayty.dao.CarneDAO;
import ufpb.ayty.model.Carne;
import ufpb.ayty.util.MinIOUtil;
import ufpb.ayty.util.QRCodeUtil;

import java.util.UUID;

public class CarneService {
    private final CarneDAO carneDAO = new CarneDAO();

    public Carne createCarne(Carne carne) {
        try {
            // Gerar o conteúdo do QR Code
            String qrCodeContent = "Tipo: " + carne.getTipo() +
                    ", Origem: " + carne.getOrigem() +
                    ", Corte: " + carne.getCorte() +
                    ", Validade: " + carne.getValidade() +
                    ", Bioma: " + carne.getBioma();

            // Gerar a imagem do QR Code como bytes
            byte[] qrCodeData = QRCodeUtil.generateQRCodeToBytes(qrCodeContent);

            // Nome único para o arquivo do QR Code
            String fileName = UUID.randomUUID() + ".png";

            // Fazer upload para o MinIO e obter a URL do QR Code
            String qrCodeUrl = MinIOUtil.uploadQRCode(fileName, qrCodeData);

            // Configurar a URL do QR Code na carne
            carne.setQrCodeUrl(qrCodeUrl);

            // Salvar no banco de dados
            int id = carneDAO.saveCarne(carne);
            if (id == -1) {
                throw new RuntimeException("Erro ao salvar a carne.");
            }

            carne.setId(id);
            return carne;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar ou salvar a carne.");
        }
    }

}
