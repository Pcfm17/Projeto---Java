package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoAvaliacao {
    // ALTERADO: Apenas o nome do banco de dados (alunos).
    private static final String URL = "jdbc:postgresql://localhost:5432/alunos"; 
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "fei"; 

    public Connection getConnection() throws SQLException {
        try {
            // Carrega o driver do PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Retorna a conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado!");
        }
    }
}