package dao;

import model.Pedido;
import controller.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ItemPedido;

public class PedidoDAO {
    private ItemPedidoDAO itemDao = new ItemPedidoDAO();

    public Pedido inserir(Pedido p) throws SQLException {
        String sql = "INSERT INTO pedidos (usuario_id, data_pedido, valor_total) VALUES (?, ?, ?)";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getUsuario().getId());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setBigDecimal(3, p.getTotal());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
            return p;
        }
    }

    public void atualizar(Pedido p) throws SQLException {
        String sql = "UPDATE pedidos SET usuario_id = ?, data_pedido = ?, total = ? WHERE id = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, p.getUsuario().getId());
            ps.setTimestamp(2, Timestamp.valueOf(p.getDataPedido()));
            ps.setBigDecimal(3, p.getTotal()); ps.setInt(4, p.getId()); ps.executeUpdate();
        }
        // atualizar itens: remover antigos e inserir novos
        List<ItemPedido> antigos = itemDao.buscarPorPedidoId(p.getId());
        for (ItemPedido ip : antigos) itemDao.deletar(ip.getId());
        for (ItemPedido ip : p.getItens()) { ip.setPedido(p); itemDao.inserir(ip); }
    }

    public Pedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setUsuario(new UsuarioDAO().buscarPorId(rs.getInt("usuario_id")));
                p.setDataPedido(rs.getTimestamp("data_pedido").toLocalDateTime());
                p.setTotal(rs.getBigDecimal("total"));
                p.setItens(itemDao.buscarPorPedidoId(id));
                return p;
            }
        }
    }

    public List<Pedido> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM pedidos";
        List<Pedido> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Pedido p = new Pedido();
                int id = rs.getInt("id");
                p.setId(id);
                p.setUsuario(new UsuarioDAO().buscarPorId(rs.getInt("usuario_id")));
                p.setDataPedido(rs.getTimestamp("data_pedido").toLocalDateTime());
                p.setTotal(rs.getBigDecimal("total"));
                p.setItens(itemDao.buscarPorPedidoId(id));
                lista.add(p);
            }
        }
        return lista;
    }

    public void deletar(int id) throws SQLException {
        for (ItemPedido ip : itemDao.buscarPorPedidoId(id)) itemDao.deletar(ip.getId());
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); ps.executeUpdate();
        }
    }
}