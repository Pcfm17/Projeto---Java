package dao;

import java.math.BigDecimal;
import model.CarrinhoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CadastrarPedidoModel;
import dao.Conexao;

public class CarrinhoDAO {
    private Connection conn;

    public CarrinhoDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Adiciona um alimento ao pedido existente
    public void adicionarAlimento(String idPedido, String novoAlimento, BigDecimal novoPreco) throws SQLException {
        // Buscar pedido atual
        CadastrarPedidoModel pedidoAtual = buscarPedidoPorId(idPedido);
        if (pedidoAtual == null) {
            throw new SQLException("Pedido não encontrado");
        }
        
        // Concatenar novo alimento
        String alimentosAtualizados = pedidoAtual.getAlimento() + ", " + novoAlimento;
        BigDecimal precoAtualizado = pedidoAtual.getPreco().add(novoPreco);
        
        // Atualizar pedido
        String sql = "UPDATE pedidos SET alimento = ?, preco = ? WHERE id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, alimentosAtualizados);
            stmt.setBigDecimal(2, precoAtualizado);
            stmt.setString(3, idPedido);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    // Busca pedido por ID
    public CadastrarPedidoModel buscarPedidoPorId(String idPedido) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, idPedido);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new CadastrarPedidoModel(
                    rs.getString("id"),
                    rs.getString("alimento"),
                    rs.getBigDecimal("preco")
                );
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
    
    // Remove um alimento específico do pedido (implementação básica)
    public void removerAlimento(String idPedido, String alimento) throws SQLException {
        // Buscar pedido atual por ID
        CadastrarPedidoModel pedidoAtual = buscarPedidoPorId(idPedido);
        if (pedidoAtual == null) {
            throw new SQLException("Pedido não encontrado");
        }

        String alimentosAtual = pedidoAtual.getAlimento();

        if (alimentosAtual != null && alimentosAtual.contains(alimento)) {
            // Remove o alimento da string de forma mais precisa
            String[] itens = alimentosAtual.split(",");
            List<String> listaAlimentos = new ArrayList<>();
            BigDecimal precoRemover = BigDecimal.ZERO;

            try (Connection conexaoBanco = new Conexao().getConnection()) {
                CadastrarPedidoDAO pedidoDAO = new CadastrarPedidoDAO(conexaoBanco);
                precoRemover = pedidoDAO.buscarPrecoAlimento(alimento.trim());
            } catch (SQLException e) {
                // Se não conseguir buscar o preço, usa 0
                precoRemover = BigDecimal.ZERO;
            }

            // Filtra os alimentos, removendo o que deve ser excluído
            for (String item : itens) {
                String itemTrim = item.trim();
                if (!itemTrim.isEmpty() && !itemTrim.equalsIgnoreCase(alimento.trim())) {
                    listaAlimentos.add(itemTrim);
                }
            }

            // Junta os alimentos restantes
            String novoAlimento = String.join(", ", listaAlimentos);
            BigDecimal novoPreco = pedidoAtual.getPreco().subtract(precoRemover);

            // Atualiza o pedido com os campos corretos
            String sql = "UPDATE pedidos SET alimento = ?, preco = ? WHERE id = ?";
            PreparedStatement stmt = null;
            try {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, novoAlimento);
                stmt.setBigDecimal(2, novoPreco);
                stmt.setString(3, idPedido);
                stmt.executeUpdate();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }
    
    // Calcula total com taxa para bebidas alcoólicas
    public BigDecimal calcularTotalComTaxa(String idPedido) throws SQLException {
        CadastrarPedidoModel pedido = buscarPedidoPorId(idPedido);
        if (pedido == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal total = pedido.getPreco();
        
        // Verificar se há bebidas alcoólicas (você precisa definir quais são)
        // Por enquanto, vou assumir que "cerveja", "vinho", "vodka" são alcoólicas
        String alimentos = pedido.getAlimento().toLowerCase();
        boolean temBebidaAlcoolica = alimentos.contains("cerveja") || 
                                    alimentos.contains("vinho") || 
                                    alimentos.contains("vodka") ||
                                    alimentos.contains("whisky");
        
        if (temBebidaAlcoolica) {
            BigDecimal taxa = total.multiply(new BigDecimal("0.10"));
            total = total.add(taxa);
        }
        
        return total;
    }
    
    // Lista todos os alimentos do pedido
    public List<CarrinhoModel> listarAlimentosPorId(String idPedido) throws SQLException {
        CadastrarPedidoModel pedido = buscarPedidoPorId(idPedido);
        List<CarrinhoModel> alimentos = new ArrayList<>();
        
        if (pedido != null && pedido.getAlimento() != null && !pedido.getAlimento().isEmpty()) {
            String[] itens = pedido.getAlimento().split(",");
            for (String item : itens) {
                String alimento = item.trim();
                if (!alimento.isEmpty()) {
                    alimentos.add(new CarrinhoModel(idPedido, alimento));
                }
            }
        }
        return alimentos;
    }
    
    // Verifica se o ID existe
    public boolean verificarIdExistente(String idPedido) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE id = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, idPedido);
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
}