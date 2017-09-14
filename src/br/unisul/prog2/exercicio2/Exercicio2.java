package br.unisul.prog2.exercicio2;

import static br.unisul.prog2.exercicio2.DatabaseService.getConnPostgres;
import javax.swing.JOptionPane;
import static br.unisul.prog2.exercicio2.EmpresaTxtReader.importar;
import java.util.ArrayList;
import java.util.Scanner;

public class Exercicio2 {

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        int menu = Integer.parseInt(JOptionPane.showInputDialog("Informe a opção desejada: \n"
                + "1 - Exercicio 1; \n"
                + "2 - Exercicio 2. \n"));
        if (menu == 1) {
            Exercicio1();

        } else {
            Exercicio2();
        }
    }

    public static void Exercicio1() {
        getConnPostgres();
        EmpresaBD database = new EmpresaBD();

        int menu = Integer.parseInt(JOptionPane.showInputDialog("Informe a opção desejada: \n"
                + "1 - Empresa Banco" + "\n"
                + "2 - Importar arquivo txt para banco") + "\n"
                + "3 - Voltar");

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
            case 3:
                menu();
            default:
                JOptionPane.showMessageDialog(null, "OPÇÃO INVÁLIDA");
                break;

        }

    }

    public static void Exercicio2() {
        getConnPostgres();
        DatabaseService.getConnPostgres();

        try {

            String opcao = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Cadastros" + "\n"
                    + "2 - Pedidos" + "\n"
                    + "3 - Relatórios" + "\n"
                    + "4 - Voltar");

            switch (opcao) {
                case "1": //Cadastros
                    Cadastro.Cadastros();
                    break;
                case "2": // Pedidos
                    Pedido.Pedidos();
                    break;
                case "3": // Relatórios
                    Relatorio.Relatorios();
                    break;
                case "4":
                    menu();
                default:
                    System.out.println("Digite uma opção valida!");
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
