package unoeste.fipp.sistemafx.db.util;

public class SingletonDB {
    private static Conexao conexao=null;

    private SingletonDB() {
    }

    public static boolean conectar()
    {
        conexao=new Conexao();
        return conexao.conectar("jdbc:postgresql://localhost:5432/","pedidos_db","postgres","postgre123");
    }
    public static Conexao getConexao() {
        return conexao;
    }
}
