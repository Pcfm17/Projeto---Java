/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CarrinhoDAO;
import dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.CarrinhoModel;
import view.Carrinho;

/**
 *
 * @author paulo
 */
public class ControleCarrinho {
    private Carrinho telaCarrinho;

    public ControleCarrinho(Carrinho telaCarrinho) {
        this.telaCarrinho = telaCarrinho;
    }

    public void AdicionarPedido() {
        String email = telaCarrinho.getTxtCarrinhoPedidoEmail().getText().trim();
        String alimento = telaCarrinho.getTxtCarrinhoPedidoAlimento().getText().trim();

        if (email.isEmpty() || alimento.isEmpty()) {
            JOptionPane.showMessageDialog(telaCarrinho, "Todos os campos devem "
                    + "ser preenchidos.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            Conexao conexao = new Conexao();
            conn = conexao.getConnection();
            CarrinhoDAO dao = new CarrinhoDAO(conn);
            
            // Verifica se o pedido existe, se não existir cria um vazio
            if (!dao.verificarEmailExistente(email)) {
                int resposta = JOptionPane.showConfirmDialog(telaCarrinho, 
                    "Email não encontrado. Deseja criar um novo pedido para este email?",
                    "Pedido Não Encontrado",
                    JOptionPane.YES_NO_OPTION);
                
                if (resposta == JOptionPane.YES_OPTION) {
                    dao.criarPedidoSeNaoExistir(email);
                } else {
                    return;
                }
            }
            
            CarrinhoModel item = new CarrinhoModel(email, alimento);
            dao.adicionarAlimento(item);
            
            JOptionPane.showMessageDialog(telaCarrinho, 
                    "Alimento adicionado ao pedido com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            atualizarListaCarrinho(email);
            limparCampoAlimento();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCarrinho, "Erro ao adicionar "
                    + "alimento: " + ex.getMessage(), "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao(conn);
        }
    }

    public void RemoverPedido() {
        String email = telaCarrinho.getTxtCarrinhoPedidoEmail().getText().trim();
        String alimento = telaCarrinho.getTxtCarrinhoPedidoAlimento().getText().trim();

        if (email.isEmpty() || alimento.isEmpty()) {
            JOptionPane.showMessageDialog(telaCarrinho, "Todos os campos "
                    + "devem ser preenchidos.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            Conexao conexao = new Conexao();
            conn = conexao.getConnection();
            CarrinhoDAO dao = new CarrinhoDAO(conn);
            
            // Verifica se o pedido existe
            if (!dao.verificarEmailExistente(email)) {
                JOptionPane.showMessageDialog(telaCarrinho, 
                    "Email não encontrado nos pedidos.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verifica se o alimento existe no pedido
            if (!dao.verificarAlimentoExistente(email, alimento)) {
                JOptionPane.showMessageDialog(telaCarrinho, 
                    "Alimento não encontrado no pedido.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            dao.removerAlimento(email, alimento);
            
            JOptionPane.showMessageDialog(telaCarrinho, 
                    "Alimento removido do pedido com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            atualizarListaCarrinho(email);
            limparCampoAlimento();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCarrinho, "Erro ao remover "
                    + "alimento: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao(conn);
        }
    }
    
    public void atualizarListaCarrinho(String email) {
        if (email == null || email.isEmpty()) {
            telaCarrinho.getTxtCarrinhoLista().setText("Digite um email para "
                    + "visualizar o pedido");
            return;
        }
        
        Connection conn = null;
        try {
            Conexao conexao = new Conexao();
            conn = conexao.getConnection();
            CarrinhoDAO dao = new CarrinhoDAO(conn);
            
            // Verifica se o pedido existe
            if (!dao.verificarEmailExistente(email)) {
                telaCarrinho.getTxtCarrinhoLista().setText("Nenhum pedido "
                        + "encontrado para: " + email);
                return;
            }
            
            List<CarrinhoModel> alimentos = dao.listarAlimentosPorEmail(email);
            String pedidoCompleto = dao.buscarPedidoPorEmail(email);
            
            StringBuilder lista = new StringBuilder();
            lista.append("Pedido para: ").append(email).append("\n\n");
            
            if (alimentos.isEmpty() || (pedidoCompleto != null && pedidoCompleto.trim().isEmpty())) {
                lista.append("Pedido vazio\n");
            } else {
                lista.append("Itens no pedido:\n");
                for (int i = 0; i < alimentos.size(); i++) {
                    lista.append(i + 1).append(". ").append(alimentos.get(i).getAlimento()).append("\n");
                }
                lista.append("\nPedido completo: ").append(pedidoCompleto);
            }
            
            telaCarrinho.getTxtCarrinhoLista().setText(lista.toString());
            
        } catch (SQLException ex) {
            telaCarrinho.getTxtCarrinhoLista().setText("Erro ao carregar "
                    + "pedido: " + ex.getMessage());
        } finally {
            fecharConexao(conn);
        }
    }

    private void limparCampoAlimento() {
        telaCarrinho.getTxtCarrinhoPedidoAlimento().setText("");
    }

    private void fecharConexao(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}