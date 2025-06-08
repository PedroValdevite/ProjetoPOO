package dao;

import model.Prato;
import controller.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PratoDAO {
    public void inserir(Prato p) throws SQLException {
        String sql = "INSERT INTO pratos (nome, descricao, preco) VALUES (?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setBigDecimal(3, p.getPreco());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Prato p) throws SQLException {
        String sql = "UPDATE pratos SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setBigDecimal(3, p.getPreco());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        }
    }

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