package dao;

import model.Usuario;
import controller.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public void inserir(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getSenha());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) u.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET login = ?, senha = ? WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getSenha());
            ps.setInt(3, u.getId());
            ps.executeUpdate();
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setLogin(rs.getString("login"));
                    u.setSenha(rs.getString("senha"));
                    return u;
                }
                return null;
            }
        }
    }

    public List<Usuario> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                lista.add(u);
            }
        }
        return lista;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}