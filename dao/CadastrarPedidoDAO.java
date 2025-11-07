/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.CadastrarPedidoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author paulo
 */
public class CadastrarPedidoDAO {
    private Connection conn;

    public CadastrarPedidoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void inserir(CadastrarPedidoModel cadastrarPedidoModel) throws SQLException {
        String sql = "INSERT INTO pedidos (email, nome) VALUES (?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cadastrarPedidoModel.getEmail());
            stmt.setString(2, cadastrarPedidoModel.getNome());
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    public boolean verificarEmailExistente(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE email = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
    
    public CadastrarPedidoModel buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE email = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new CadastrarPedidoModel(
                    rs.getString("email"),
                    rs.getString("nome")
                );
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
    
    public void atualizarPedido(String emailAntigo, String novoNome) throws SQLException {
        String sql = "UPDATE pedidos SET nome = ? WHERE email = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, novoNome);
            stmt.setString(2, emailAntigo);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
        }
    }
    
    public void excluirPedido(String email) throws SQLException {
        String sql = "DELETE FROM pedidos WHERE email = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
        }
    }
}