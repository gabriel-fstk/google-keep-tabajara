package handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Anotacao;
import dao.AnotacaoDAO;
import dao.AnotacaoDAOImpl;
import util.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AnotacaoHandler implements HttpHandler {

    private final AnotacaoDAO anotacaoDAO;

    public AnotacaoHandler() throws SQLException {
        // Inicializa o DAO com a conexão ao banco de dados
        this.anotacaoDAO = new AnotacaoDAOImpl(DBConnection.getConnection());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Adiciona headers CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        String method = exchange.getRequestMethod();

        if ("OPTIONS".equals(method)) {
            // Responde a solicitações preflight
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
            return;
        }

        try {
            switch (method) {
                case "GET":
                    handleGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "PUT":
                    handlePut(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                default:
                    exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
                    break;
            }
        } catch (SQLException | JsonSyntaxException e) {
            e.printStackTrace();
            String response = "Erro ao processar a solicitação: " + e.getMessage();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException, SQLException {
        List<Anotacao> anotacoes = anotacaoDAO.listarAnotacoes();
        Gson gson = new Gson();
        String response = gson.toJson(anotacoes);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handlePost(HttpExchange exchange) throws IOException, SQLException {
        Gson gson = new Gson();
        Anotacao anotacao = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Anotacao.class);

        anotacaoDAO.adicionarAnotacao(anotacao);
        String response = "Anotação adicionada com sucesso";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_CREATED, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handlePut(HttpExchange exchange) throws IOException, SQLException {
        Gson gson = new Gson();
        Anotacao anotacao = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Anotacao.class);
        anotacaoDAO.alterarAnotacao(anotacao);
        String response = "Anotação alterada com sucesso";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleDelete(HttpExchange exchange) throws IOException, SQLException {
        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");
        String idStr = parts[parts.length - 1];
        int id = Integer.parseInt(idStr);
        anotacaoDAO.excluirAnotacao(id);
        String response = "Anotação excluída com sucesso";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
