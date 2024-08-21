import com.sun.net.httpserver.HttpServer;
import handler.AnotacaoHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/anotacoes", new AnotacaoHandler());
            server.setExecutor(null); // cria um executor padrão
            server.start();
            System.out.println("Servidor iniciado na porta 8080");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
