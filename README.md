<div align="center">

# 🃏 Jogo Triple Trip 🃏

### Uma versão de terminal do clássico Triple Triad

Projeto desenvolvido em Java para a disciplina de **Paradigmas de Programação**.

[![Java 17+](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![GitHub repository](https://img.shields.io/badge/GitHub-projeto--triple--trip-181717?logo=github)](https://github.com/monick-evelyn/projeto-triple-trip)

</div>

## ✨ Sobre o projeto

Triple Trip é uma implementação jogável de **Triple Triad** (inspirado em Final Fantasy VIII) feita com foco em aplicar conceitos de orientação a objetos. A partida acontece em um tabuleiro 3×3, com dois jogadores e cartas cujos valores determinam as capturas.\
**IDE Utilizada**: Eclipse

| 🎓 Componente curricular | 👩‍🎓 Curso |
| --- | --- |
| Paradigmas de Programação | Bacharelado em Ciência da Computação |

## 🚀 Como executar

Você precisa do **JDK 17 ou superior**. Execute os comandos a partir da raiz do projeto.

### Linux / macOS

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out principal.Main
```

### Windows (Prompt de Comando)

```cmd
if not exist out mkdir out
dir /s /b src\*.java > fontes.txt
javac -d out @fontes.txt
java -cp out principal.Main
```

O menu permite iniciar uma partida, consultar as regras e sair do jogo. Para executar os testes automatizados, compile o projeto e rode:

```bash
java -cp out teste.TodosOsTestes
```

## 🎮 Regras do jogo

- Tabuleiro de **3×3** e **5 cartas para cada jogador**.
- Uma carta captura a carta adversária adjacente quando seu valor naquele lado é maior.
- Cada jogador começa com 5 pontos; capturar uma carta soma 1 ponto e perder uma carta subtrai 1.
- Vence quem tiver mais células ao final da partida.
- Regras adicionais disponíveis: **Same**, **Plus**, **Combo** e **Three Open**.

## 🂠 Arquivo de cartas

As cartas são carregadas de `cards.csv`, codificado em **UTF-16** e separado por ponto e vírgula. Cada linha contém:

```text
id;name;ranks;element
```

Os valores de `ranks` seguem o formato `[UP:1,LEFT:5,DOWN:1,RIGHT:4]`. O valor **A** representa 10.

## 🧩 Paradigmas aplicados

- **Orientação a objetos:** entidades do jogo em `modelo` (`Carta`, `Jogador`, `Celula` e `Tabuleiro`); interface `Regra` e suas implementações; polimorfismo no motor de captura; `Jogo` coordena a partida e `ConfiguracoesRegras` ativa ou desativa regras.
- **Programação procedural:** `principal.Main` conduz o fluxo inicial, enquanto as classes de `terminal` cuidam da leitura de dados e da interação com o usuário.

## 👩‍💻 Contribuidoras

| Monick Évelyn Soares Nóbrega de Araújo | Clarissa Sousa Gomes |
| --- | --- |
| [![GitHub](https://img.shields.io/badge/GitHub-monick--evelyn-181717?logo=github)](https://github.com/monick-evelyn) [![LinkedIn](https://img.shields.io/badge/LinkedIn-Monick-0A66C2?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/monickevelyn) [![Instagram](https://img.shields.io/badge/Instagram-monii.tech-E4405F?logo=instagram&logoColor=white)](https://www.instagram.com/monii.tech) [![E-mail](https://img.shields.io/badge/E--mail-contato-EA4335?logo=gmail&logoColor=white)](mailto:monick.evelyn.tech@gmail.com) | [![GitHub](https://img.shields.io/badge/GitHub-Clarissagomess-181717?logo=github)](https://github.com/Clarissagomess) [![LinkedIn](https://img.shields.io/badge/LinkedIn-Clarissa-0A66C2?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/clarissasousagomes/) [![E-mail](https://img.shields.io/badge/E--mail-contato-EA4335?logo=gmail&logoColor=white)](mailto:clarissasousags@gmail.com) |

## 🌿 Fluxo de trabalho

Cada tarefa foi desenvolvida em uma branch no formato `feature/TT-XX-nome-da-task`. As alterações são propostas por pull request para `developer` e precisam da aprovação da pessoa responsável pelo projeto.
