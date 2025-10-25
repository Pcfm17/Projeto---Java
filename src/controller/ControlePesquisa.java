package controller;

import dao.AlunoDAO;
import dao.Conexao;
import java.sql.Connection;
import javax.swing.JOptionPane;
import model.Aluno;
import view.Pesquisa;

public class ControlePesquisa {
    
    private Pesquisa telaPesquisa;
    
    public ControlePesquisa(Pesquisa telaPesquisa) {
        this.telaPesquisa = telaPesquisa;
    }
    
    public void buscarUsuario() {
        String email = telaPesquisa.getTxtProcurando().getText();
        
        // 1. Validação simples
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(telaPesquisa, 
                    "Digite um email para pesquisar.", "Atenção", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = new Conexao().getConnection();
            AlunoDAO alunoDAO = new AlunoDAO(conn);
            
            // 2. Chama o método do DAO (que criaremos no próximo passo)
            Aluno aluno = alunoDAO.buscarPorEmail(email);
            
            // 3. Exibe o resultado na tela
            if (aluno != null) {
                String resultado = "Email: " + aluno.getEmail() + "\n"
                                 + "Nome: " + aluno.getNome() + "\n"
                                 + "Gênero: " + aluno.getGenero();
                telaPesquisa.getJTextArea1().setText(resultado);
            } else {
                telaPesquisa.getJTextArea1().setText("Usuário com o email '" + 
                        email + "' não encontrado.");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaPesquisa, "Erro na pesquisa: " + 
                    e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    // Ignora
                }
            }
        }
    }
}