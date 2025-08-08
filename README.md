# CRUD de Cadastro de Pets (Terminal)

Projeto desenvolvido em Java que implementa um sistema CRUD para cadastro de pets, totalmente via terminal e utilizando arquivos `.txt` para persistência dos dados.

## Funcionalidades principais

- **Menu Inicial:**  
  - Entrar no menu de pets para manipular cadastros.  
  - Entrar no menu de formulário, onde é possível alterar ou adicionar novas perguntas ao cadastro de pets (exceto as perguntas pré-estabelecidas).

- **Menu PETS:**  
  - Cadastro de pets com validação rigorosa para todos os campos, garantindo que os dados inseridos estejam corretos.  
  - Listagem de todos os pets cadastrados.  
  - Busca flexível de pets por um ou dois atributos, conforme escolha do usuário.  
  - Busca por data de cadastro (Ano, Ano + Mês ou Ano + Mês + Dia).  
  - Exclusão ou edição do pet em qualquer lista retornada.

- **Menu Formulário:**  
  - Listagem de todas as perguntas.  
  - Permite edição ou exclusão das perguntas (exceto as pré-estabelecidas).  
  - Ao adicionar uma nova pergunta, ela é dinamicamente incorporada ao CRUD dos pets.

- Persistência de dados totalmente realizada em arquivos `.txt`.

## Tecnologias usadas

- Java 17  
  - Java IO  
  - Programação Orientada a Objetos (POO)  
  - Collections  
- Arquitetura inspirada no padrão MVC (Model-View-Controller)
