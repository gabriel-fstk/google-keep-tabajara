package controller;

import dao.AnotacaoDAO;
import dao.AnotacaoDAOImpl;
import model.Anotacao;
import util.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;



public class AnotacaoController extends HttpServlet {
    private AnotacaoDAO anotacaoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection connection = DBConnection.getConnection(); // Obter conexão com o banco
            anotacaoDAO = new AnotacaoDAOImpl(connection); // Inicializar o DAO com a conexão
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Anotacao> anotacoes = anotacaoDAO.listarAnotacoes();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(anotacoes));
        } catch (SQLException e) {
            // Tratar a exceção
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Anotacao anotacao = new Gson().fromJson(request.getReader(), Anotacao.class);
            anotacaoDAO.adicionarAnotacao(anotacao);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED); // Status 201 Created
            response.getWriter().write(new Gson().toJson(anotacao));
        } catch (SQLException e) {
            // Tratar a exceção
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getPathInfo().substring(1);
        int id = Integer.parseInt(idStr);
        try {
            anotacaoDAO.excluirAnotacao(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (SQLException e) {
            // Tratar a exceção
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Anotacao anotacao = new Gson().fromJson(request.getReader(), Anotacao.class);
            anotacaoDAO.alterarAnotacao(anotacao);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(anotacao));
        } catch (SQLException e) {
            // Tratar a exceção
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error\"}");
        }
    }
}
