package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.AvaliacaoModel;

public class AvaliacaoDAO {
    private final Connection connection;

    public AvaliacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(AvaliacaoModel avaliacao) throws SQLException {
        String sql = "INSERT INTO avaliacao (pedido_id, descricao, nota) VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Assumimos que o pedidoId é String, mas pode ser um Integer (ajuste conforme seu banco)
            statement.setString(1, avaliacao.getPedidoId()); 
            statement.setString(2, avaliacao.getDescricao());
            statement.setString(3, avaliacao.getNota()); // A nota é salva como 1, 2, 3, 4 ou 5
            
            statement.execute();
        }
    }
}