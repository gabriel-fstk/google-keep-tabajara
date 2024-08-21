import React from 'react';

const AnotacaoItem = ({ anotacao, onEdit, onDelete }) => {
    return (
        <div>
            <h3>{anotacao.titulo}</h3>
            <p>{anotacao.descricao}</p>
            <p>Cor: {anotacao.cor}</p>
            <p>Arquivo: {anotacao.caminhoArquivo}</p>
            <button onClick={onEdit}>Editar</button>
            <button onClick={onDelete}>Excluir</button>
        </div>
    );
};

export default AnotacaoItem;
