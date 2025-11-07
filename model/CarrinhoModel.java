/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author paulo
 */
public class CarrinhoModel {
    private String email;
    private String alimento;
    
    public CarrinhoModel() {
    }
    
    public CarrinhoModel(String email, String alimento) {
        this.email = email;
        this.alimento = alimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }
    
    @Override
    public String toString() {
        return alimento;
    }
}