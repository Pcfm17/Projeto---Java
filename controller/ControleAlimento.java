package controller;

import dao.AlimentoDAO;
import dao.Conexao;
import java.sql.Connection;
import javax.swing.JOptionPane;
import model.AlimentoModel;
import view.Alimento;

public class ControleAlimento {

    private Alimento view;

    public ControleAlimento(Alimento view) {
        this.view = view;
    }

    public void buscarInformacoesDoAlimento(String nomeAlimento) {
        if (nomeAlimento == null || nomeAlimento.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, digite o nome de um alimento.");
            return;
        }

        try (Connection conexao = new Conexao().getConnection()) {
            AlimentoDAO dao = new AlimentoDAO(conexao);
            AlimentoModel alimento = dao.buscarPorNome(nomeAlimento.trim());

            if (alimento != null) {
                view.getjTextArea1().setText(
                    "Alimento: " + alimento.getNome() + "\n\n" +
                    "Informações:\n" + alimento.getInformacoes()
                );
            } else {
                view.getjTextArea1().setText(
                    "Alimento '" + nomeAlimento + "' não encontrado.\n\n" +
                    "Sugestões:\n- Verifique a ortografia\n- Tente um termo mais geral"
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao buscar alimento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
