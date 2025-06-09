package controller;

import dao.UsuarioDAO;
import java.sql.SQLException;
import model.Usuario;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

public boolean salvarUsuario(Usuario novoUsuario) throws SQLException {

        // --- 1. VALIDAÇÃO DE ENTRADA ---
        // Verifica se os dados essenciais não são nulos ou vazios.
        if (novoUsuario == null || novoUsuario.getNome() == null || novoUsuario.getSenha() == null ||
            novoUsuario.getNome().trim().isEmpty() || novoUsuario.getSenha().trim().isEmpty()) {
            
            // Mensagem de erro para o usuário.
            System.err.println("Erro de validação: Nome e senha são obrigatórios.");
            
            // Retorna 'false' para indicar que a operação falhou.
            return false;
        }

        // --- 2. VALIDAÇÃO DE REGRA DE NEGÓCIO ---
        // Verifica se o nome de usuário já está em uso antes de tentar a inserção.
        // A lógica de acesso ao banco foi movida para um bloco try-catch separado para isolar o erro.
        try {
            if (usuarioDAO.existePorNome(novoUsuario.getNome())) {
                // Mensagem de erro para o usuário.
                System.err.println("Erro: O nome de usuário '" + novoUsuario.getNome() + "' já está em uso.");
                // Retorna 'false' para indicar que a operação falhou.
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro: Falha ao verificar a disponibilidade do nome de usuário no banco de dados.");
            // Imprime o stack trace para depuração (ajuda o desenvolvedor a encontrar o problema técnico).
            e.printStackTrace();
            // Retorna 'false' para indicar que a operação falhou.
            return false;
        }

        // --- 3. OPERAÇÃO DE INSERÇÃO ---
        // Se todas as validações passaram, tenta inserir o usuário no banco de dados.
        try {
            usuarioDAO.inserir(novoUsuario);

            // Mensagem de sucesso para o usuário.
            System.out.println("Usuário '" + novoUsuario.getNome() + "' cadastrado com sucesso!");
            
            // Retorna 'true' para indicar que a operação foi bem-sucedida.
            return true;

        } catch (SQLException e) {
            System.err.println("Erro: Ocorreu um problema inesperado ao tentar cadastrar o usuário.");
            e.printStackTrace();
            // Retorna 'false' para indicar que a operação falhou.
            return false;
        }
    }

    
}