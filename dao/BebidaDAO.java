package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.BebidaModel;

public class BebidaDAO {
    private final Connection conexao;

    public BebidaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public BebidaModel buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT bebida, informacoes FROM bebidadainformacoes WHERE bebida ILIKE ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BebidaModel bebida = new BebidaModel();
                    bebida.setNome(rs.getString("bebida"));
                    bebida.setInformacoes(rs.getString("informacoes"));
                    return bebida;
                }
            }
        }

        return null;
    }
}