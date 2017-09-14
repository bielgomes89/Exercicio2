package br.unisul.prog2.exercicio2;

import java.sql.Connection;
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
                JOptionPane.showMessageDialog(null, "Pedido cadastrado com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    private static void ConsultarPedido() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
