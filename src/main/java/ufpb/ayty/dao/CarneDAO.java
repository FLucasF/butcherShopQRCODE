package ufpb.ayty.dao;

import ufpb.ayty.config.DatabaseConfig;
import ufpb.ayty.model.Carne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarneDAO {

    // Salvar carne
    public int saveCarne(Carne carne) {
        String query = "INSERT INTO carne (tipo, origem, corte, validade, bioma, idade_abate, marmoreio, certificacao_qualidade, qr_code_url, dicas_preparo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            stmt.setString(10, carne.getDicasPreparo());
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

    // Atualizar carne
    public boolean updateCarne(Carne carne) {
        String query = "UPDATE carne SET tipo = ?, origem = ?, corte = ?, validade = ?, bioma = ?, " +
                "idade_abate = ?, marmoreio = ?, certificacao_qualidade = ?, dicas_preparo = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, carne.getTipo());
            stmt.setString(2, carne.getOrigem());
            stmt.setString(3, carne.getCorte());
            stmt.setDate(4, carne.getValidade());
            stmt.setString(5, carne.getBioma());
            stmt.setInt(6, carne.getIdadeAbate());
            stmt.setString(7, carne.getMarmoreio());
            stmt.setString(8, carne.getCertificacaoQualidade());
            stmt.setString(9, carne.getDicasPreparo());
            stmt.setInt(10, carne.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Excluir carne
    public boolean deleteCarne(int id) {
        String query = "DELETE FROM carne WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar carne por ID
    public Carne getById(int id) {
        String query = "SELECT * FROM carne WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
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
                carne.setDicasPreparo(rs.getString("dicas_preparo"));
                return carne;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todas as carnes
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
                carne.setDicasPreparo(rs.getString("dicas_preparo"));

                carnes.add(carne);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return carnes;
    }
}
