package controller;

import dao.HistoricoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import model.HistoricoModel;
import view.Historico;

public class ControleHistorico {

    private Historico tela;

    public ControleHistorico(Historico tela) {
        this.tela = tela;
    }

    public void buscarHistorico(String email) {
        HistoricoDAO dao = new HistoricoDAO();
        List<HistoricoModel> lista = dao.buscarPorEmail(email);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Nenhum alimento encontrado"
                    + " para esse email.");
            tela.getTxtResultadoSalvoNaLista().setText("");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (HistoricoModel item : lista) {
            sb.append("- ").append(item.getAlimento()).append("\n");
        }
        tela.getTxtResultadoSalvoNaLista().setText(sb.toString());
    }
}