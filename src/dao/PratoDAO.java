package dao;

import model.Prato;
import controller.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PratoDAO {

    public Prato buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pratos WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prato p = new Prato();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setPreco(rs.getBigDecimal("preco"));
                    return p;
                }
                return null;
            }
        }
    }

    public List<Prato> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM pratos";
        List<Prato> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Prato p = new Prato();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getBigDecimal("preco"));
                lista.add(p);
            }
        }
        return lista;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM pratos WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}