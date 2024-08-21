// src/components/AnotacaoCard.js
import React from 'react';

const AnotacaoCard = ({ anotacao, onEdit, onDelete, onClone }) => {
  const { id, titulo, descricao, cor, caminhoArquivo, dataHora } = anotacao;

  return (
    <div className="card mb-3" style={{ maxWidth: '100%', borderColor: cor }}>
      <div className="card-body">
        <h5 className="card-title">{titulo}</h5>
        <p className="card-text">{descricao}</p>
        <p className="card-text">Data e Hora: {dataHora}</p>
        <p className="card-text">Cor: {cor}</p>
        <p className="card-text">Caminho do Arquivo: {caminhoArquivo}</p>
        <div className="mt-3">
          <button onClick={() => onEdit(anotacao)} className="btn btn-secondary mr-2">Editar</button>
          <button onClick={() => onDelete(id)} className="btn btn-danger">Excluir</button>
          {/* <button onClick={onClone} className="btn btn-primary">Clonar</button> */}
        </div>
      </div>
    </div>
  );
};

export default AnotacaoCard;
