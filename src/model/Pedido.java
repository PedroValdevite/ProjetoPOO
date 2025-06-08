package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private int id;
    private Usuario usuario;
    private LocalDateTime dataPedido;
    private BigDecimal total;
    private List<ItemPedido> itens;

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
}