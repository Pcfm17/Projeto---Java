package model;

public class AvaliacaoModel {
    private String pedidoId;
    private String descricao;
    private String nota;

    public AvaliacaoModel(String pedidoId, String descricao, String nota) {
        this.pedidoId = pedidoId;
        this.descricao = descricao;
        this.nota = nota;
    }

    public String getPedidoId() { return pedidoId; }
    public String getDescricao() { return descricao; }
    public String getNota() { return nota; }
}