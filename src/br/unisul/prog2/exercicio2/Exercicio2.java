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
        String cadastro = "";
        String pedido = "";
        String relatorio = "";

        String opcao = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Cadastros" + "\n"
                + "2 - Pedidos" + "\n"
                + "3 - Relatórios");

        switch (opcao) {
            case "1":
                cadastro = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Clientes" + "\n"
                        + "2 - Produtos");

                switch (cadastro) {
                    case "1":
                        String option1 = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Inserir" + "\n"
                        + "2 - Remover" + "\n"
                        + "3 - Alterar" );
                        
                        switch (option1) {
                            case "1":
                                break;
                            case "2":
                                break;
                            case "3":
                                break;
                        }
                        
                        break;

                    case "2":
                        String option2 = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Inserir" + "\n"
                        + "2 - Remover" + "\n"
                        + "3 - Alterar" );
                        
                        switch (option2) {
                            case "1":
                                break;
                            case "2":
                                break;
                            case "3":
                                break;
                        }
                        
                        break;
                    default:
                        System.out.println("erro");
                        break;
                }
                break;

            case "2":
                pedido = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Efetuar Pedido" + "\n"
                        + "2 - Consultar Pedido");

                switch (pedido) {
                    case "1":
//                       Efetuar Pedidos
                        break;

                    case "2":
//                       Listar Produtos
                        break;

                    default:
                        System.out.println("erro");
                }
                break;

            case "3":
                relatorio = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Listagem de Clientes" + "\n"
                        + "2 - Listagem de Produtos \n"
                        + "3 - Listagem de Pedidos");

                switch (relatorio) {
                    case "1":
//                       Listagem de Clientes
                        break;

                    case "2":
//                       Listagem de Produtos
                        break;
                    case "3":
//                        Listagem de Pedidos
                        break;
                    default:
                        System.out.println("erro");
                }
                break;

            default:
                System.out.println("Digite uma opção valida!");
        }

    }
}
