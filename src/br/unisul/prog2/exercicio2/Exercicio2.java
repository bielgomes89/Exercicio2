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
        int codigoCliente = 0;
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
                    String msg = "";
                    int id = 0;
                    String nome = "";
                    String marca = "";
                    float valor = 0;
                    double valorTotal = 0.0;
                    int qtd = 0;
                    String endereco = "";
                    String telefone = "";
                    String cpf = "";
                    String email = "";
                    String estadoCivil = "";
                    String dataNascimento = "";
                    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                    DatabaseService.getConnPostgres();
                    cadastro = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Clientes" + "\n"
                            + "2 - Produtos");

                    switch (cadastro) {
                        case "1": // Cadastro de clientes
                            
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

                                    break;
                                case "2": // Remover Cliente
                                    cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM CLIENTES WHERE cpf = ?");
                                    st.setString(1, cpf);
                                    rs = st.executeQuery();
                                    if (rs.next()) {
                                        st = conn.prepareStatement("DELETE FROM CLIENTES WHERE cpf = ?");
                                        st.setString(1, cpf);
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
                                case "1": // Inserir Produto
                                    id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do produto: "));
                                    id = Integer.parseInt(String.format("%06d", id));
                                    nome = JOptionPane.showInputDialog("Informe o nome do produto: ");
                                    marca = JOptionPane.showInputDialog("Informe a marca do produto: ");
                                    valor = Float.parseFloat(JOptionPane.showInputDialog("Informe o valor do produto: "));
                                    qtd = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade do produto: "));
                                    produtoDB.cadastroPedido(nome, marca, valor, qtd);
                                    
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("INSERT INTO PRODUTOS (cod_produto, nome_produto, marca, valor_unitario, quantidade) VALUES(?, ?, ?, ?, ?)");
                                    
                                    st.setInt(1, id);
                                    st.setString(2, nome);
                                    st.setString(3, marca);
                                    st.setFloat(4, valor);
                                    st.setInt(5, qtd);

                                    rs = st.executeQuery();
                                    break;
                                case "2":
                                    id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do produto: "));
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM PRODUTOS WHERE cod_produto = ?");
                                    st.setInt(1, id);
                                    rs = st.executeQuery();
                                    if (rs.next()) {
                                        st = conn.prepareStatement("DELETE FROM PRODUTOS WHERE cod_produto = ?");
                                        st.setInt(1, id);
                                        rs = st.executeQuery();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "PRODUTO NÃO ENCONTRADO.");
                                    }
                                    
                                    break;
                                case "3":
                                    id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do produto: "));
                                    nome = JOptionPane.showInputDialog("Informe o nome do produto: ");
                                    marca = JOptionPane.showInputDialog("Informe a marca do produto: ");
                                    valor = Float.parseFloat(JOptionPane.showInputDialog("Informe o valor do produto: "));
                                    qtd = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade do produto: "));

                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("UPDATE PRODUTOS SET nome_produto = ?, marca = ?, valor_unitario = ?, quantidade = ? WHERE cod_produto = ?");
                                    st.setString(1, nome);
                                    st.setString(2, marca);
                                    st.setFloat(3, valor);
                                    st.setInt(4, qtd);
                                    st.setInt(5, id);

                                    rs = st.executeQuery();
                                    
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
                            String optionlist = JOptionPane.showInputDialog("Insira uma opcao:\n"
                                                                    + "1 - Listar por ID \n"
                                                                    + "2 - Listar todos");
                            switch(optionlist) {
                                case "1":
                                    //Listagem de Clientes por ID
                                    msg = "";
                                    id = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID de um cliente: "));
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM CLIENTES WHERE codcli = ?");
                                    st.setInt(1, id);

                                    rs = st.executeQuery();
                                    while (rs.next()) {
                                        id = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                                        nome = rs.getString("nome") == null ? "" : rs.getString("nome");
                                        endereco = rs.getString("endereco") == null ? "" : rs.getString("endereco");
                                        telefone = rs.getString("telefone") == null ? "" : rs.getString("telefone");
                                        cpf = rs.getString("cpf") == null ? "" : rs.getString("cpf");
                                        java.sql.Date data = rs.getDate("dtnascimento");
                                        email = rs.getString("email") == null ? "" : rs.getString("email");
                                        estadoCivil = rs.getString("senha") == null ? "" : rs.getString("senha");

                                        msg +=  "ID: " + id + "\n"
                                                + "Nome: " + nome + "\n"
                                                + "CPF: " + cpf + "\n"
                                                + "Endereco: " + endereco + "\n"
                                                + "Telefone: " + telefone + "\n"
                                                + "Dt. Nasc: " + data + "\n"
                                                + "Email: " + email + "\n"
                                                + "Estado Civil: " + estadoCivil + "\n\n";
                                        System.out.println(msg);

                                    }
                            
                                    if(!"".equals(msg)) {
                                        JOptionPane.showMessageDialog(null, msg);
                                    } else { 
                                        JOptionPane.showMessageDialog(null, "CADASTRO NÃO ENCONTRADO.");
                                    }
                                    break;
                                    
                                case "2":
                                    msg = "";
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM CLIENTES");
                                    rs = st.executeQuery();
                                    while (rs.next()) {
                                        id = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                                        nome = rs.getString("nome") == null ? "" : rs.getString("nome");
                                        endereco = rs.getString("endereco") == null ? "" : rs.getString("endereco");
                                        telefone = rs.getString("telefone") == null ? "" : rs.getString("telefone");
                                        cpf = rs.getString("cpf") == null ? "" : rs.getString("cpf");
                                        java.sql.Date data = rs.getDate("dtnascimento");
                                        email = rs.getString("email") == null ? "" : rs.getString("email");
                                        estadoCivil = rs.getString("senha") == null ? "" : rs.getString("senha");

                                        msg +=  "ID: " + id + "\n"
                                                + "Nome: " + nome + "\n"
                                                + "CPF: " + cpf + "\n"
                                                + "Endereco: " + endereco + "\n"
                                                + "Telefone: " + telefone + "\n"
                                                + "Dt. Nasc: " + data + "\n"
                                                + "Email: " + email + "\n"
                                                + "Estado Civil: " + estadoCivil + "\n\n";
                                        System.out.println(msg);

                                    }
                            
                                    if(!"".equals(msg)) {
                                        JOptionPane.showMessageDialog(null, msg);
                                    } else { 
                                        JOptionPane.showMessageDialog(null, "CADASTROS NÃO ENCONTRADOS.");
                                    }
                                    break;
                            
                            }
                            break;

                        case "2":
//                       Listagem de Produtos
                            String optionlist2 = JOptionPane.showInputDialog("Insira uma opcao:\n"
                                                                    + "1 - Listar por ID \n"
                                                                    + "2 - Listar todos");
                            switch(optionlist2) {
                                case "1": // Listar por ID
                                    msg = "";
                                    id = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID de um produto: "));
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM PRODUTOS WHERE cod_produto = ?");
                                    st.setInt(1, id);

                                    rs = st.executeQuery();
                                    while (rs.next()) {
                                        id = rs.getInt("cod_produto") == 0 ? 0 : rs.getInt("cod_produto");
                                        nome = rs.getString("nome_produto") == null ? "" : rs.getString("nome_produto");
                                        marca = rs.getString("marca") == null ? "" : rs.getString("marca");
                                        telefone = rs.getString("valor_unitario") == null ? "" : rs.getString("valor_unitario");
                                        qtd = rs.getInt("quantidade") == 0 ? 0 : rs.getInt("quantidade");

                                        msg +=  "ID: " + id + "\n"
                                                + "Nome: " + nome + "\n"
                                                + "Marca: " + marca + "\n"
                                                + "Telefone: " + telefone + "\n"
                                                + "Quantidade: " + qtd + "\n\n";

                                        System.out.println(msg);

                                    }

                                    if(!"".equals(msg)) {
                                        JOptionPane.showMessageDialog(null, msg);
                                    } else { 
                                        JOptionPane.showMessageDialog(null, "CADASTRO NÃO ENCONTRADO.");
                                    }
                                    break;
                                    
                                case "2": // Listar todos
                                    msg = "";
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM PRODUTOS");

                                    rs = st.executeQuery();
                                    while (rs.next()) {
                                        id = rs.getInt("cod_produto") == 0 ? 0 : rs.getInt("cod_produto");
                                        nome = rs.getString("nome_produto") == null ? "" : rs.getString("nome_produto");
                                        marca = rs.getString("marca") == null ? "" : rs.getString("marca");
                                        telefone = rs.getString("valor_unitario") == null ? "" : rs.getString("valor_unitario");
                                        qtd = rs.getInt("quantidade") == 0 ? 0 : rs.getInt("quantidade");

                                        msg +=  "ID: " + id + "\n"
                                                + "Nome: " + nome + "\n"
                                                + "Marca: " + marca + "\n"
                                                + "Telefone: " + telefone + "\n"
                                                + "Quantidade: " + qtd + "\n\n";

                                        System.out.println(msg);

                                    }

                                    if(!"".equals(msg)) {
                                        JOptionPane.showMessageDialog(null, msg);
                                    } else { 
                                        JOptionPane.showMessageDialog(null, "CADASTROS NÃO ENCONTRADOS.");
                                    }
                                    break;
                        
                            }
                            break;
                        case "3":
//                        Listagem de Pedidos
                            String optionlist3 = JOptionPane.showInputDialog("Insira uma opcao:\n"
                                                                    + "1 - Listar por ID \n"
                                                                    + "2 - Listar todos");
                            switch(optionlist3) {
                                case "1": // Listar por ID
                                    msg = "";
                                    id = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID de um pedido: "));
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM PEDIDOS WHERE codpedido = ?");
                                    st.setInt(1, id);

                                    rs = st.executeQuery();
                                    while (rs.next()) {
                                        id = rs.getInt("codpedido") == 0 ? 0 : rs.getInt("codpedido");
                                        codigoCliente = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                                        nome = rs.getString("descricao") == null ? "" : rs.getString("descricao");
                                        valorTotal = rs.getDouble("valor_total") == 0.0 ? 0.0 : rs.getDouble("valor_total");
                                        java.sql.Date data = rs.getDate("datapedido");

                                        msg +=  "ID Pedido: " + id + "\n"
                                                + "ID Cliente: " + codigoCliente + "\n"
                                                + "Descrição: " + nome + "\n"
                                                + "Valor Total: " + valorTotal + "\n"
                                                + "Data Pedido: " + data + "\n\n";

                                        System.out.println(msg);

                                    }

                                    if(!"".equals(msg)) {
                                        JOptionPane.showMessageDialog(null, msg);
                                    } else { 
                                        JOptionPane.showMessageDialog(null, "CADASTRO NÃO ENCONTRADO.");
                                    }

                                case "2": // Listar todos
                                    msg = "";
                                    conn = DatabaseService.getConnPostgres();
                                    st = conn.prepareStatement("SELECT * FROM PEDIDOS");

                                    rs = st.executeQuery();
                                    while (rs.next()) {
                                        id = rs.getInt("codpedido") == 0 ? 0 : rs.getInt("codpedido");
                                        codigoCliente = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                                        nome = rs.getString("descricao") == null ? "" : rs.getString("descricao");
                                        valorTotal = rs.getDouble("valor_total") == 0.0 ? 0.0 : rs.getDouble("valor_total");
                                        java.sql.Date data = rs.getDate("datapedido");

                                        msg +=  "ID Pedido: " + id + "\n"
                                                + "ID Cliente: " + codigoCliente + "\n"
                                                + "Descrição: " + nome + "\n"
                                                + "Valor Total: " + valorTotal + "\n"
                                                + "Data Pedido: " + data + "\n\n";

                                        System.out.println(msg);

                                    }

                                    if(!"".equals(msg)) {
                                        JOptionPane.showMessageDialog(null, msg);
                                    } else { 
                                        JOptionPane.showMessageDialog(null, "CADASTRO NÃO ENCONTRADO.");
                                    }
                                    break;
                            }
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
