package br.unisul.prog2.exercicio2;

public class Cliente {
    
    public int codigo;
    public String nome;
    public String endereco;
    public String telefone;
    public String cpf;
    public String email;
    public String estadoCivil;
    public String dataNascimento;

    public void cadastroCliente(String nome, String endereco, String telefone, String cpf, String email, String estadoCivil, String dataNascimento ) {
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
}
