package controller;

import dao.BebidaDAO;
import dao.Conexao;
import java.sql.Connection;
import javax.swing.JOptionPane;
import view.Bebida;
import model.BebidaModel;

public class ControleBebida {

    private Bebida view;

    public ControleBebida(Bebida view) {
        this.view = view;
    }

    public void buscarInformacoesDoBebida(String nomeBebida) {
        if (nomeBebida == null || nomeBebida.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, digite o nome de uma bebida.");
            return;
        }

        try (Connection conexao = new Conexao().getConnection()) {
            BebidaDAO dao = new BebidaDAO(conexao);
            BebidaModel bebida = dao.buscarPorNome(nomeBebida.trim());

            if (bebida != null) {
                view.getjTextArea1().setText(
                    "Bebida: " + bebida.getNome() + "\n\n" +
                    "Informações:\n" + bebida.getInformacoes()
                );
            } else {
                view.getjTextArea1().setText(
                    "Bebida '" + nomeBebida + "' não encontrado.\n\n" +
                    "Sugestões:\n- Verifique a ortografia\n- Tente um termo mais geral"
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao buscar bebida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
