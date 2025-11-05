package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.HistoricoModel;

public class HistoricoDAO {

    public List<HistoricoModel> buscarPorEmail(String email) {
        List<HistoricoModel> lista = new ArrayList<>();
        String sql = "SELECT email, alimento FROM salvandopesquisa WHERE email "
                + "= ?";

        try (Connection conexao = new Conexao().getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String emailBanco = rs.getString("email");
                String alimento = rs.getString("alimento");
                System.out.println("Banco: " + emailBanco + " - " + alimento);
                lista.add(new HistoricoModel(emailBanco, alimento));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar histórico: " + e.getMessage());
        }

        return lista;
    }
}
