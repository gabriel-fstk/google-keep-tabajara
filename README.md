# Google Keep Tabajara

**Google Keep Tabajara** é um aplicativo de gerenciamento de anotações, inspirado no Google Keep, desenvolvido com React no frontend e Java com PostgreSQL no backend. O projeto permite criar, editar, clonar, excluir e listar anotações com título, data/hora, descrição, cor e arquivos anexados.

## Índice

- [Sobre](#sobre)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Backend](#configuração-do-backend)
- [Configuração do Frontend](#configuração-do-frontend)
- [Executando o Projeto](#executando-o-projeto)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Sobre

O Google Keep Tabajara é um sistema de anotações que inclui funcionalidades básicas como criação, edição, clonagem, exclusão e listagem de anotações. As anotações podem conter títulos, descrições, datas e arquivos. O projeto utiliza o padrão DAO para persistência de dados e aplica princípios de Orientação a Objetos.

## Tecnologias Utilizadas

- **Frontend:** React
- **Backend:** Java (nativo, sem Spring Boot)
- **Banco de Dados:** PostgreSQL

## Pré-requisitos

Antes de começar, certifique-se de ter os seguintes itens instalados:

- [Node.js](https://nodejs.org/) (para executar o frontend)
- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (para executar o backend)
- [PostgreSQL](https://www.postgresql.org/download/) (para o banco de dados)

## Configuração do Backend

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seuusuario/google-keep-tabajara.git
   cd google-keep-tabajara/backend
   ```

2. **Crie as pastas `lib` e `bin` se não existirem:**

   ```bash
   mkdir -p lib bin
   ```

3. **Configure o banco de dados:**

   - Crie um banco de dados no PostgreSQL.
   - Configure as credenciais e detalhes do banco de dados no arquivo `src/util/DBConnection.java`.

4. **Compile o backend:**

   - Navegue para o diretório `backend` e compile o código-fonte com o comando:

     ```bash
     javac -d bin -cp "lib/*" src/**/*.java
     ```

5. **Execute o servidor backend:**

   - Inicie o servidor backend com o comando:

     ```bash
     java -cp "bin:lib/*" Main
     ```

   Certifique-se de que o backend está rodando na porta `8080` ou ajuste o frontend conforme a configuração do backend.

## Configuração do Frontend

1. **Navegue para o diretório do frontend:**

   ```bash
   cd ../frontend
   ```

2. **Instale as dependências:**

   ```bash
   npm install
   ```

3. **Configure o URL da API:**

   - No arquivo `src/services/api.js`, verifique se a URL do backend está correta.

     ```javascript
     const API_URL = 'http://localhost:8080/anotacoes';
     ```

4. **Execute o frontend:**

   ```bash
   npm start
   ```

   O frontend será iniciado em `http://localhost:3000`.

## Executando o Projeto

Para rodar o projeto completo, siga os seguintes passos:

1. **Inicie o backend:**

   ```bash
   cd google-keep-tabajara/backend
   mkdir -p lib bin
   javac -d bin -cp "lib/*" src/**/*.java
   java -cp "bin:lib/*" Main
   ```

2. **Inicie o frontend:**

   ```bash
   cd ../frontend
   npm install
   npm start
   ```

   Acesse `http://localhost:3000` em seu navegador para usar o aplicativo.

## Contribuição

Contribuições são bem-vindas! Para contribuir com o projeto:

1. Faça um fork do repositório.
2. Crie uma branch para a sua feature (`git checkout -b feature/nome-da-feature`).
3. Faça commit das suas alterações (`git commit -am 'Adiciona nova feature'`).
4. Envie a branch para o repositório remoto (`git push origin feature/nome-da-feature`).
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---
