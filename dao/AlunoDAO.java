package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Aluno;

public class AlunoDAO {
    private Connection conn;

    public AlunoDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO usuarios (email, nome, senha, genero) VALUES "
                + "(?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, aluno.getEmail());
        stmt.setString(2, aluno.getNome());
        stmt.setString(3, aluno.getSenha());
        stmt.setString(4, aluno.getGenero());
        stmt.executeUpdate();
        stmt.close();
    }
    
    public boolean verificarLogin(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            var rs = stmt.executeQuery();

            boolean existe = rs.next(); // se encontrou algum registro
            rs.close();
            stmt.close();
            return existe;
        } catch (SQLException e) {
            System.out.println("Erro ao verificar login: " + e.getMessage());
            return false;
        }
    }
    
    public boolean verificarEmailExistente(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        
        try (var rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Se a contagem for maior que zero, o email existe
                return rs.getInt(1) > 0; 
            }
        } finally {
            stmt.close();
        }
        return false; // Em caso de erro ou se não houver registros (o que é 
        //improvável com COUNT(*))
    }
    
    public Aluno buscarPorEmail(String email) throws java.sql.SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try (java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Se o usuário for encontrado, cria o objeto Aluno
                    Aluno aluno = new Aluno();
                    aluno.setEmail(rs.getString("email"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setSenha(rs.getString("senha")); // Embora não 
                    //precise mostrar, é bom carregar o objeto completo
                    aluno.setGenero(rs.getString("genero"));
                    return aluno;
                }
            }
        }
        return null; // Retorna null se não encontrar o usuário
    }

}
