package dao;

import model.Anotacao;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


public class AnotacaoDAOImpl implements AnotacaoDAO {

    private Connection connection;

    public AnotacaoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void adicionarAnotacao(Anotacao anotacao) throws SQLException {
        
        String sql = "INSERT INTO anotacoes (titulo, dataHora, descricao, cor, caminhoArquivo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, anotacao.getTitulo());
            stmt.setTimestamp(2, anotacao.getDataHora());
            stmt.setString(3, anotacao.getDescricao());
            stmt.setString(4, anotacao.getCor());
            stmt.setString(5, anotacao.getCaminhoArquivo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar anotação: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void alterarAnotacao(Anotacao anotacao) throws SQLException {
        String sql = "UPDATE anotacoes SET titulo = ?, dataHora = ?, descricao = ?, cor = ?, caminhoArquivo = ? WHERE id = ?";
        System.out.println(anotacao.getId());
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, anotacao.getTitulo());
            stmt.setTimestamp(2, anotacao.getDataHora());
            stmt.setString(3, anotacao.getDescricao());
            stmt.setString(4, anotacao.getCor());
            stmt.setString(5, anotacao.getCaminhoArquivo());
            stmt.setInt(6, anotacao.getId());
           
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Nenhuma anotação foi atualizada. Verifique o ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao atualizar anotação: " + e.getMessage());
        }
    }

    @Override
    public void excluirAnotacao(int id) throws SQLException {
        String sql = "DELETE FROM anotacoes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Anotacao copiarAnotacao(int id) throws SQLException {
        Anotacao anotacao = buscarPorId(id);
        if (anotacao != null) {
            Anotacao novaAnotacao = new Anotacao();
            novaAnotacao.setTitulo(anotacao.getTitulo() + " (Cópia)");
            novaAnotacao.setDataHora(anotacao.getDataHora());
            novaAnotacao.setDescricao(anotacao.getDescricao());
            novaAnotacao.setCor(anotacao.getCor());
            novaAnotacao.setCaminhoArquivo(anotacao.getCaminhoArquivo());
            adicionarAnotacao(novaAnotacao);
            return novaAnotacao;
        }
        return null;
    }

    @Override
    public List<Anotacao> listarAnotacoes() throws SQLException {
        List<Anotacao> anotacoes = new ArrayList<>();
        String sql = "SELECT * FROM anotacoes ORDER BY dataHora DESC";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Anotacao anotacao = new Anotacao();
                anotacao.setId(rs.getInt("id"));
                anotacao.setTitulo(rs.getString("titulo"));
                anotacao.setDataHora(rs.getTimestamp("dataHora"));
                anotacao.setDescricao(rs.getString("descricao"));
                anotacao.setCor(rs.getString("cor"));
                anotacao.setCaminhoArquivo(rs.getString("caminhoArquivo"));
                anotacoes.add(anotacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anotacoes;
    }

    private Anotacao buscarPorId(int id) {
        String sql = "SELECT * FROM anotacoes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Anotacao anotacao = new Anotacao();
                anotacao.setId(rs.getInt("id"));
                anotacao.setTitulo(rs.getString("titulo"));
                anotacao.setDataHora(rs.getTimestamp("dataHora"));
                anotacao.setDescricao(rs.getString("descricao"));
                anotacao.setCor(rs.getString("cor"));
                anotacao.setCaminhoArquivo(rs.getString("caminhoArquivo"));
                return anotacao;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
