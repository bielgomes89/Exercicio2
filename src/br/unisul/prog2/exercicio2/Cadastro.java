/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisul.prog2.exercicio2;

import static br.unisul.prog2.exercicio2.Exercicio2.Exercicio2;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel
 */
public class Cadastro {
    public static void Cadastros() throws ParseException, SQLException {
        try {
            String cadastro = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Clientes" + "\n"
                    + "2 - Produtos" + "\n"
                    + "3 - Voltar");

            switch (cadastro) {
                case "1": // Cadastro de clientes
                    Cliente.CadastroCliente();
                case "2": // Cadastro de produtos
                    Produto.CadastroProduto();
                    break;
                case "3":
                    Exercicio2();
                    break;
                default:
                    System.out.println("erro");
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
}
