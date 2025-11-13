package dao;

import model.CadastrarPedidoModel;
import java.sql.*;
import java.math.BigDecimal;

public class CadastrarPedidoDAO {
    private Connection conexao;

    public CadastrarPedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    public void inserir(CadastrarPedidoModel pedido) throws SQLException {
        String comandoSQL = "INSERT INTO pedidos (id, alimento, preco) VALUES (?, ?, ?)";
        try (PreparedStatement declaracaoPreparada = conexao.prepareStatement(comandoSQL)) {
            declaracaoPreparada.setString(1, pedido.getId());
            declaracaoPreparada.setString(2, pedido.getAlimento());
            declaracaoPreparada.setBigDecimal(3, pedido.getPreco());
            declaracaoPreparada.executeUpdate();
        }
    }
    
    public boolean verificarIdExistente(String id) throws SQLException {
        String comandoSQL = "SELECT COUNT(*) FROM pedidos WHERE id = ?";
        try (PreparedStatement declaracaoPreparada = conexao.prepareStatement(comandoSQL)) {
            declaracaoPreparada.setString(1, id);
            ResultSet resultadoConsulta = declaracaoPreparada.executeQuery();
            return resultadoConsulta.next() && resultadoConsulta.getInt(1) > 0;
        }
    }
    
    // Método para buscar pedido por ID
    public CadastrarPedidoModel buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CadastrarPedidoModel pedido = new CadastrarPedidoModel();
                pedido.setId(rs.getString("id"));
                pedido.setAlimento(rs.getString("alimento"));
                pedido.setPreco(rs.getBigDecimal("preco"));
                return pedido;
            }
            return null;
        }
    }
    
    public void atualizarPedido(String id, String novoAlimento, BigDecimal novoPreco) throws SQLException {
        String comandoSQL = "UPDATE pedidos SET alimento = ?, preco = ? WHERE id = ?";
        try (PreparedStatement declaracaoPreparada = conexao.prepareStatement(comandoSQL)) {
            declaracaoPreparada.setString(1, novoAlimento);
            declaracaoPreparada.setBigDecimal(2, novoPreco);
            declaracaoPreparada.setString(3, id);
            declaracaoPreparada.executeUpdate();
        }
    }
    
    public void excluirPedido(String id) throws SQLException {
        String comandoSQL = "DELETE FROM pedidos WHERE id = ?";
        try (PreparedStatement declaracaoPreparada = conexao.prepareStatement(comandoSQL)) {
            declaracaoPreparada.setString(1, id);
            declaracaoPreparada.executeUpdate();
        }
    }
    
    // Busca preço do alimento no cardápio
    public BigDecimal buscarPrecoAlimento(String alimento) throws SQLException {
        String comandoSQL = "SELECT preco FROM cadastrodepedido WHERE alimento = ?";
        try (PreparedStatement declaracaoPreparada = conexao.prepareStatement(comandoSQL)) {
            declaracaoPreparada.setString(1, alimento);
            ResultSet resultadoConsulta = declaracaoPreparada.executeQuery();
            if (resultadoConsulta.next()) {
                return resultadoConsulta.getBigDecimal("preco");
            }
            return BigDecimal.ZERO;
        }
    }
    
    // Verifica se alimento existe no cardápio
    public boolean verificarAlimentoExiste(String alimento) throws SQLException {
        String comandoSQL = "SELECT COUNT(*) FROM cadastrodepedido WHERE alimento = ?";
        try (PreparedStatement declaracaoPreparada = conexao.prepareStatement(comandoSQL)) {
            declaracaoPreparada.setString(1, alimento);
            ResultSet resultadoConsulta = declaracaoPreparada.executeQuery();
            return resultadoConsulta.next() && resultadoConsulta.getInt(1) > 0;
        }
    }
    
    // Lista todos os alimentos do cardápio
    public java.util.List<String> listarCardapio() throws SQLException {
        String comandoSQL = "SELECT alimento FROM cadastrodepedido ORDER BY alimento";
        try (PreparedStatement declaracaoPreparada = conexao.prepareStatement(comandoSQL)) {
            ResultSet resultadoConsulta = declaracaoPreparada.executeQuery();
            java.util.List<String> cardapio = new java.util.ArrayList<>();
            while (resultadoConsulta.next()) {
                cardapio.add(resultadoConsulta.getString("alimento"));
            }
            return cardapio;
        }
    }
}