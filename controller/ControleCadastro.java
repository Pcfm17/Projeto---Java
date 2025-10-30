package controller;

import dao.AlunoDAO;
import dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Aluno;
import view.Cadastro;

public class ControleCadastro {
    private Cadastro tela3;

    // REMOVIDA A LINHA COM ERRO: // ControleCadastro c = new ControleCadastro(this);

    public ControleCadastro(Cadastro tela3){
        this.tela3 = tela3;
    }

    public void salvarAluno(){
        String email = tela3.getTxtCadastroEmail().getText();
        String nome = tela3.getTxtCadastroNome().getText();
        String senha = tela3.getTxtCadastroSenha().getText();
        String genero = tela3.getRbMasculino().isSelected() ? "Masculino" : 
                        "Feminino";

        // VALIDAÇÃO INICIAL DE CAMPOS VAZIOS
        if (email.isEmpty() || nome.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(tela3, "Todos os campos devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conexao conexao = new Conexao();
        Connection conn = null;
        
        try {
            conn = conexao.getConnection();
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
            Aluno aluno = new Aluno(email, nome, senha, genero);
            dao.inserir(aluno);
            
            JOptionPane.showMessageDialog(tela3, 
                    "Usuário cadastrado com sucesso! Agora acesse o login para ter o acesso", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(tela3, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro",
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
    }
}