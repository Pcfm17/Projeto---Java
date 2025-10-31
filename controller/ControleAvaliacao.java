package controller;

import dao.AlunoDAO;
import dao.AvaliacaoDAO;
import dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Aluno;
import model.AvaliacaoModel;
import view.Avaliacao;

public class ControleAvaliacao {
    private Avaliacao tela3;

    // REMOVIDA A LINHA COM ERRO: // ControleCadastro c = new ControleCadastro(this);

    public ControleAvaliacao(Avaliacao tela3){
        this.tela3 = tela3;
    }

    public void salvarAvaliacao() {
        String email1 = tela3.getTxtAvaliacaoEmail().getText();
        String nota = tela3.getRb1().isSelected() ? "★" :
                      tela3.getRb2().isSelected() ? "★★" :
                      tela3.getRb3().isSelected() ? "★★★" :
                      tela3.getRb4().isSelected() ? "★★★★" :
                      tela3.getRb5().isSelected() ? "★★★★★" : "";
        String descricao = tela3.getTxtAreaAvaliacao().getText();

        if (email1.isEmpty() || nota.isEmpty() || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(tela3, "Todos os campos devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conexao conexaoAvaliacao = new Conexao();
        Connection conn = null;

        try {
            conn = conexaoAvaliacao.getConnection();

            // Verifica se o e-mail existe no cadastro de usuários
            AlunoDAO alunoDAO = new AlunoDAO(conn);
            if (!alunoDAO.verificarEmailExistente(email1)) {
                JOptionPane.showMessageDialog(tela3,
                    "Este e-mail não está cadastrado. Faça o login antes de avaliar.",
                    "Erro de E-mail",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Se o email existe, salva a avaliação
            AvaliacaoModel avaliacao = new AvaliacaoModel(email1, descricao, nota);
            AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(conn);
            avaliacaoDAO.inserir(avaliacao);

            JOptionPane.showMessageDialog(tela3,
                    "Avaliação registrada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(tela3, "Erro ao registrar avaliação: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }
    }

}