package model;

import com.sun.jdi.connect.spi.Connection;

public class Aluno {
    private String email;
    private String nome;
    private String senha;
    private String genero;
    
    public Aluno() {
    }

    public Aluno(String email, String nome, String senha, String genero) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.genero = genero;
    }

    // GETTERS
    public String getEmail() { return email; }
    public String getNome() { return nome; }
    public String getSenha() { return senha; }
    public String getGenero() { return genero; }

    // SETTERS (Adicionados para que o AlunoDAO.buscarPorEmail possa 
    //preencher o objeto)
    public void setEmail(String email) { this.email = email; }
    public void setNome(String nome) { this.nome = nome; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override
    public String toString() {
        return "Aluno{" + "email=" + email + ", nome=" + nome + ", senha=" + 
                senha + ", genero=" + genero + '}';
    }
    
    private String pedidoId;
    private String descricao;
    private String nota;

    public Aluno(String pedidoId, String descricao, String nota) {
        this.pedidoId = pedidoId;
        this.descricao = descricao;
        this.nota = nota;
    }

    public String getPedidoId() { return pedidoId; }
    public String getDescricao() { return descricao; }
    public String getNota() { return nota; }
}
