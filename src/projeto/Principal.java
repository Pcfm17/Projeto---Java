/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projeto;

import view.Cadastro;

/**
 *
 * SELECT * FROM public.usuarios;
SELECT * FROM usuarios WHERE email = 'paulo@gmail.com' AND senha = '123';
 * @author unifpmotta
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cadastro janelaCadastro = new Cadastro();
        janelaCadastro.setVisible(true);
    }
    
}
