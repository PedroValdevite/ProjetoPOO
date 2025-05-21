package conexao;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            Conexao.conectar();
            System.out.println("Conexão OK!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
