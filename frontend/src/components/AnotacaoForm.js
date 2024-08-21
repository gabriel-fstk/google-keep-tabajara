// src/components/AnotacaoForm.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AnotacaoForm = ({ anotacao, onSave, onCancel }) => {
  const [titulo, setTitulo] = useState('');
  const [descricao, setDescricao] = useState('');
  const [cor, setCor] = useState('');
  const [caminhoArquivo, setCaminhoArquivo] = useState('');
  const [dataHora, setDataHora] = useState('');

  const formatDateTimeForInput = (dateTime) => {
    const date = new Date(dateTime);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  };
  

  useEffect(() => {
    if (anotacao) {
      setTitulo(anotacao.titulo || '');
      setDescricao(anotacao.descricao || '');
      setCor(anotacao.cor || '');
      setCaminhoArquivo(anotacao.caminhoArquivo || '');
      setDataHora(formatDateTimeForInput(anotacao.dataHora));
    }
  }, [anotacao]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formattedDataHora = new Date(dataHora).toISOString(); // 'yyyy-MM-ddThh:mm:ss.sssZ'

    const novaAnotacao = {titulo, dataHora: formattedDataHora, descricao, cor, caminhoArquivo };
    try {
      if (anotacao && anotacao.id) {
        let anotacaoAtualziada = {id: anotacao.id, ...novaAnotacao};
        
        await axios.put(`http://localhost:8080/anotacoes/${anotacao.id}`, anotacaoAtualziada);
        console.log(anotacaoAtualziada);
      } else {
        await axios.post('http://localhost:8080/anotacoes', novaAnotacao);
      }
      onSave();
    } catch (error) {
      console.error('Erro ao salvar anotação', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="titulo">Título</label>
        <input
          type="text"
          className="form-control"
          id="titulo"
          value={titulo}
          onChange={(e) => setTitulo(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label htmlFor="descricao">Descrição</label>
        <textarea
          className="form-control"
          id="descricao"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label htmlFor="cor">Cor</label>
        <input
          type="color"
          className="form-control"
          id="cor"
          value={cor}
          onChange={(e) => setCor(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label htmlFor="caminhoArquivo">Caminho do Arquivo</label>
        <input
          type="text"
          className="form-control"
          id="caminhoArquivo"
          value={caminhoArquivo}
          onChange={(e) => setCaminhoArquivo(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label htmlFor="dataHora">Data e Hora</label>
        <input
          type="datetime-local"
          className="form-control"
          id="dataHora"
          value={dataHora}
          onChange={(e) => setDataHora(e.target.value)}
        />
      </div>
      <button type="submit" className="btn btn-primary">Salvar</button>
      <button type="button" className="btn btn-secondary" onClick={onCancel}>Cancelar</button>
    </form>
  );
};

export default AnotacaoForm;
