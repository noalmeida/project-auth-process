### Authorizer/Creation application

#### name project:  **authorizer-cllg**.



#### Alguns pontos sobre o projeto/requisitos e decisões de desenvolvimento:

- Java como linguagem escolhida, por ser uma ótima opção para desenvolvimento de operações que envolvem principalmente back-end, e grandes fluxos tanto de leitura, processamento, quanto armazenamento, por exemplo para operações bancárias, são sempre escolhidas essas tecnologias como java e spring.

  O Maven e Spring acabam auxiliando nas configurações em geral, como exemplo, ajuda a ter flexibilidade para compilar, buildar, instalações de plugins e dependências de maneira fácil.

  Usando dependencias de log,  jackson (para que seja possível trabalhar com o Json, ainda existindo Gson tmbém, mas optei por jackson) o spring-boot para facilitar a forma de rodar o app.

- Eu gostaria de ter construido a aplicação no modelo de Spring-batch com jobs e steps para leitura de arquivos. Pois acredito que é uma arquitetura bem adequada para os requisitos de leituras de dados e transações bancárias. Entretanto não consegui construir nessa arquitetura, consegui, utilizando basicamente java mesmo sem o spring-batch.

  

#### Processo de build e execução: 



Considerando  o projeto a ser executado no CLI prompt de comando.

Havendo a necessidade de que a máquina que está executando os procedimentos, tenha instalado em seu ambiente, o java e maven.*(podendo ser java de 8 ou superior, e Apache maven à partir do 3xx)*

**Estando na pasta raiz da aplicacao onde se encontra o pom.xml,**



1- executar:

 `mvn clean install` 

```csharp
/* para baixar as dependências, a primeira vez a ser rodado será mais demorado, levando talvez 1min mas depois será bem rapido a excecução, segundos. */
```

2 -executar:

`mvn spring-boot:run`

```csharp
/* para rodar a aplicação. */
```



#### :memo: Sobre o funcionamento da aplicação:

Agora que foi executado o comando de rodar a aplicação, temos uma execução realizada, considerando o arquivo ->   **operations.json** que está na pasta, localizada dentro do projeto com seguinte caminho:  **File**/input/operations.json, considerando a pasta raiz da aplicação.

Este arquivo que é a entrada de dados o qual o sistema consome, é crucial para que o módulo processe as informações e crie um arquivo de saída com as informações que foram processadas, que será o **authorizationTransactions.json**, localizado também na pasta File, no seguinte path: 

**File/output/authorizationTransactions.json.**



##### Fluxo do módulo :

Verifica o arquivo operations.json no input, que contem a seguinte estrutura de JSON como entrada :

```json
{
   "account":{
      "flow-process":"authorizer",
      "active-card":true,
      "available-limit":10,
      "transaction":[
         {
            "merchant":"Burger King",
            "amount":20,
            "time":"2019-02-13T11:01:00.000Z"
         },
         {
            "merchant":"Habbib's",
            "amount":90,
            "time":"2019-02-13T11:03:00.000Z"
         }
      ]
   }
}
```

Verifica o campo/chave :  `"flow-process":"authorizer"` se authorizer ou creation.

- se for **authorizer** : vai para o fluxo de verificação das violações das regras, com saida do arquivo após consumo e processamento -> 

  arquivo **authorizationTransactions.json** no path destino **File/output/authorizationTransactions.json**.

- se for **creation**: Se o campo/chave `"flow-process":"creation"`, será encaminhado para o fluxo de criação da conta, sendo a saída do arquivo> **accountCreated.json**  localizada também no destino de output: **File/output/accountCreated.json** .



No fluxo **authorizer**, o modulo verifica o limite do cartão, se o cartão está ativo, se existem transações duplicadas, ou num intervalo de 2min e

havendo alguma dessas situações, lança violações como exemplo de json de saída com violação de transação em intervalo curto de 2min : 



```json
{
   "account":{
      "flow-process":"AUTHORIZER",
      "active-card":true,
      "available-limit":10,
      "transaction":null
   },
   "violations":[
      {
         "violationType":"highfrequency-small-interval"
      }
   ]
}
```





Para fazer os testes de saída, precisa apenas mudar o dado do arquivo de entrada. Exemplo, quero verificar se vai ser lançado a violação de **insufficient-limit**, portanto, posso testar colocando um json de entrada com limit de 0 R$ ou limite menor do que o valor do campo Amout das transações realizadas.

````json
{
   "account":{
      "flow-process":"authorizer",
      "active-card":true,
      "available-limit":0,   // Ou Coloca Zero, ou qualquer valor que seja menor do que o amout de alguma transação.
      "transaction":[
         {
            "merchant":"Burger King",
            "amount":20,    // /Por exemplo, aqui é 20R$, um limite entre 0-20, irá lançar a violação de insufficientlimit.
            "time":"2019-02-13T11:01:00.000Z"
         },
         {
            "merchant":"Habbib's",
            "amount":90,
            "time":"2019-02-13T11:03:00.000Z"
         }
      ]
   }
}
````



Só verificar nos arquivos de entrada e saída as informações, existe um log também que será exibido no console/prompt mostrando em qual processo o dado de entrada foi colocado e lançado no processamento, e qual arquivo e onde foi lançado a saída.

Fim.  :space_invader:  :purple_heart:























