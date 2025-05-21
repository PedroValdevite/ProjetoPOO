package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:postgresql://db.drizknwjxvcknjtseiox.supabase.co:5432/postgres?user=postgres&password=jairleopedro123";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "jairleopedro123";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
