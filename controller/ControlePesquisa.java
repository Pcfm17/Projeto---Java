package controller;

import dao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import view.Pesquisa;

public class ControlePesquisa {
    private Pesquisa tela;

    public ControlePesquisa(Pesquisa tela) {
        this.tela = tela;
    }

    // Método chamado ao clicar no botão "Pesquisar"
    public void salvandoPesquisaDoAlimento() {
        String email = tela.getTxtPesquisaParaSalvarEmail().getText().trim();
        String alimento = tela.getTxtProcurandoAlimento().getText().trim();

        if (email.isEmpty() || alimento.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha o email e o alimento antes de salvar.");
            return;
        }

        try (Connection conexao = new Conexao().getConnection()) {
            String sql = "INSERT INTO salvandopesquisa (email, alimento) VALUES (?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, alimento);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Pesquisa salva com sucesso!");
            tela.getJTextArea1().setText("Alimento pesquisado: " + alimento);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar pesquisa: " + e.getMessage());
        }
    }
}