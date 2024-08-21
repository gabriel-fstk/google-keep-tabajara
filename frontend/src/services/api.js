import axios from 'axios';

const API_URL = 'http://localhost:8080/anotacoes'; // Ajuste conforme o endpoint do seu backend

export const listarAnotacoes = async () => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const adicionarAnotacao = async (anotacao) => {
    const response = await axios.post(API_URL, anotacao);
    return response.data;
};

export const alterarAnotacao = async (anotacao) => {
    const response = await axios.put(`${API_URL}/${anotacao.id}`, anotacao);
    return response.data;
};

export const excluirAnotacao = async (id) => {
    const response = await axios.delete(`${API_URL}/${id}`);
    return response.data;
};

export const copiarAnotacao = async (id) => {
    const response = await axios.post(`${API_URL}/copiar/${id}`);
    return response.data;
};
