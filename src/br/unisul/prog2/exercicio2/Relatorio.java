/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisul.prog2.exercicio2;

import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel
 */
public class Relatorio {
    public static void Relatorios() throws SQLException {
        try {
            String relatorio = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Listagem de Clientes" + "\n"
                    + "2 - Listagem de Produtos \n"
                    + "3 - Listagem de Pedidos \n"
                    + "4 - Voltar");

            switch (relatorio) {
                case "1":
                    ListagemCliente();
                    break;
                case "2":
                    ListagemProduto();
                    break;
                case "3":
                    ListagemPedido();
                    break;
                case "4":
                    Exercicio2.Exercicio2();
                default:
                    System.out.println("erro");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Listar Pedido 
    private static void ListagemPedido() throws SQLException, ParseException {
        try {
            String optionlist3 = JOptionPane.showInputDialog("Insira uma opcao:\n"
                    + "1 - Listar por ID \n"
                    + "2 - Listar todos \n"
                    + "3 - Voltar");
            switch (optionlist3) {
                case "1": // Listar por ID
                    Pedido.ListarPedidoId();
                case "2": // Listar todos
                    Pedido.ListarPedidoTodos();
                    break;
                case "3": // Voltar
                    Pedido.Pedidos();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Listar Cliente
    private static void ListagemCliente() throws SQLException {
        try {
            String optionlist = JOptionPane.showInputDialog("Insira uma opcao:\n"
                    + "1 - Listar por ID \n"
                    + "2 - Listar todos \n"
                    + "3 - Voltar");
            switch (optionlist) {
                case "1":
                    Cliente.ListarClienteId();
                    break;
                case "2":
                    Cliente.ListarClienteTodos();
                    break;
                case "3":
                    Relatorios();
                    break;

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Listar Produto
    private static void ListagemProduto() throws SQLException {
        try {
            String optionlist2 = JOptionPane.showInputDialog("Insira uma opcao:\n"
                    + "1 - Listar por ID \n"
                    + "2 - Listar todos \n"
                    + "3 - Voltar");
            switch (optionlist2) {
                case "1": // Listar por ID
                    Produto.ListarProdutoId();
                    break;
                case "2": // Listar todos
                    Produto.ListarProdutoTodos();
                    break;
                case "3":
                    Relatorios();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

}
