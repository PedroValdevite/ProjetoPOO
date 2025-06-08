package controller;

import dao.UsuarioDAO;
import java.sql.SQLException;

public class LoginController {

    private final UsuarioDAO usuarioDAO;

    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean realizarLogin(String login, String senha) {

        if (login == null || senha == null || login.trim().isEmpty() || senha.trim().isEmpty()) {
            System.out.println("[CONTROLLER LOG] Erro: Campos de login e senha não podem ser vazios.");
            return false;
        }

        try {
            boolean autenticado = usuarioDAO.autenticar(login, senha);

            if (autenticado) {
                System.out.println("[CONTROLLER LOG] Sucesso: Login para '" + login + "' realizado com sucesso!");
                return true;
            } else {
                System.out.println("[CONTROLLER LOG] Falha: Login ou senha inválidos para o usuário '" + login + "'.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("[CONTROLLER LOG] ERRO DE BANCO DE DADOS: Não foi possível realizar a autenticação.");
            e.printStackTrace();
            return false;
        }
    }
}