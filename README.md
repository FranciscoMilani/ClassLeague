# ClassLeague – Gerenciamento de Torneios Esportivos

O ClassLeague é um software desktop para Windows desenvolvido para auxiliar a organização e gerenciamento de campeonatos esportivos em ambientes escolares. O sistema automatiza o controle de torneios, gerencia partidas (com cronômetro e placar) e gera rankings de times e jogadores, facilitando a administração de competições escolares.

<img src="https://github.com/user-attachments/assets/55df50a9-f330-43f8-b2be-bafeb930f125" alt="ClassLeague Screenshot"  width="600" />


## Sobre

O ClassLeague foi idealizado, projetado e desenvolvido por [alunos](#autores) da disciplina de Projeto Temático II da [Universidade de Caxias do Sul (UCS)](https://www.ucs.br/site). O software foi concebido para gerenciar torneios esportivos, possibilitando o cadastro de alunos, treinadores, turmas, times e a organização completa das competições – desde o chaveamento dos confrontos até o controle do tempo das partidas e a visualização dos rankings. A metodologia utilizada abrangeu as fases de especificação, modelagem (incluindo diagramas UML e casos de uso) e implementação, seguindo práticas modernas de Engenharia de Software.

## Começando

As instruções abaixo permitirão que você configure o ambiente de desenvolvimento e execute o sistema ClassLeague.

### Dependências

- **Java JDK 20** – Versão estável para desenvolvimento desktop.
- **Swing** – Biblioteca para construção da interface gráfica.
- **Maven** – Para gerenciamento de dependências e build do projeto.
- **PostgreSQL** – Banco de dados relacional para armazenamento dos dados.
- **Hibernate e JPA** – Framework e API para mapeamento objeto-relacional.
- **OpenCSV** – Parser para manipulação e exportação de arquivos CSV.

### Instalando

1. **Download do Código-Fonte**  
   Clone ou faça o download do repositório do projeto.

2. **Configuração do Ambiente**  
   - Instale o Java JDK 20.
   - Configure o PostgreSQL com um banco de dados local.
   - Certifique-se de ter o Maven instalado.

3. **Build do Projeto**  
   No diretório raiz do projeto, execute:

```
mvn clean install
```

### Executando o Programa

1. Navegue até o diretório onde os arquivos executáveis foram gerados.
2. Execute o arquivo principal (por exemplo, `ClassLeague.exe` para Windows ou via comando:
```
java -jar ClassLeague.jar
```
3. Configure o programa conforme sua necessidade, como os diretórios para dados e logs.

## Demonstração de Telas

### Tela Principal

- **Visualização Geral:**  
Exibe uma lista dos torneios cadastrados, com filtros de data e pesquisa por nome. Um botão para criação de um novo torneio facilita o acesso rápido à funcionalidade de cadastro.

### Cadastro de Torneios

- **Registro de Campeonatos:**  
Permite o cadastro de torneios com informações básicas (nome, datas e modalidade esportiva) e a seleção dos times participantes. O sistema gera automaticamente os confrontos com base no número de equipes (de acordo com a lógica de chaveamento simples).

### Gerenciamento de Partidas

- **Tela de Partidas:**  
Exibe os detalhes da partida selecionada, incluindo os times envolvidos, controle do cronômetro e área para inserção dos pontos dos jogadores. Permite também a finalização da partida ou avanço para a próxima fase do torneio.

### Outras Telas

- **Cadastro de Alunos, Turmas, Times e Treinadores:**  
Oferece formulários dedicados para cada cadastro, garantindo que os dados necessários sejam inseridos e relacionados corretamente (por exemplo, os alunos devem estar associados à mesma turma que compõe um time).

## Funcionalidades

- **Gerenciamento Completo de Campeonatos:**  
Cadastro de torneios, times, alunos, treinadores e turmas.
- **Chaveamento Automático:**  
Geração aleatória e controlada dos confrontos com base no número de equipes participantes.
- **Controle de Partidas:**  
Cronômetro integrado para controle do tempo de jogo e registro de pontuação.
- **Exportação de Dados:**  
Funcionalidade para exportar resultados e rankings em formato CSV.
- **Interface Amigável e Intuitiva:**  
Desenvolvida com Swing, priorizando a usabilidade e simplicidade para o usuário final.

## Autores

- **Ezequiel Sartori Benelli** – [esbenelli@ucs.br](mailto:esbenelli@ucs.br)
- **Francisco Fornasier Milani** – [ffmilani@ucs.br](mailto:ffmilani@ucs.br)
- **Victor Scariot Menegotto** – [vsmenegotto@ucs.br](mailto:vsmenegotto@ucs.br)

## Referências
- EVANS, E. *Domain-Driven Design: Tackling Complexity in the Heart of Software.* Addison-Wesley.
- ISO/IEC 25010:2011 – Systems and Software Engineering – System and Software Quality Models.
- LARMAN, C. *Applying UML and Patterns: An Introduction to Object-oriented Analysis and Design and the Unified Process.* Prentice Hall.
- MARTIN, R. C. *Clean Architecture: A Craftsman’s Guide to Software Structure and Design.* Prentice Hall.
- SOMMERVILLE, I. *Software Engineering.* 9ª edição, Addison-Wesley.
- VALENTE, M. T. *Engenharia de Software Moderna: Princípios e Práticas para Desenvolvimento de Software com Produtividade.* Independente.

## Artigo Técnico de Desenvolvimento

Durante o desenvolvimento, todas as etapas – desde a especificação e modelagem até a implementação – foram documentadas detalhadamente. O projeto foi desenvolvido com base no artigo técnico intitulado **"ClassLeague: Planejamento e desenvolvimento de um software focado em gerenciar campeonatos para ambientes escolares"**, que pode ser consultado para obter uma visão aprofundada do planejamento, análise de requisitos, arquitetura candidata, diagrama de classes e casos de uso criados.

O download da versão final do documento, em .PDF, pode ser feito através do link abaixo:

<table>
  <tr>
    <td><img src="https://upload.wikimedia.org/wikipedia/commons/8/87/PDF_file_icon.svg" width="30" /></td>
    <td><a href="https://github.com/user-attachments/files/19495597/Artigo.ClassLeague.pdf" target="_blank"><strong>Artigo.pdf</strong></a></td>
  </tr>
</table>


