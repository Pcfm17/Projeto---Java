package controller;

import dao.AlunoDAO;
import dao.Conexao;
import java.sql.Connection;
import javax.swing.JOptionPane;
import view.Login;

public class ControleLogin {
    private Login telaLogin;

    public ControleLogin(Login telaLogin) {
        this.telaLogin = telaLogin;
    }

    public void autenticar() {
        String email = telaLogin.getTxtEmail().getText();
        // se quiser usar campo senha depois, mude aqui
        String senha = new String(telaLogin.getTxtSenha().getPassword()); 

        try {
            Connection conn = new Conexao().getConnection();
            AlunoDAO alunoDAO = new AlunoDAO(conn);

            boolean autenticado = alunoDAO.verificarLogin(email, senha);

            if (autenticado) {
                JOptionPane.showMessageDialog(telaLogin, "Login realizado "
                        + "com sucesso!");
                view.JanelaInicial telaInicial = new view.JanelaInicial();
                telaInicial.setVisible(true);
                telaLogin.dispose();
            } else {
                JOptionPane.showMessageDialog(telaLogin, 
                        "Email ou senha incorretos!", "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaLogin, 
                    "Erro ao tentar autenticar: " + e.getMessage(), "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
