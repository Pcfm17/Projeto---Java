package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.AvaliacaoModel;

public class AvaliacaoDAO {
    private Connection con;

    public AvaliacaoDAO(Connection connection) {
        this.con = connection;
    }

    public void inserir(AvaliacaoModel avaliacao) throws SQLException {
        String sql = "INSERT INTO avaliacao (email, descricao, nota) VALUES (?, ?, ?)";
        
        PreparedStatement stmt = con.prepareStatement(sql);
        // Assumimos que o pedidoId é String, mas pode ser um Integer (ajuste conforme seu banco)
        stmt.setString(1, avaliacao.getTxtAvaliacaoEmail());
        stmt.setString(2, avaliacao.getDescricao());
        stmt.setString(3, avaliacao.getNota()); // A nota é salva como 1, 2, 3, 4 ou 5
            
        stmt.executeUpdate();
        stmt.close();
    }
    
    
}