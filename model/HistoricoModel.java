package model;

public class HistoricoModel {
    private String email;
    private String alimento;

    public HistoricoModel(String email, String alimento) {
        this.email = email;
        this.alimento = alimento;
    }

    public String getEmail() {
        return email;
    }

    public String getAlimento() {
        return alimento;
    }
}
