# Desafio DS Movie RestAssured
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/MarcusViniciusGodoy/DSMovie-RestAssured/blob/main/LICENSE)

# Sobre o projeto

Projeto DSCOMMERCE do curso Java Spring Professional, cursos organizado pela [DevSuperior](https://devsuperior.com "Site da DevSuperior").

Este é um projeto de filmes e avaliações de filmes. A visualização dos dados dos filmes é pública (não necessita login), porém as alterações de filmes (inserir, atualizar, deletar) são permitidas apenas para usuários ADMIN. As avaliações de filmes podem ser registradas por qualquer usuário logado CLIENT ou ADMIN. A entidade Score armazena uma nota de 0 a 5 (score) que cada usuário deu a cada filme. Sempre que um usuário registra uma nota, o sistema calcula a média das notas de todos usuários, e armazena essa nota média (score) na entidade Movie, juntamente com a contagem de votos (count).

## UML
![Client](https://github.com/MarcusViniciusGodoy/assets/blob/main/dsmovie.PNG)

## RestAssured
![RestAssured](https://github.com/MarcusViniciusGodoy/assets/blob/main/dsmovieRest.PNG)

# Tecnologias Utilizadas
## Back end
- Java
- Spring Boot
- Maven

## Testes Automatizados
- RestAssured

# Autor
Marcus Vinícius de Godoy

https://www.linkedin.com/in/marcus-vin%C3%ADcius-godoy-15b5461a0/
