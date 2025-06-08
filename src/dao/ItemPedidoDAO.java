package dao;

import model.ItemPedido;
import model.Prato;
import model.Pedido;
import controller.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {
    public void inserir(ItemPedido ip) throws SQLException {
        String sql = "INSERT INTO itens_pedido (pedido_id, prato_id, quantidade, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ip.getPedido().getId());
            ps.setInt(2, ip.getPrato().getId());
            ps.setInt(3, ip.getQuantidade());
            ps.setBigDecimal(4, ip.getSubtotal());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) ip.setId(rs.getInt(1));
            }
        }
    }
    public void atualizar(ItemPedido ip) throws SQLException {
        String sql = "UPDATE itens_pedido SET pedido_id = ?, prato_id = ?, quantidade = ?, subtotal = ? WHERE id = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ip.getPedido().getId());
            ps.setInt(2, ip.getPrato().getId());
            ps.setInt(3, ip.getQuantidade());
            ps.setBigDecimal(4, ip.getSubtotal());
            ps.setInt(5, ip.getId());
            ps.executeUpdate();
        }
    }
    public ItemPedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM itens_pedido WHERE id = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ItemPedido ip = new ItemPedido();
                    ip.setId(rs.getInt("id"));
                    Pedido p = new Pedido(); p.setId(rs.getInt("pedido_id")); ip.setPedido(p);
                    Prato pr = new Prato(); pr.setId(rs.getInt("prato_id")); ip.setPrato(pr);
                    ip.setQuantidade(rs.getInt("quantidade"));
                    ip.setSubtotal(rs.getBigDecimal("subtotal"));
                    return ip;
                }
                return null;
            }
        }
    }
    public List<ItemPedido> buscarPorPedidoId(int pedidoId) throws SQLException {
        String sql = "SELECT * FROM itens_pedido WHERE pedido_id = ?";
        List<ItemPedido> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pedidoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemPedido ip = new ItemPedido();
                    ip.setId(rs.getInt("id"));
                    Pedido p = new Pedido(); p.setId(pedidoId); ip.setPedido(p);
                    Prato pr = new Prato(); pr.setId(rs.getInt("prato_id")); ip.setPrato(pr);
                    ip.setQuantidade(rs.getInt("quantidade")); ip.setSubtotal(rs.getBigDecimal("subtotal"));
                    lista.add(ip);
                }
            }
        }
        return lista;
    }
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM itens_pedido WHERE id = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); ps.executeUpdate();
        }
    }
}