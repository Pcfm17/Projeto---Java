package controller;

import dao.CadastrarPedidoDAO;
import dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import model.CadastrarPedidoModel;
import view.CadastrarPedido;

public class ControleCadastrarPedido {
    private CadastrarPedido telaCadastrarPedido;

    public ControleCadastrarPedido(CadastrarPedido telaCadastrarPedido){
        this.telaCadastrarPedido = telaCadastrarPedido;
    }

    public void CriarPedido(){
        String id = telaCadastrarPedido.getTxtCadastrarPedidoId().getText().trim();
        String alimento = telaCadastrarPedido.getTxtCadastrarPedidoNome().getText().trim();

        if (id.isEmpty() || alimento.isEmpty()) {
            JOptionPane.showMessageDialog(telaCadastrarPedido, "ID e Alimento devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conexaoBanco = new Conexao().getConnection()) {
            CadastrarPedidoDAO pedidoDAO = new CadastrarPedidoDAO(conexaoBanco);
            
            if (pedidoDAO.verificarIdExistente(id)) {
                JOptionPane.showMessageDialog(telaCadastrarPedido, "Este ID já existe. Por favor, use um ID diferente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!pedidoDAO.verificarAlimentoExiste(alimento)) {
                mostrarCardapioCompleto(pedidoDAO.listarCardapio());
                return;
            }
            
            BigDecimal precoAlimento = pedidoDAO.buscarPrecoAlimento(alimento);
            CadastrarPedidoModel pedido = new CadastrarPedidoModel(id, alimento, precoAlimento);
            pedidoDAO.inserir(pedido);
            
            JOptionPane.showMessageDialog(telaCadastrarPedido, 
                    "Pedido criado com sucesso!\nID: " + id + 
                    "\nAlimento: " + alimento + 
                    "\nPreço: R$ " + precoAlimento, 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            limparCampos();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(telaCadastrarPedido, "Erro ao criar pedido: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void EditarPedido(){
        try {
            // CORREÇÃO: Usar o campo específico para edição
            String id = telaCadastrarPedido.getTxtCadastrarPedidoIDEditar().getText().trim();
            String alimentoAntigo = telaCadastrarPedido.getTxtEditarCadastrarPedidoNomeExistente1().getText().trim();
            String alimentoNovo = telaCadastrarPedido.getTxtEditarCadastrarPedidoNomeSubstituicao().getText().trim();

            if (id.isEmpty() || alimentoAntigo.isEmpty() || alimentoNovo.isEmpty()) {
                JOptionPane.showMessageDialog(telaCadastrarPedido, "Todos os campos de edição devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection conexaoBanco = new Conexao().getConnection()) {
                CadastrarPedidoDAO pedidoDAO = new CadastrarPedidoDAO(conexaoBanco);

                // VERIFICAÇÃO ALTERNATIVA se buscarPorId ainda der problema
                if (!pedidoDAO.verificarIdExistente(id)) {
                    JOptionPane.showMessageDialog(telaCadastrarPedido, "Pedido não encontrado com este ID.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Buscar o pedido atual para verificar o alimento antigo
                // Método alternativo se buscarPorId não funcionar
                String comandoSQL = "SELECT alimento FROM pedidos WHERE id = ?";
                try (java.sql.PreparedStatement stmt = conexaoBanco.prepareStatement(comandoSQL)) {
                    stmt.setString(1, id);
                    java.sql.ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        String alimentoAtualNoBanco = rs.getString("alimento");

                        if (!alimentoAtualNoBanco.equals(alimentoAntigo)) {
                            JOptionPane.showMessageDialog(telaCadastrarPedido, 
                                "O alimento antigo não coincide com o pedido atual.\n" +
                                "Alimento no pedido: " + alimentoAtualNoBanco + "\n" +
                                "Alimento informado: " + alimentoAntigo, 
                                "Erro", 
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                if (!pedidoDAO.verificarAlimentoExiste(alimentoNovo)) {
                    mostrarCardapioCompleto(pedidoDAO.listarCardapio());
                    return;
                }

                BigDecimal novoPreco = pedidoDAO.buscarPrecoAlimento(alimentoNovo);
                pedidoDAO.atualizarPedido(id, alimentoNovo, novoPreco);

                JOptionPane.showMessageDialog(telaCadastrarPedido, 
                        "Pedido editado com sucesso!\nID: " + id +
                        "\nNovo alimento: " + alimentoNovo + 
                        "\nNovo preço: R$ " + novoPreco, 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);

                limparCamposEdicao();

            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(telaCadastrarPedido, "Erro ao editar pedido: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaCadastrarPedido, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ExcluirPedido(){
        String id = telaCadastrarPedido.getTxtCadastrarPedidoId().getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(telaCadastrarPedido, "Informe o ID do pedido a ser excluído.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(telaCadastrarPedido, 
            "Tem certeza que deseja excluir o pedido com ID: " + id + "?",
            "Confirmação de Exclusão",
            JOptionPane.YES_NO_OPTION);

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection conexaoBanco = new Conexao().getConnection()) {
            CadastrarPedidoDAO pedidoDAO = new CadastrarPedidoDAO(conexaoBanco);
            
            if (!pedidoDAO.verificarIdExistente(id)) {
                JOptionPane.showMessageDialog(telaCadastrarPedido, 
                    "ID não encontrado para exclusão.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            pedidoDAO.excluirPedido(id);
            
            JOptionPane.showMessageDialog(telaCadastrarPedido, 
                    "Pedido excluído com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            limparCampos();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(telaCadastrarPedido, "Erro ao excluir pedido: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        telaCadastrarPedido.getTxtCadastrarPedidoId().setText("");
        telaCadastrarPedido.getTxtCadastrarPedidoNome().setText("");
    }

    private void limparCamposEdicao() {
        telaCadastrarPedido.getTxtCadastrarPedidoIDEditar().setText("");
        telaCadastrarPedido.getTxtEditarCadastrarPedidoNomeExistente1().setText("");
        telaCadastrarPedido.getTxtEditarCadastrarPedidoNomeSubstituicao().setText("");
    }

    private void mostrarCardapioCompleto(java.util.List<String> cardapio) {
        StringBuilder mensagemCardapio = new StringBuilder();
        mensagemCardapio.append("Alimento não encontrado no cardápio!\n\n");
        mensagemCardapio.append("CARDÁPIO DISPONÍVEL:\n");
        
        for (int indice = 0; indice < cardapio.size(); indice++) {
            mensagemCardapio.append("• ").append(cardapio.get(indice)).append("\n");
            if ((indice + 1) % 5 == 0) {
                mensagemCardapio.append("\n");
            }
        }
        
        javax.swing.JTextArea areaTextoCardapio = new javax.swing.JTextArea(mensagemCardapio.toString());
        areaTextoCardapio.setEditable(false);
        areaTextoCardapio.setLineWrap(true);
        areaTextoCardapio.setWrapStyleWord(true);
        areaTextoCardapio.setBackground(new java.awt.Color(240, 240, 240));
        
        javax.swing.JScrollPane painelRolagemCardapio = new javax.swing.JScrollPane(areaTextoCardapio);
        painelRolagemCardapio.setPreferredSize(new java.awt.Dimension(400, 250));
        
        JOptionPane.showMessageDialog(telaCadastrarPedido, painelRolagemCardapio, 
                                    "Cardápio - Alimentos Disponíveis", 
                                    JOptionPane.ERROR_MESSAGE);
    }
}