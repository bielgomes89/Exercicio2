package br.unisul.prog2.exercicio2;

import static br.unisul.prog2.exercicio2.DatabaseService.getConnPostgres;
import javax.swing.JOptionPane;
import static br.unisul.prog2.exercicio2.EmpresaTxtReader.importar;
import static br.unisul.prog2.exercicio2.EmpresaBD.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;

public class Exercicio2 {

    public static void main(String[] args) {
        int menu = Integer.parseInt(JOptionPane.showInputDialog("Informe a opção desejada: \n"
                                                                + "1 - Exercicio 1; \n"
                                                                + "2 - Exercicio 2. \n"));
        if (menu == 1){
            Exercicio1();
            
        }else{
            Exercicio2();
        }
    }
    
    public static void Exercicio1() {
        getConnPostgres();
        EmpresaBD database = new EmpresaBD();
        
        
        int menu = Integer.parseInt(JOptionPane.showInputDialog("Informe a opção desejada: \n"
                + "1 - Empresa Banco;\n"
                + "2 - Importar arquivo txt para banco.\n"));
        
        switch (menu) {
            case 1:
                database.empresa_Insert();
                break;
            case 2:
                ArrayList empresas = new ArrayList();
                Scanner ler = new Scanner(System.in);
                importar(empresas);
                database.empresa_Insert();
                
                break;
            default:
                JOptionPane.showMessageDialog(null, "OPÇÃO INVÁLIDA");
                break;
        
        }
    
    }
    
    public static void Exercicio2() {
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
