package model;

public class PesquisaModel {
    private String email;
    private String alimento;

    public PesquisaModel(String email, String alimento) {
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
