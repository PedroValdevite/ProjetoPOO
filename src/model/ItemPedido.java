package model;

import java.math.BigDecimal;

public class ItemPedido {
    private int id;
    private Pedido pedido;
    private Prato prato;
    private int quantidade;
    private BigDecimal subtotal;

    public ItemPedido(int id, Pedido pedido, Prato prato, int quantidade, BigDecimal subtotal) {
        this.id = id;
        this.pedido = pedido;
        this.prato = prato;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }

    public ItemPedido() {
    }

    public ItemPedido(Prato prato, int quantidade) {
        this.prato = prato;
        this.quantidade = quantidade;
        
        if (prato != null && prato.getPreco() != null) {
            // Multiplica o preço do prato (BigDecimal) pela quantidade (convertida para BigDecimal)
            this.subtotal = prato.getPreco().multiply(new BigDecimal(quantidade));
        } else {
            // Garante que o subtotal seja zero se não houver preço, evitando null.
            this.subtotal = BigDecimal.ZERO;
        }
    }
    
    

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Prato getPrato() { return prato; }
    public void setPrato(Prato prato) { this.prato = prato; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
