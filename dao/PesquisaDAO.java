package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.PesquisaModel;

public class PesquisaDAO {
    private Connection con;

    public PesquisaDAO(Connection con) {
        this.con = con;
    }

    // Inserir pesquisa no banco
    public void inserir(PesquisaModel pesquisa) throws SQLException {
        String sql = "INSERT INTO salvandopesquisa (email, alimentos) VALUES (?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, pesquisa.getEmail());
        stmt.setString(2, pesquisa.getAlimento());
        stmt.executeUpdate();
        stmt.close();
    }

    // Buscar por email
    public PesquisaModel buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM salvandopesquisa WHERE email = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        PesquisaModel pesquisa = null;
        if (rs.next()) {
            pesquisa = new PesquisaModel(
                rs.getString("email"),
                rs.getString("alimentos")
            );
        }

        rs.close();
        stmt.close();
        return pesquisa;
    }
}
