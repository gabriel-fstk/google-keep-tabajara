// src/components/AnotacaoList.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AnotacaoCard from './AnotacaoCard';
import AnotacaoForm from './AnotacaoForm';

const AnotacaoList = () => {
  const [anotacoes, setAnotacoes] = useState([]);
  const [isFormVisible, setFormVisible] = useState(false);
  const [anotacaoParaEdicao, setAnotacaoParaEdicao] = useState(null);
  // const [idBuscar, setIdBuscar] = useState('');
  // const [anotacaoEncontrada, setAnotacaoEncontrada] = useState(null);

  useEffect(() => {
    fetchAnotacoes();
  }, []);

  const fetchAnotacoes = async () => {
    try {
      const response = await axios.get('http://localhost:8080/anotacoes');
      setAnotacoes(response.data);
    } catch (error) {
      console.error('Erro ao buscar anotações', error);
    }
  };

  // const handleBuscar = async () => {
  //   if (idBuscar) {
  //     try {
  //       const response = await axios.get(`http://localhost:8080/anotacoes/${idBuscar}`);
  //       setAnotacaoEncontrada(response.data);
  //     } catch (error) {
  //       console.error('Erro ao buscar anotação por ID', error);
  //       setAnotacaoEncontrada(null);
  //     }
  //   }
  // };

  const handleSave = () => {
    fetchAnotacoes();
    setFormVisible(false);
    setAnotacaoParaEdicao(null);
  };

  const handleAddClick = () => {
    setFormVisible(true);
    setAnotacaoParaEdicao(null);
  };

  const handleEdit = (anotacao) => {
    setAnotacaoParaEdicao(anotacao);
    setFormVisible(true);
  };

  const handleCloseCard = () => {
    setFormVisible(false);
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/anotacoes/${id}`);
      fetchAnotacoes();
    } catch (error) {
      console.error('Erro ao excluir anotação', error);
    }
  };

  // const handleClonar = async (id) => {
  //   try {
  //     const response = await axios.post(`http://localhost:8080/anotacoes/copiar/${id}`);
  //     setAnotacoes([...anotacoes, response.data]);
  //   } catch (error) {
  //     console.error('Erro ao clonar anotação', error);
  //   }
  // };

  return (
    <div className="container">
      <h1>Google Keep Tabajara</h1>
      <button onClick={handleAddClick} className="btn btn-primary mb-3">
        Adicionar Anotação
      </button>
      {isFormVisible && (
        <AnotacaoForm
          anotacao={anotacaoParaEdicao}
          onSave={handleSave}
          onCancel={handleCloseCard}
        />
      )}
      <div className="row">
        {anotacoes.map(anotacao => (
          <div className="col-md-4 mb-4" key={anotacao.id}>
            <AnotacaoCard
              anotacao={anotacao}
              onEdit={() => handleEdit(anotacao)}
              onDelete={() => handleDelete(anotacao.id)}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default AnotacaoList;
