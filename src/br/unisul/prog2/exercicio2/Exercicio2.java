package br.unisul.prog2.exercicio2;

import static br.unisul.prog2.exercicio2.DatabaseService.getConnPostgres;
import javax.swing.JOptionPane;
import static br.unisul.prog2.exercicio2.EmpresaTxtReader.importar;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Exercicio2 {

    static Connection conn;
    static String msg = "";
    static int id = 0;
    static String nome = "";
    static String marca = "";
    static float valor = 0;
    static double valorTotal = 0.0;
    static int qtd = 0;
    static String endereco = "";
    static String telefone = "";
    static String cpf = "";
    static String email = "";
    static String estadoCivil = "";
    static String dataNascimento = "";
    static DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    static PreparedStatement st = null;
    static int codigoCliente = 0;
    static ResultSet rs = null;
    static Cliente clienteDB = new Cliente();
    static Pedido pedidoDB = new Pedido();
    static Produto produtoDB = new Produto();
    static String cadastro = "";
    static String pedido = "";
    static String relatorio = "";
    private static java.util.Date dtnasc;

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
                    Cadastros();
                    break;
                case "2": // Pedidos
                    Pedidos();
                    break;
                case "3": // Relatórios
                    Relatorios();
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

    //menu opção 1
    private static void Cadastros() throws ParseException, SQLException {
        try {
            cadastro = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Clientes" + "\n"
                    + "2 - Produtos" + "\n"
                    + "3 - Voltar");

            switch (cadastro) {
                case "1": // Cadastro de clientes
                    CadastroCliente();
                case "2": // Cadastro de produtos
                    CadastroProduto();
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

    // Cadastro Cliente
    private static void CadastroCliente() throws ParseException, SQLException {
        try {
            String option1 = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Inserir cliente" + "\n"
                    + "2 - Remover cliente" + "\n"
                    + "3 - Alterar cliente" + "\n"
                    + "4 - Voltar");

            switch (option1) {
                case "1": //Inserir Cliente
                    InserirCliente();
                    break;
                case "2": // Remover Cliente
                    RemoverCliente();
                    break;
                case "3": // Alterar Cliente
                    AlterarCliente();
                    break;
                case "4":
                    Cadastros();
                    break;
            }
        } catch (Exception e) {
        }

    }

    private static void InserirCliente() throws ParseException, SQLException {
        try {
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
        } catch (Exception e) {
            System.out.println(e);
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

    private static void RemoverCliente() throws SQLException {
        try {
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

    private static void AlterarCliente() throws ParseException, SQLException {
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do Cliente:"));
            nome = JOptionPane.showInputDialog("Informe o nome do cliente: ");
            endereco = JOptionPane.showInputDialog("Informe o endereço do cliente: ");
            telefone = JOptionPane.showInputDialog("Informe o telefone do cliente: ");
            cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
            email = JOptionPane.showInputDialog("Informe o email do cliente: ");
            estadoCivil = JOptionPane.showInputDialog("Informe o Estado Civil do cliente: ");
            dataNascimento = JOptionPane.showInputDialog("Informe a data de nascimento do cliente: ");
            dtnasc = formatter.parse(dataNascimento);
            Date sqlDate = new java.sql.Date(dtnasc.getTime());

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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

    // Cadastro Produto
    private static void CadastroProduto() throws SQLException, ParseException {
        try {
            String option2 = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Inserir Produto" + "\n"
                    + "2 - Remover produto" + "\n"
                    + "3 - Alterar produto" + "\n"
                    + "4 - Voltar");

            switch (option2) {
                case "1": // Inserir Produto
                    InserirProduto();
                    break;
                case "2":
                    RemoverProduto();
                    break;
                case "3":
                    AlterarProduto();
                    break;
                case "4":
                    Cadastros();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private static void InserirProduto() throws SQLException {
        try {
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

    private static void RemoverProduto() throws SQLException {
        try {
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

    private static void AlterarProduto() throws SQLException {
        try {
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
    //--

    // Pedidos
    private static void Pedidos() throws SQLException, ParseException {
        try {
            pedido = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Efetuar Pedido" + "\n"
                    + "2 - Consultar Pedido" + "\n"
                    + "3 - Voltar");

            switch (pedido) {
                case "1":
                    EfetuarPedido();
                    break;
                case "2":
                    ConsultarPedido();
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

    private static void EfetuarPedido() throws SQLException, ParseException {
        try {
            msg = "";
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM CLIENTES");
            rs = st.executeQuery();
            while (rs.next()) {
                id = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                nome = rs.getString("nome") == null ? "" : rs.getString("nome");

                msg += "ID: " + id + "  |  "
                        + "Nome: " + nome + "\n";

            }

            int codcli = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do cliente:" + "\n" + msg));
            String descricao = JOptionPane.showInputDialog("Informe a descrição do produto: ");
            double vlTotal = Double.parseDouble(JOptionPane.showInputDialog("Informe o Valor Total do Produto: "));
            String dataPedido = JOptionPane.showInputDialog("Informe a data do pedido: ");
            java.util.Date dtPedido = formatter.parse(dataPedido);
            java.sql.Date sqlDate = new java.sql.Date(dtPedido.getTime());
            java.sql.Date data = new java.sql.Date(formatter.parse(dataPedido).getTime());

            pedidoDB.cadastroPedido(codcli, descricao, vlTotal, dataPedido);

            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("INSERT INTO PEDIDOS (codcli, descricao, valor_total, datapedido) VALUES( ?, ?, ?, ?)");
            st.setInt(1, codcli);
            st.setString(2, descricao);
            st.setDouble(3, vlTotal);
            st.setDate(4, data);
            rs = st.executeQuery();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
                Pedidos();

            } catch (Exception e) {

            }

        }

    }

    private static void ConsultarPedido() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //--

    //Relatorios
    //menu opção 3
    private static void Relatorios() throws SQLException {
        try {
            relatorio = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Listagem de Clientes" + "\n"
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
                    Exercicio2();
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
                    ListarPedidoId();
                case "2": // Listar todos
                    ListarPedidoTodos();
                    break;
                case "3": // Voltar
                    Pedidos();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private static void ListarPedidoId() throws SQLException {
        try {
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

                msg += "ID Pedido: " + id + "\n"
                        + "ID Cliente: " + codigoCliente + "\n"
                        + "Descrição: " + nome + "\n"
                        + "Valor Total: " + valorTotal + "\n"
                        + "Data Pedido: " + data + "\n\n";

                System.out.println(msg);

            }

            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "CADASTRO NÃO ENCONTRADO.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
                ListagemPedido();
            } catch (Exception e) {

            }

        }

    }

    private static void ListarPedidoTodos() throws SQLException {
        try {
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

                msg += "ID Pedido: " + id + "\n"
                        + "ID Cliente: " + codigoCliente + "\n"
                        + "Descrição: " + nome + "\n"
                        + "Valor Total: " + valorTotal + "\n"
                        + "Data Pedido: " + data + "\n\n";

                System.out.println(msg);

            }
            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "CADASTRO NÃO ENCONTRADO.");
            }
        } catch (Exception e) {
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
                ListagemPedido();
            } catch (Exception e) {

            }

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
                    ListarClienteId();
                    break;
                case "2":
                    ListarClienteTodos();
                    break;
                case "3":
                    Relatorios();
                    break;

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private static void ListarClienteId() throws SQLException {
        try {
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

                msg += "ID: " + id + "\n"
                        + "Nome: " + nome + "\n"
                        + "CPF: " + cpf + "\n"
                        + "Endereco: " + endereco + "\n"
                        + "Telefone: " + telefone + "\n"
                        + "Dt. Nasc: " + data + "\n"
                        + "Email: " + email + "\n"
                        + "Estado Civil: " + estadoCivil + "\n\n";
                System.out.println(msg);

            }

            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "CADASTRO NÃO ENCONTRADO.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

    private static void ListarClienteTodos() {
        try {
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

                msg += "ID: " + id + "\n"
                        + "Nome: " + nome + "\n"
                        + "CPF: " + cpf + "\n"
                        + "Endereco: " + endereco + "\n"
                        + "Telefone: " + telefone + "\n"
                        + "Dt. Nasc: " + data + "\n"
                        + "Email: " + email + "\n"
                        + "Estado Civil: " + estadoCivil + "\n\n";
                System.out.println(msg);

            }

            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "CADASTROS NÃO ENCONTRADOS.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

    //Listar Produto
    private static void ListagemProduto() throws SQLException {
        try {
            String optionlist2 = JOptionPane.showInputDialog("Insira uma opcao:\n"
                    + "1 - Listar por ID \n"
                    + "2 - Listar todos \n"
                    + "3 - Voltar");
            switch (optionlist2) {
                case "1": // Listar por ID
                    ListarProdutoId();
                    break;
                case "2": // Listar todos
                    ListarProdutoTodos();
                    break;
                case "3":
                    Relatorios();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } 

    }

    private static void ListarProdutoId() throws SQLException {
        try {
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

                msg += "ID: " + id + "\n"
                        + "Nome: " + nome + "\n"
                        + "Marca: " + marca + "\n"
                        + "Telefone: " + telefone + "\n"
                        + "Quantidade: " + qtd + "\n\n";

                System.out.println(msg);

            }

            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "CADASTRO NÃO ENCONTRADO.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }finally {
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

    private static void ListarProdutoTodos() throws SQLException {
        try {
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

                msg += "ID: " + id + "\n"
                        + "Nome: " + nome + "\n"
                        + "Marca: " + marca + "\n"
                        + "Telefone: " + telefone + "\n"
                        + "Quantidade: " + qtd + "\n\n";

                System.out.println(msg);

            }

            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "CADASTROS NÃO ENCONTRADOS.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

    //--
}
