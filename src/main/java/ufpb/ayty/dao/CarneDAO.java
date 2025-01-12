package  ufpb.ayty.dao;

import ufpb.ayty.config.DatabaseConfig;
import ufpb.ayty.model.Carne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarneDAO {

    public int saveCarne(Carne carne) {
        String query = "INSERT INTO carne (tipo, origem, corte, validade, bioma, idade_abate, marmoreio, certificacao_qualidade, qr_code_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, carne.getTipo());
            stmt.setString(2, carne.getOrigem());
            stmt.setString(3, carne.getCorte());
            stmt.setDate(4, carne.getValidade());
            stmt.setString(5, carne.getBioma());
            stmt.setInt(6, carne.getIdadeAbate());
            stmt.setString(7, carne.getMarmoreio());
            stmt.setString(8, carne.getCertificacaoQualidade());
            stmt.setString(9, carne.getQrCodeUrl());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Carne> getAll() {
        List<Carne> carnes = new ArrayList<>();
        String query = "SELECT * FROM carne";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Carne carne = new Carne();
                carne.setId(rs.getInt("id"));
                carne.setTipo(rs.getString("tipo"));
                carne.setOrigem(rs.getString("origem"));
                carne.setCorte(rs.getString("corte"));
                carne.setValidade(rs.getDate("validade"));
                carne.setBioma(rs.getString("bioma"));
                carne.setIdadeAbate(rs.getInt("idade_abate"));
                carne.setMarmoreio(rs.getString("marmoreio"));
                carne.setCertificacaoQualidade(rs.getString("certificacao_qualidade"));
                carne.setQrCodeUrl(rs.getString("qr_code_url"));

                carnes.add(carne);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return carnes;
    }
}
