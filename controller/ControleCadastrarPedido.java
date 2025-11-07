/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CadastrarPedidoDAO;
import dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.CadastrarPedidoModel;
import view.CadastrarPedido;

/**
 *
 * @author paulo
 */
public class ControleCadastrarPedido {
    private CadastrarPedido tela3;

    public ControleCadastrarPedido(CadastrarPedido tela3){
        this.tela3 = tela3;
    }

    public void CriarPedido(){
        String email = tela3.getTxtCadastrarPedidoEmail().getText().trim();
        String nome = tela3.getTxtCadastrarPedidoNome().getText().trim();

        if (email.isEmpty() || nome.isEmpty()) {
            JOptionPane.showMessageDialog(tela3, "Todos os campos devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            Conexao conexao = new Conexao();
            conn = conexao.getConnection();
            CadastrarPedidoDAO dao = new CadastrarPedidoDAO(conn);
            
            if (dao.verificarEmailExistente(email)) {
                JOptionPane.showMessageDialog(tela3, 
                    "Este e-mail já está com um pedido. Por favor, utilize outro.", 
                    "Email Duplicado", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CadastrarPedidoModel pedido = new CadastrarPedidoModel(email, nome);
            dao.inserir(pedido);
            
            JOptionPane.showMessageDialog(tela3, 
                    "Pedido cadastrado com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            limparCampos();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(tela3, "Erro ao cadastrar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao(conn);
        }
    }

    public void EditarPedido(){
        String emailExistente = tela3.getTxtCadastrarPedidoEmailEditar().getText().trim(); // AGORA USA O NOVO CAMPO
        String itemAntigo = tela3.getTxtEditarCadastrarPedidoNomeExistente1().getText().trim();
        String itemNovo = tela3.getTxtEditarCadastrarPedidoNomeSubstituicao().getText().trim();

        if (emailExistente.isEmpty() || itemAntigo.isEmpty() || itemNovo.isEmpty()) {
            JOptionPane.showMessageDialog(tela3, "Todos os campos de edição devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            Conexao conexao = new Conexao();
            conn = conexao.getConnection();
            CadastrarPedidoDAO dao = new CadastrarPedidoDAO(conn);
            
            if (!dao.verificarEmailExistente(emailExistente)) {
                JOptionPane.showMessageDialog(tela3, 
                    "E-mail não encontrado para edição.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Busca o pedido atual para verificar se o item antigo coincide
            CadastrarPedidoModel pedidoAtual = dao.buscarPorEmail(emailExistente);
            if (pedidoAtual != null && !pedidoAtual.getNome().equals(itemAntigo)) {
                JOptionPane.showMessageDialog(tela3, 
                    "O item antigo não coincide com o pedido atual.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            dao.atualizarPedido(emailExistente, itemNovo);
            
            JOptionPane.showMessageDialog(tela3, 
                    "Pedido editado com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            limparCamposEdicao();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(tela3, "Erro ao editar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao(conn);
        }
    }

    public void ExcluirPedido(){
        String email = tela3.getTxtCadastrarPedidoEmail().getText().trim();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(tela3, "Informe o email do pedido a ser excluído.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(tela3, 
            "Tem certeza que deseja excluir o pedido do email: " + email + "?",
            "Confirmação de Exclusão",
            JOptionPane.YES_NO_OPTION);

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        Connection conn = null;
        try {
            Conexao conexao = new Conexao();
            conn = conexao.getConnection();
            CadastrarPedidoDAO dao = new CadastrarPedidoDAO(conn);
            
            if (!dao.verificarEmailExistente(email)) {
                JOptionPane.showMessageDialog(tela3, 
                    "E-mail não encontrado para exclusão.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            dao.excluirPedido(email);
            
            JOptionPane.showMessageDialog(tela3, 
                    "Pedido excluído com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            limparCampos();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(tela3, "Erro ao excluir pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao(conn);
        }
    }

    private void limparCampos() {
        tela3.getTxtCadastrarPedidoEmail().setText("");
        tela3.getTxtCadastrarPedidoNome().setText("");
    }

    private void limparCamposEdicao() {
        tela3.getTxtCadastrarPedidoEmailEditar().setText("");
        tela3.getTxtEditarCadastrarPedidoNomeExistente1().setText("");
        tela3.getTxtEditarCadastrarPedidoNomeSubstituicao().setText("");
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