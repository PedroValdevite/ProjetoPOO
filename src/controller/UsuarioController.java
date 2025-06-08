package controller;

import dao.UsuarioDAO;
import java.sql.SQLException;
import model.Usuario;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public String salvarUsuario(Usuario novoUsuario) throws SQLException {
    if (novoUsuario == null || novoUsuario.getNome() == null || novoUsuario.getSenha() == null ||
        novoUsuario.getNome().trim().isEmpty() || novoUsuario.getSenha().trim().isEmpty()) {
        
        return "Erro: Nome e senha são obrigatórios.";
    }

    // 2. NOVA VALIDAÇÃO: Verificar se o usuário já existe ANTES de tentar inserir.
    try {
        // Chama o novo método do DAO que a Pessoa 1 precisa criar
        boolean jaExiste = usuarioDAO.existePorNome(novoUsuario.getNome());
        
        if (jaExiste) {
            return "Erro: Este nome de usuário já está em uso.";
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Imprime o erro técnico para o desenvolvedor
        return "Erro ao verificar a disponibilidade do nome de usuário.";
    }

    // 3. TENTATIVA DE INSERÇÃO: Apenas se passou por todas as validações.
    try {
        usuarioDAO.inserir(novoUsuario);
        return "Usuário '" + novoUsuario.getNome() + "' cadastrado com sucesso!";
    } catch (Exception e) {
        // Este erro acontece se algo der errado durante a inserção final.
        e.printStackTrace(); // Imprime o erro técnico para o desenvolvedor
        return "Erro inesperado ao cadastrar o usuário.";
    }
}

    
}