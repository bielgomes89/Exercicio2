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
        String opcao = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Cadastros" + "\n"
                + "2 - Pedidos" + "\n"
                + "3 - Relatórios");

        switch (opcao) {
            case "1":
                int cadastro = Integer.parseInt(JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Clientes" + "\n"
                        + "2 - Produtos"));

                switch (cadastro) {
                    case 1:
//                        Clientes
                        break;

                    case 2:
//                        Produtos
                        break;
                }

            case "2":
                int pedido = Integer.parseInt(JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Efetuar Pedido" + "\n"
                        + "2 - Consultar Pedido"));

                switch (pedido) {
                    case 1:
//                       Efetuar Pedidos
                        break;

                    case 2:
//                       Listar Produtos
                        break;
                }
                
            case 3:
                int relatorio = Integer.parseInt(JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Listagem de Clientes" + "\n"
                        + "2 - Listagem de Produtos \n"
                        + "3 - Listagem de Pedidos"));

                switch (relatorio) {
                    case 1:
//                       Listagem de Clientes
                        break;

                    case 2:
//                       Listagem de Produtos
                        break;
                    case 3:
//                        Listagem de Pedidos
                        break;
                }
                
            default:
                System.out.println("Digite uma opção valida!");
        }

    }
}
