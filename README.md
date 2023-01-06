# Teste backend Attornatus

Teste backend da Attornatus ao qual foi criada uma API em Java - Spring para gerenciar pessoas.

## Instalação

Clone o repositório e espere baixar as dependências do projeto.

## Observações a cerca do projeto

- O banco de dados h2 já vem com seed de algumas pessoas;
- Foi utilizada a versão mais nova do Spring;
- Foi utilizado o Lombok para diminuição do código boilerplate. Previamente já configurado no IntelliJ;
- Não estou usando a anotação @EqualsAndHashCode do Lombok pois a utilização da mesma não é recomendada em entidades JPA podendo causar severa perca de performance e consumo excessivo de memória;
- O código está coberto com testes unitários;
