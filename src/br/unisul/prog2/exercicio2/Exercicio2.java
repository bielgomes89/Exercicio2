package br.unisul.prog2.exercicio2;

import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel.gomes2
 */
public class Exercicio2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int opcao = Integer.parseInt(JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Deletar Jogo" + "\n" 
        + "2 - Inserir Jogo" + "\n"
        + "3 - Seleção de Jogos"));
    }
    
}
