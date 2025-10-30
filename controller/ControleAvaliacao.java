package controller;

import dao.AlunoDAO;
import dao.Conexao;
import dao.ConexaoAvaliacao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Aluno;
import view.Cadastro;

public class ControleAvaliacao {
    /*private Cadastro tela3;

    // REMOVIDA A LINHA COM ERRO: // ControleCadastro c = new ControleCadastro(this);

    public ControleAvaliacao(Cadastro tela3){
        this.tela3 = tela3;
    }

    public void salvarAvaliacao(){
        String email = tela3.getTxtCadastroEmail().getText();
        //String descricao = tela3.getTxtAreaAvaliacao().getText();
        //String nota = tela3.rb5().isSelected() ? "★" : 
                        //"★★" : "★★★" : "★★★★" : "★★★★★";

        // VALIDAÇÃO INICIAL DE CAMPOS VAZIOS
        if (email.isEmpty() || descricao.isEmpty() || nota.isEmpty()) {
            JOptionPane.showMessageDialog(tela3, "Todos os campos devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ConexaoAvaliacao conexaoAvaliacao = new ConexaoAvaliacao();
        Connection conn = null;
        
        try {
            conn = conexaoAvaliacao.getConnection();
            AlunoDAO dao = new AlunoDAO(conn);
            
            // *** VERIFICAÇÃO DE DUPLICIDADE ***
            if (dao.verificarEmailExistente(email)) {
                JOptionPane.showMessageDialog(tela3, 
                    "Este e-mail já está cadastrado. Por favor, utilize outro.", 
                    "Email Duplicado", 
                    JOptionPane.ERROR_MESSAGE);
                return; // Interrompe a inserção
            }
            
            // SE NÃO EXISTE, INSERE
            Aluno aluno = new Aluno(email, descricao, nota);
            dao.inserir(aluno);
            
            JOptionPane.showMessageDialog(tela3, 
                    "Registrado a avaliação com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(tela3, "Erro ao registrar a sua avaliação: " + ex.getMessage(), "Erro",
                                         JOptionPane.ERROR_MESSAGE);
        } finally {
            // Fecha a conexão
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }*/
}