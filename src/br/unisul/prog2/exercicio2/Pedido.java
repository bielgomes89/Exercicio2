package br.unisul.prog2.exercicio2;

public class Pedido {
    public int codigo;
    public int codCliente;
    public String descricao;
    public double valorTotal; 
    public String dataPedido;
    
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
    
    
}
