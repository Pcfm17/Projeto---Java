package model;

import java.math.BigDecimal;

public class CadastrarPedidoModel {
    private String id;
    private String alimento;
    private BigDecimal preco;
    
    public CadastrarPedidoModel() {
    }
    
    public CadastrarPedidoModel(String id, String alimento, BigDecimal preco) {
        this.id = id;
        this.alimento = alimento;
        this.preco = preco;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}