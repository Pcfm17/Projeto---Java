package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.AlimentoModel;

public class AlimentoDAO {
    private final Connection conexao;

    public AlimentoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public AlimentoModel buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT alimento, informacoes FROM comidadainformacoes WHERE alimento ILIKE ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AlimentoModel alimento = new AlimentoModel();
                    alimento.setNome(rs.getString("alimento"));
                    alimento.setInformacoes(rs.getString("informacoes"));
                    return alimento;
                }
            }
        }

        return null;
    }
}
