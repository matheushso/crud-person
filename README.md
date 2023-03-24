# 💻 Projeto teste técnico de Java com Spring [API REST]

# ✔️ Tecnologias utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- Rest assured [Testes integrados]
- Maven
- MySQL
- OpenCSV
- Lombok

# 📂 Documentação da API
<div align="center">
<img src="https://user-images.githubusercontent.com/51098870/227647799-cfee900b-61aa-4142-be84-d2d368c5a6d8.png" width="700px" />
</div>
Disponível no arquivo "API_documentation.yaml" na raiz do projeto e a collection com os endpoints está disponível no arquivo "CRUD Person.postman_collection.json"

# ⚙️ Como rodar a aplicação
1. Certifique-se de ter o Java JDK 17 instalado em sua máquina. Você pode verificar a versão do Java digitando o comando java -version no prompt de comando ou terminal.

2. Faça o download do código fonte da aplicação ou clone o repositório da aplicação do GitHub para a sua máquina.

3. Abra o prompt de comando ou terminal e navegue até o diretório raiz do projeto, onde está localizado o arquivo pom.xml.

4. Para executar a aplicação, execute o seguinte comando na pasta raiz do projeto:
"java -jar crud-person-0.0.1-SNAPSHOT.jar --server.port=8080 --spring.datasource.username=nome-de-usuario --spring.datasource.password=senha-do-usuario"
Importante possuir o banco MySQL e informar o usuário e senha.

5. A aplicação deve estar sendo executada agora e você poderá acessá-la em um navegador ou ferramenta de requisições como o Postman, usando a URL http://localhost:8080 ou a porta definida em sua aplicação.
