package model;

import java.math.BigDecimal;

public class ItemPedido {
    private int id;
    private Pedido pedido;
    private Prato prato;
    private int quantidade;
    private BigDecimal subtotal;

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
