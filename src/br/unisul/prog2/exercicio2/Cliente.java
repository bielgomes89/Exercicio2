package br.unisul.prog2.exercicio2;

import static br.unisul.prog2.exercicio2.Exercicio2.formatter;
import static br.unisul.prog2.exercicio2.Exercicio2.nome;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;

public class Cliente {

    static public int codigo;
    static public String nome;
    static public String endereco;
    static public String telefone;
    static public String cpf;
    static public String email;
    static public String estadoCivil;
    static public String dataNascimento;
    
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

    public static void InserirCliente() throws ParseException, SQLException {
        Cliente clienteDB = new Cliente();
        
        try {
            String nome = JOptionPane.showInputDialog("Informe o nome do cliente: ");
            String endereco = JOptionPane.showInputDialog("Informe o endereço do cliente: ");
            String telefone = JOptionPane.showInputDialog("Informe o telefone do cliente: ");
            String cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
            String email = JOptionPane.showInputDialog("Informe o email do cliente: ");
            String estadoCivil = JOptionPane.showInputDialog("Informe o Estado Civil do cliente: ");
            String dataNascimento = JOptionPane.showInputDialog("Informe a data de nascimento do cliente: ");

            clienteDB.cadastroCliente(nome, endereco, telefone, cpf, email, estadoCivil, dataNascimento);

            java.util.Date dtnasc = formatter.parse(dataNascimento);
            java.sql.Date sqlDate = new java.sql.Date(dtnasc.getTime());

            java.sql.Date data = new java.sql.Date(formatter.parse(dataNascimento).getTime());

            conn = DatabaseService.getConnPostgres();
            PreparedStatement st = conn.prepareStatement("INSERT INTO CLIENTES (nome, cpf, endereco, telefone, dtnascimento, email, senha) VALUES( ?, ?, ?, ?, ?, ?, ?)");

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

                JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    public static void RemoverCliente() throws SQLException {
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

                JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                Exercicio2.Exercicio2();

            } catch (Exception e) {

            }

        }

    }

    public static void AlterarCliente() throws ParseException, SQLException {
        try {
            codigo = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do Cliente:"));
            nome = JOptionPane.showInputDialog("Informe o nome do cliente: ");
            endereco = JOptionPane.showInputDialog("Informe o endereço do cliente: ");
            telefone = JOptionPane.showInputDialog("Informe o telefone do cliente: ");
            cpf = JOptionPane.showInputDialog("Informe o CPF do cliente: ");
            email = JOptionPane.showInputDialog("Informe o email do cliente: ");
            estadoCivil = JOptionPane.showInputDialog("Informe o Estado Civil do cliente: ");
            dataNascimento = JOptionPane.showInputDialog("Informe a data de nascimento do cliente: ");
            java.util.Date dtnasc = formatter.parse(dataNascimento);
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
            st.setInt(8, codigo);

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
}
