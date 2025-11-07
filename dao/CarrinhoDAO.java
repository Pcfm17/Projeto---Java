/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.CarrinhoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class CarrinhoDAO {
    private Connection conn;

    public CarrinhoDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Adiciona um alimento ao pedido (atualiza o campo nome)
    public void adicionarAlimento(CarrinhoModel item) throws SQLException {
        String sql = "UPDATE pedidos SET nome = nome || ', ' || ? WHERE email = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getAlimento());
            stmt.setString(2, item.getEmail());
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    // Remove um alimento específico do pedido
    public void removerAlimento(String email, String alimento) throws SQLException {
        // Primeiro busca o pedido atual
        String pedidoAtual = buscarPedidoPorEmail(email);
        if (pedidoAtual != null && pedidoAtual.contains(alimento)) {
            // Remove o alimento da string
            String novoPedido = pedidoAtual.replace(alimento, "")
                                          .replace(",,", ",")
                                          .replaceAll("^,\\s*", "")
                                          .replaceAll(",\\s*$", "")
                                          .trim();
            
            // Atualiza o pedido
            String sql = "UPDATE pedidos SET nome = ? WHERE email = ?";
            PreparedStatement stmt = null;
            try {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, novoPedido);
                stmt.setString(2, email);
                stmt.executeUpdate();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }
    
    // Busca o pedido completo por email
    public String buscarPedidoPorEmail(String email) throws SQLException {
        String sql = "SELECT nome FROM pedidos WHERE email = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
    
    // Lista todos os alimentos do pedido (separados por vírgula)
    public List<CarrinhoModel> listarAlimentosPorEmail(String email) 
            throws SQLException {
        String pedidoCompleto = buscarPedidoPorEmail(email);
        List<CarrinhoModel> alimentos = new ArrayList<>();
        
        if (pedidoCompleto != null && !pedidoCompleto.isEmpty()) {
            String[] itens = pedidoCompleto.split(",");
            for (String item : itens) {
                String alimento = item.trim();
                if (!alimento.isEmpty()) {
                    alimentos.add(new CarrinhoModel(email, alimento));
                }
            }
        }
        return alimentos;
    }
    
    // Verifica se o email existe na tabela pedidos
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
    
    // Verifica se um alimento específico existe no pedido
    public boolean verificarAlimentoExistente(String email, String alimento) 
            throws SQLException {
        String pedidoCompleto = buscarPedidoPorEmail(email);
        if (pedidoCompleto != null) {
            String[] itens = pedidoCompleto.split(",");
            for (String item : itens) {
                if (item.trim().equalsIgnoreCase(alimento.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // Cria um novo pedido se não existir (para quando quiser adicionar itens 
        //a um email novo)
    public void criarPedidoSeNaoExistir(String email) throws SQLException {
        if (!verificarEmailExistente(email)) {
            String sql = "INSERT INTO pedidos (email, nome) VALUES (?, '')";
            PreparedStatement stmt = null;
            try {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.executeUpdate();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }
}