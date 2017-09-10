package br.unisul.prog2.exercicio2;

public class Produto {
    public int codigo;
    public String nome;
    public String marca;
    public float preco;
    int quantidade;

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

    
    
    
}
