package dao;

import model.Anotacao;

import java.sql.SQLException;
import java.util.List;

public interface AnotacaoDAO {
    void adicionarAnotacao(Anotacao anotacao) throws SQLException;
    void alterarAnotacao(Anotacao anotacao) throws SQLException;
    void excluirAnotacao(int id) throws SQLException;
    Anotacao copiarAnotacao(int id) throws SQLException;
    List<Anotacao> listarAnotacoes() throws SQLException;
}
