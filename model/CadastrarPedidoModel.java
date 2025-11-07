/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author paulo
 */
public class CadastrarPedidoModel {
    private String email;
    private String nome;

    // Construtor vazio adicionado
    public CadastrarPedidoModel() {
    }

    public CadastrarPedidoModel(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public String getEmail() { return email;}
    public String getNome() { return nome; }

    public void setEmail(String email) { this.email = email; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return "CadastrarPedidoModel{" + "email=" + email + ", nome=" + nome + '}';
    }
    
}
