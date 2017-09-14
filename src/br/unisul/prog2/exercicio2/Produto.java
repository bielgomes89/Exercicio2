package br.unisul.prog2.exercicio2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;

public class Produto {

    static public int codigo;
    static public String nome;
    static public String marca;
    static public float preco;
    static int quantidade;

    static Connection conn = null;
    static ResultSet rs = null;
    static PreparedStatement st = null;

    public void cadastroPedido(String nome, String marca, float preco, int quantidade) {
        Produto produtoDB = new Produto();
        produtoDB.setNome(nome);
        produtoDB.setMarca(marca);
        produtoDB.setPreco(preco);
        produtoDB.setQuantidade(quantidade);

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public static void CadastroProduto() throws SQLException, ParseException {
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
                    Cadastro.Cadastros();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public static void InserirProduto() throws SQLException {
        Produto produtoDB = new Produto();

        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do produto: "));
            id = Integer.parseInt(String.format("%06d", id));
            nome = JOptionPane.showInputDialog("Informe o nome do produto: ");
            marca = JOptionPane.showInputDialog("Informe a marca do produto: ");
            float valor = Float.parseFloat(JOptionPane.showInputDialog("Informe o valor do produto: "));
            int qtd = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade do produto: "));
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
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    public static void RemoverProduto() throws SQLException {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do produto: "));
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

                JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }
    }

    public static void AlterarProduto() throws SQLException {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do produto: "));
            nome = JOptionPane.showInputDialog("Informe o nome do produto: ");
            marca = JOptionPane.showInputDialog("Informe a marca do produto: ");
            float valor = Float.parseFloat(JOptionPane.showInputDialog("Informe o valor do produto: "));
            int qtd = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade do produto: "));

            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("UPDATE PRODUTOS SET nome_produto = ?, marca = ?, valor_unitario = ?, quantidade = ? WHERE cod_produto = ?");
            st.setString(1, nome);
            st.setString(2, marca);
            st.setFloat(3, valor);
            st.setInt(4, qtd);
            st.setInt(5, id);
            rs = st.executeQuery();

        } catch (Exception e) {
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }

                JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    public static void ListarProdutoId() throws SQLException {
        try {
            String msg = "";
            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID de um produto: "));
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM PRODUTOS WHERE cod_produto = ?");
            st.setInt(1, id);

            rs = st.executeQuery();
            while (rs.next()) {
                id = rs.getInt("cod_produto") == 0 ? 0 : rs.getInt("cod_produto");
                nome = rs.getString("nome_produto") == null ? "" : rs.getString("nome_produto");
                marca = rs.getString("marca") == null ? "" : rs.getString("marca");
                String vlUnitario = rs.getString("valor_unitario") == null ? "" : rs.getString("valor_unitario");
                int qtd = rs.getInt("quantidade") == 0 ? 0 : rs.getInt("quantidade");

                msg += "ID: " + id + "\n"
                        + "Nome: " + nome + "\n"
                        + "Marca: " + marca + "\n"
                        + "Valor Un.: " + vlUnitario + "\n"
                        + "Quantidade: " + qtd + "\n\n";

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

    public static void ListarProdutoTodos() throws SQLException {
        try {
            String msg = "";
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM PRODUTOS");

            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("cod_produto") == 0 ? 0 : rs.getInt("cod_produto");
                nome = rs.getString("nome_produto") == null ? "" : rs.getString("nome_produto");
                marca = rs.getString("marca") == null ? "" : rs.getString("marca");
                String vlUnitario = rs.getString("valor_unitario") == null ? "" : rs.getString("valor_unitario");
                int qtd = rs.getInt("quantidade") == 0 ? 0 : rs.getInt("quantidade");

                msg += "ID: " + id + "\n"
                        + "Nome: " + nome + "\n"
                        + "Marca: " + marca + "\n"
                        + "Valor: " + vlUnitario + "\n"
                        + "Quantidade: " + qtd + "\n\n";

            }

            if (!"".equals(msg)) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "CADASTROS NÃO ENCONTRADOS.");
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

    //--
}
