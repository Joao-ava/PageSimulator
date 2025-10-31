# Simulador de algoritmos de paginação

João Alex Vieira de Almeida </br>
Taís Moreira Rodrigues

Palavras-chave: Simulador. Algoritmos de paginação. FIFO. LRU. NFU. Clock.

## Resumo

O presente trabalho tem como objetivo o desenvolvimento de um simulador gráfico de algoritmos de substituição de páginas em memória virtual. O sistema permite visualizar, passo a passo, o funcionamento de diferentes estratégias de paginação, comparando o número de faltas de página em cenários distintos. A ferramenta foi implementada em Java, com interface construída em Swing, e tem como foco fins didáticos, auxiliando o entendimento do comportamento dos algoritmos FIFO, LRU, NFU e Clock.

O simulador recebe como parâmetros o tamanho da memória e a sequência de páginas referenciadas, apresentando como resultado o número total de faltas e o estado da memória a cada iteração. Os resultados obtidos mostram a diferença de eficiência entre os algoritmos conforme o padrão de acesso utilizado.

## Introdução

Algoritmos de paginação são essenciais para sistemas operacionais modernos quando não há espaço suficiente na memória para todos os processos, existem diferentes abordagens para lidar com a paginação cada um com um algoritmo, com o objetivo de visualizar e comparar o comportamento desses algoritmos neste trabalho explicamos a criação de um simulador de algoritmos de paginação, recebendo uma sequência de páginas, e demonstrando como cada algoritmo trabalha para com a substituição uma vez em que ocorre uma falta de página.

## Metodologia

O simulador implementa os algoritmos de paginação FIFO (First In, First Out), Relógio (Clock), LRU (Least Recently Used) e NFU (Not Frequently Used) na linguagem de programação JAVA com cada algoritmo sendo implementado em uma classe própria, o simulador recebe dois parâmetros de entrada a quantidade de partições da memória e uma cadeia de inteiros que representam a ordem de páginas que foram utilizadas, como saída o simulador retorna a quantidade de faltas de páginas para cada algoritmo, dessa forma podendo se ter uma base comparativa entre os algoritmos. Opcionalmente mostra uma tabela com o estado da memória e páginas que saíram em cada passo da entrada para melhorar o compreendimento do status interno do algoritmo. Para melhor compreensão e uso do simulador foi desenvolvido a interface gráfica com a biblioteca Swing.
Resultados e Discussão

Ao executar o simulador com diferentes configurações de memória e sequências de páginas, foi possível observar o impacto da escolha do algoritmo na quantidade total de faltas de página.
No exemplo apresentado, com uma sequência de referência 1 2 3 4 5 6 5 4 3 2 1 e memória com 3 partições, obteve-se os seguintes resultados: FIFO com 9 faltas de página, LRU com 9 faltas de página, NFU com 9 faltas de página e Clock também com 9 faltas de página.

![Tela principal](./assets/main-screen.png)

Esse comportamento demonstra que, para esta sequência específica, todos os algoritmos apresentaram desempenho semelhante. Contudo, ao testar com outras sequências, observou-se que o LRU tende a apresentar menor número de faltas em padrões de acesso repetitivos, enquanto o FIFO pode sofrer com o fenômeno de Belady, em que o aumento do número de molduras não garante a redução de faltas.

O Clock e o NFU mostraram-se bons compromissos entre eficiência e simplicidade de implementação, sendo frequentemente utilizados em sistemas reais devido à sua boa relação entre custo e desempenho.

O projeto PageSimulator pode ser executado de duas formas. A primeira é pela IDE IntelliJ IDEA: abra a pasta do projeto, certifique-se de que a pasta src está marcada como Source Root (geralmente é automático), abra o arquivo src/Main.java e execute a classe Main por meio do menu Run > Run 'Main'. Ao fazer isso, a interface gráfica desenvolvida com Swing será exibida automaticamente.

A segunda forma é pelo terminal. Estando na raiz do projeto (PageSimulator), compile os arquivos com o comando **javac -d out src/core/\*.java src/ui/\*.java src/Main.java** e execute o programa com **java -cp out Main**. Se desejar gerar um arquivo JAR simples, utilize **jar --create --file PageSimulator.jar -C out**. e, para executá-lo, digite **java -cp PageSimulator.jar Main**.

## Conclusão

Com esse trabalho podemos observar os diferentes comportamentos dos algoritmos de substituição de páginas e ver em quais cenários surgem seus pontos positivos e negativos, com isso entendendo melhor onde usar cada algoritmo.

## Referências

ALMEIDA, João Alex Vieira de; RODRIGUES, Taís Moreira. Simulador de algoritmos de substituição de páginas (GUI em Java). Fortale­za: Universidade de Fortaleza, Centro de Ciências Tecnológicas, Curso de Ciência da Computação, 2025. Disponível em: [https://github.com/Joao-ava/PageSimulator](https://github.com/Joao-ava/PageSimulator) . Acesso em: 31/10/2025.
