package br.unisul.prog2.exercicio2;

import static br.unisul.prog2.exercicio2.DatabaseService.getConnPostgres;
import javax.swing.JOptionPane;
import static br.unisul.prog2.exercicio2.EmpresaTxtReader.importar;
import static br.unisul.prog2.exercicio2.EmpresaBD.*;
import static java.lang.String.format;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Exercicio2 {

    public static void main(String[] args) {
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
        getConnPostgres();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Cliente clienteDB = new Cliente();
        Pedido pedidoDB = new Pedido();
        Produto produtoDB = new Produto();
        String cadastro = "";
        String pedido = "";
        String relatorio = "";

        try {

            String opcao = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Cadastros" + "\n"
                    + "2 - Pedidos" + "\n"
                    + "3 - Relatórios");

            switch (opcao) {
                case "1": //Cadastros
                    cadastro = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Clientes" + "\n"
                            + "2 - Produtos");

                    switch (cadastro) {
                        case "1": // Cadastro de clientes
                            int id = 0;
                            String nome = "";
                            String endereco = "";
                            String telefone = "";
                            String cpf = "";
                            String email = "";
                            String estadoCivil = "";
                            String dataNascimento = "";
                            String msg = "";
                            DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                            DatabaseService.getConnPostgres();
                            String option1 = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Inserir cliente" + "\n"
                                    + "2 - Remover cliente" + "\n"
                                    + "3 - Alterar cliente");

                            switch (option1) {
                                case "1": //Inserir Cliente
                                    nome = JOptionPane.showInputDialog("Informe o nome do cliente: ");
                                    endereco = JOptionPane.showInputDialog("Informe o endereço do cliente: ");
                                    telefone = JOptionPane.showInputDialog("Informe o telefone do cliente: ");
                                    cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
                                    email = JOptionPane.showInputDialog("Informe o email do cliente: ");
                                    estadoCivil = JOptionPane.showInputDialog("Informe o Estado Civil do cliente: ");
                                    dataNascimento = JOptionPane.showInputDialog("Informe a data de nascimento do cliente: ");

                                    clienteDB.cadastroCliente(nome, endereco, telefone, cpf, email, estadoCivil, dataNascimento);

                                    java.util.Date dtnasc = formatter.parse(dataNascimento);
                                    java.sql.Date sqlDate = new java.sql.Date(dtnasc.getTime());

                                    java.sql.Date data = new java.sql.Date(formatter.parse(dataNascimento).getTime());

                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("INSERT INTO CLIENTES (nome, cpf, endereco, telefone, dtnascimento, email, senha) VALUES( ?, ?, ?, ?, ?, ?, ?)");

                                    st.setString(1, nome);
                                    st.setString(2, endereco);
                                    st.setString(3, telefone);
                                    st.setString(4, cpf);
                                    st.setDate(5, data);
                                    st.setString(6, email);
                                    st.setString(7, estadoCivil);

                                    rs = st.executeQuery();
                                    System.out.println(st.executeQuery());

                                    break;
                                case "2": // Remover Cliente
                                    cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM CLIENTES WHERE cpf = ?");
                                    st.setString(1, cpf);
                                    if (st.executeQuery() != null) {
                                        st = conn.prepareStatement("DELETE FROM CLIENTES WHERE cpf = ?");
                                        st.setString(1, cpf);
                                        JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                                        rs = st.executeQuery();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "CLIENTE NÃO ENCONTRADO.");
                                    }

                                    break;
                                case "3": // Alterar Cliente
                                    id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do Cliente:"));
                                    nome = JOptionPane.showInputDialog("Informe o nome do cliente: ");
                                    endereco = JOptionPane.showInputDialog("Informe o endereço do cliente: ");
                                    telefone = JOptionPane.showInputDialog("Informe o telefone do cliente: ");
                                    cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
                                    email = JOptionPane.showInputDialog("Informe o email do cliente: ");
                                    estadoCivil = JOptionPane.showInputDialog("Informe o Estado Civil do cliente: ");
                                    dataNascimento = JOptionPane.showInputDialog("Informe a data de nascimento do cliente: ");
                                    dtnasc = formatter.parse(dataNascimento);
                                    sqlDate = new java.sql.Date(dtnasc.getTime());

                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("UPDATE CLIENTES SET nome = ?, cpf = ?, endereco = ?, telefone = ?, dtnascimento = ?, email = ?, senha = ? WHERE codcli = ?");
                                    st.setString(1, nome);
                                    st.setString(2, endereco);
                                    st.setString(3, telefone);
                                    st.setString(4, cpf);
                                    st.setDate(5, sqlDate);
                                    st.setString(6, email);
                                    st.setString(7, estadoCivil);
                                    st.setInt(8, id);

                                    rs = st.executeQuery();
                                    break;
                            }

                            break;

                        case "2": // Cadastro de produtos
                            String option2 = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Inserir Produto" + "\n"
                                    + "2 - Remover produto" + "\n"
                                    + "3 - Alterar produto");

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

                case "2": // Pedidos
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

                case "3": // Relatórios
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

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {

            }

        }
    }
}
