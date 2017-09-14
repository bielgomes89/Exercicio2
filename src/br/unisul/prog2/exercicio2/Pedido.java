package br.unisul.prog2.exercicio2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class Pedido {

    public int codigo;
    public int codCliente;
    public String descricao;
    public double valorTotal;
    public String dataPedido;
    static DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    static Connection conn = null;
    static PreparedStatement st = null;
    static ResultSet rs = null;

    public void cadastroPedido(int codCliente, String descricao, double valorTotal, String dataPedido) {
        Pedido pedidoDBPedido = new Pedido();
        pedidoDBPedido.setCodCliente(codCliente);
        pedidoDBPedido.setDescricao(descricao);
        pedidoDBPedido.setValorTotal(valorTotal);
        pedidoDBPedido.setDataPedido(dataPedido);
    }

    public void cadastroPedido() {
        Pedido pedidoDB = new Pedido();

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public static void Pedidos() throws SQLException, ParseException {
        try {
            String pedido = JOptionPane.showInputDialog("Insira a opção desejada: \n" + "1 - Efetuar Pedido" + "\n"
                    + "2 - Consultar Cliente CPF " + "\n"
                    + "3 - Consultar Produto " + "\n"
                    + "4 - Voltar");

            switch (pedido) {
                case "1":
                    EfetuarPedido();
                    break;
                case "2":
                    ConsultarClienteCpf();
                    break;
                case "3":
                    ConsultarProdutoNome();
                    break;
                case "4":
                    Exercicio2.Exercicio2();
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
        Pedido pedidoDB = new Pedido();

        try {
            String msg = "";
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM CLIENTES");
            rs = st.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                String nome = rs.getString("nome") == null ? "" : rs.getString("nome");

                msg += "ID: " + codigo + "  |  "
                        + "Nome: " + nome + "\n";

            }

            int codcli = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do cliente:" + "\n" + msg));
            
            
            msg = "";
            double valorUnitario = 0;
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM PRODUTOS");
            rs = st.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("cod_produto") == 0 ? 0 : rs.getInt("cod_produto");
                String nome = rs.getString("nome_produto") == null ? "" : rs.getString("nome_produto");
                valorUnitario = rs.getDouble("valor_unitario") == 0 ? 0 : rs.getDouble("valor_unitario");
                
                msg += "ID: " + codigo + "\n"
                        + "Nome Produto: " + nome + "\n"
                        + "Valor Uni: " + valorUnitario + "\n" + "\n";
            }
            
            int codProduto = Integer.parseInt(JOptionPane.showInputDialog("Informe o codigo do produto:" + "\n" + msg));
            
            st = conn.prepareStatement("SELECT * FROM PRODUTOS WHERE cod_produto = ?");
            st.setInt(1, codProduto);
            rs = st.executeQuery();
            
            int quantidadeProduto = 0;
            double valorUnitarioProduto = 0;
            String nomeProduto = "";
                    
            while (rs.next()) {
                nomeProduto = rs.getString("nome_produto") == null ? "" : rs.getString("nome_produto");
                valorUnitarioProduto = rs.getDouble("valor_unitario") == 0 ? 0 : rs.getDouble("valor_unitario");
                quantidadeProduto = rs.getInt("quantidade") == 0 ? 0 : rs.getInt("quantidade");              
            }
            
            String dataPedido = JOptionPane.showInputDialog("Informe a data do pedido: ");
            java.util.Date dtPedido = formatter.parse(dataPedido);
            Date sqlDate = new java.sql.Date(dtPedido.getTime());
            
            int quantidadePedido = 0;
            
            quantidadePedido = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de produto deste pedido: "));
            
            double valorTotalPedido = 0;
            
            if(quantidadePedido > quantidadeProduto)
            {
                JOptionPane.showMessageDialog(null, "O estoque do produto não possui" + quantidadePedido + "itens.");
                Pedidos();
            }else{
                valorTotalPedido = quantidadePedido * valorUnitario;
            }      
            
            int dialogButton = JOptionPane.YES_NO_OPTION;
            
            JOptionPane.showConfirmDialog(null, "Você está adquirindo: " + "\n"
                                              + "Produto Código: " + codProduto + "\n"
                                              + "Nome do Produto: " + nomeProduto + "\n"
                                              + "Preço Unitário: " + valorUnitario + "\n"
                                              + "Quantidade Comprada" + quantidadePedido + "\n"
                                              + "Valor Total:" + valorTotalPedido + "\n"
                                              + "Deseja fechar a compra?", "Atenção", dialogButton);
            
            if(dialogButton == JOptionPane.YES_OPTION)
            {
                
            
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("INSERT INTO PEDIDOS (codcli, descricao, valor_total, datapedido) VALUES( ?, ?, ?, ?)");
            st.setInt(1, codcli);
            st.setString(2, nomeProduto);
            st.setDouble(3, valorTotalPedido);
            st.setDate(4, sqlDate);
            rs = st.executeQuery();
            rs = st.getGeneratedKeys();
            
            int codPedido = rs.getInt(1);
            
            st = conn.prepareStatement("INSERT INTO ITEM_PEDIDO (cod_pedido, cod_produto, valor_item, quantidade_itens) VALUES( ?, ?, ?, ?)");
            st.setInt(1, codPedido);
            st.setInt(2, codProduto);
            st.setDouble(3, valorUnitario);
            st.setInt(4, quantidadePedido);
            rs = st.executeQuery();
            
            }else{
                Pedidos();
            }

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
                JOptionPane.showMessageDialog(null, "Pedido cadastrado com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    private static void ConsultarClienteCpf() {
        try {
            String msg = "";
            String cpf = JOptionPane.showInputDialog(null, "Digite o cpf do cliente: ");
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM CLIENTES WHERE cpf = ?");
            st.setString(1, cpf);

            rs = st.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                String nome = rs.getString("nome") == null ? "" : rs.getString("nome");
                String telefone = rs.getString("telefone") == null ? "" : rs.getString("telefone");

                msg += "ID Cliente: " + codigo + "\n"
                        + "Nome: " + nome + "\n"
                        + "Telefone: " + telefone + "\n";
            }

            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
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
                Pedidos();
            } catch (Exception e) {

            }

        }
    }

    public static void ListarPedidoId() throws SQLException {
        try {
            String msg = "";
            int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID de um pedido: "));
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM PEDIDOS WHERE codpedido = ?");
            st.setInt(1, codigo);

            rs = st.executeQuery();
            while (rs.next()) {
                codigo = rs.getInt("codpedido") == 0 ? 0 : rs.getInt("codpedido");
                int codigoCliente = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                String nome = rs.getString("descricao") == null ? "" : rs.getString("descricao");
                double valorTotal = rs.getDouble("valor_total") == 0.0 ? 0.0 : rs.getDouble("valor_total");
                java.sql.Date data = rs.getDate("datapedido");

                msg += "ID Pedido: " + codigo + "\n"
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
                Exercicio2.Exercicio2();
            } catch (Exception e) {

            }

        }

    }

    public static void ListarPedidoTodos() throws SQLException {
        try {
            String msg = "";
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM PEDIDOS");

            rs = st.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("codpedido") == 0 ? 0 : rs.getInt("codpedido");
                int codigoCliente = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                String nome = rs.getString("descricao") == null ? "" : rs.getString("descricao");
                double valorTotal = rs.getDouble("valor_total") == 0.0 ? 0.0 : rs.getDouble("valor_total");
                java.sql.Date data = rs.getDate("datapedido");

                msg += "ID Pedido: " + codigo + "\n"
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
                Exercicio2.Exercicio2();
            } catch (Exception e) {

            }

        }

    }

    private static void ConsultarProdutoNome() {
        try {
            String msg = "";
            String nome = JOptionPane.showInputDialog(null, "Digite o nome do produto: ");
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM PRODUTOS WHERE nome_produto = ?");
            st.setString(1, nome);

            rs = st.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("cod_produto") == 0 ? 0 : rs.getInt("cod_produto");
                String valorUnitario = rs.getString("valor_unitario") == null ? "" : rs.getString("valor_unitario");
                String marca = rs.getString("marca") == null ? "" : rs.getString("marca");
                int quantidade = rs.getInt("quantidade") == 0 ? 0 : rs.getInt("quantidade");

                msg += "Nome Produto: " + nome + "\n"
                        + "Valor: " + valorUnitario + "\n"
                        + "Marca: " + marca + "\n"
                        + "Quantidade: " + quantidade;
            }

            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "PRODUTO NÃO ENCONTRADO.");
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
                Pedidos();
            } catch (Exception e) {

            }

        }
    }

}
