package model;

public class AvaliacaoModel {
    private String emailAvaliacao;
    private String descricao;
    private String nota;

    public AvaliacaoModel(String emailAvaliacao, String descricao, String nota) {
        this.emailAvaliacao = emailAvaliacao;
        this.descricao = descricao;
        this.nota = nota;
    }

    public String getTxtAvaliacaoEmail() { return emailAvaliacao; }
    public String getDescricao() { return descricao; }
    public String getNota() { return nota; }
}