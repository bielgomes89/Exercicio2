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

public class Cliente {

    public int codigo;
    public String nome;
    public String endereco;
    public String telefone;
    public String cpf;
    public String email;
    public String estadoCivil;
    public String dataNascimento;

    static DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    static Connection conn = null;
    static ResultSet rs = null;
    static PreparedStatement st = null;

    public void cadastroCliente(String nome, String endereco, String telefone, String cpf, String email, String estadoCivil, String dataNascimento) {
        Cliente clienteDB = new Cliente();
        clienteDB.setNome(nome);
        clienteDB.setCpf(cpf);
        clienteDB.setEndereco(endereco);
        clienteDB.setTelefone(telefone);
        clienteDB.setDataNascimento(dataNascimento);
        clienteDB.setEmail(email);
        clienteDB.setEstadoCivil(estadoCivil);
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public static void CadastroCliente() throws ParseException, SQLException {
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
                    Cadastro.Cadastros();
                    break;
            }
        } catch (Exception e) {
        }

    }

    public static void InserirCliente() throws ParseException, SQLException {
        Cliente clienteDB = new Cliente();

        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Informe o código do Cliente"));
            String nome = JOptionPane.showInputDialog("Informe o nome do cliente: ");
            String cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
            String endereco = JOptionPane.showInputDialog("Informe o endereço do cliente: ");
            String telefone = JOptionPane.showInputDialog("Informe o telefone do cliente: ");
            String dataNascimento = JOptionPane.showInputDialog("Informe a data de nascimento do cliente: ");
            String email = JOptionPane.showInputDialog("Informe o email do cliente: ");
            String estadoCivil = JOptionPane.showInputDialog("Informe o estado civil: ");
            
            clienteDB.cadastroCliente(nome, endereco, telefone, cpf, email, estadoCivil, dataNascimento);

            java.util.Date dtnasc = formatter.parse(dataNascimento);
            java.sql.Date sqlDate = new java.sql.Date(dtnasc.getTime());

            java.sql.Date data = new java.sql.Date(formatter.parse(dataNascimento).getTime());

            conn = DatabaseService.getConnPostgres();
            PreparedStatement st = conn.prepareStatement("INSERT INTO CLIENTES (codcli, nome, endereco, telefone, cpf, dtnascimento, email, estadocivil) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ");

            st.setInt(1, codigo);
            st.setString(2, nome);
            st.setString(3, endereco);
            st.setString(4, telefone);
            st.setString(5, cpf);
            st.setDate(6, data);
            st.setString(7, email);
            st.setString(8, estadoCivil);

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

                JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    public static void RemoverCliente() throws SQLException {
        try {
            String cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
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

                JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    public static void AlterarCliente() throws ParseException, SQLException {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do Cliente:"));
            String nome = JOptionPane.showInputDialog("Informe o nome do cliente: ");
            String endereco = JOptionPane.showInputDialog("Informe o endereço do cliente: ");
            String telefone = JOptionPane.showInputDialog("Informe o telefone do cliente: ");
            String cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
            String email = JOptionPane.showInputDialog("Informe o email do cliente: ");
            String estadoCivil = JOptionPane.showInputDialog("Informe o Estado Civil do cliente: ");
            String dataNascimento = JOptionPane.showInputDialog("Informe a data de nascimento do cliente: ");
            java.util.Date dtnasc = formatter.parse(dataNascimento);
            Date sqlDate = new java.sql.Date(dtnasc.getTime());
            
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("UPDATE CLIENTES SET nome = ?, endereco = ?, telefone = ?, cpf = ?, dtnascimento = ?, email = ?, estadocivil = ? WHERE codcli = ?");
            st.setString(1, nome);
            st.setString(2, endereco);
            st.setString(3, telefone);
            st.setString(4, cpf);
            st.setDate(5, sqlDate);
            st.setString(6, email);
            st.setString(7, estadoCivil);

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

                JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    public static void ListarClienteId() throws SQLException {
        try {
            //Listagem de Clientes por ID
            String msg = "";
            int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID de um cliente: "));
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM CLIENTES WHERE codcli = ?");
            st.setInt(1, codigo);

            rs = st.executeQuery();
            while (rs.next()) {
                codigo = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                String nome = rs.getString("nome") == null ? "" : rs.getString("nome");
                String endereco = rs.getString("endereco") == null ? "" : rs.getString("endereco");
                String telefone = rs.getString("telefone") == null ? "" : rs.getString("telefone");
                String cpf = rs.getString("cpf") == null ? "" : rs.getString("cpf");
                java.sql.Date data = rs.getDate("dtnascimento");
                String email = rs.getString("email") == null ? "" : rs.getString("email");
                String estadoCivil = rs.getString("estadocivil") == null ? "" : rs.getString("estadocivil");

                msg += "ID: " + codigo + "\n"
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

                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }
    }

    public static void ListarClienteTodos() throws SQLException {
        try {
            String msg = "";
            conn = DatabaseService.getConnPostgres();
            st = conn.prepareStatement("SELECT * FROM CLIENTES");
            rs = st.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("codcli") == 0 ? 0 : rs.getInt("codcli");
                String nome = rs.getString("nome") == null ? "" : rs.getString("nome");
                String endereco = rs.getString("endereco") == null ? "" : rs.getString("endereco");
                String telefone = rs.getString("telefone") == null ? "" : rs.getString("telefone");
                String cpf = rs.getString("cpf") == null ? "" : rs.getString("cpf");
                java.sql.Date data = rs.getDate("dtnascimento");
                String email = rs.getString("email") == null ? "" : rs.getString("email");
                String estadoCivil = rs.getString("estadocivil") == null ? "" : rs.getString("estadocivil");

                msg += "ID: " + codigo + "\n"
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

                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

}
