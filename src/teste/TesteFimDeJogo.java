package teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jogo.ConfiguracoesRegras;
import jogo.Jogo;
import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;
import regras.MotorDeCaptura;

public class TesteFimDeJogo {
	private static int falhas = 0;

	public static void main(String[] args) throws Exception {
		testarVitoriaJogador1SemCapturas();
		testarEmpateDePontosNaoEmpataOJogo();
		testarPartidaSimuladaVitoriaJogador2();

		System.out.println();
		if (falhas == 0) {
			System.out.println("Todos os testes passaram.");
		} else {
			System.out.println(falhas + " teste(s) falharam.");
		}
	}

	// J1 nas casas ímpares (5 cartas), J2 nas pares (4 cartas), sem nenhuma captura
	private static void testarVitoriaJogador1SemCapturas() {
		Jogo jogo = novoJogo(config(false, false, false));
		Jogador j1 = jogo.getJogador1();
		Jogador j2 = jogo.getJogador2();
		Tabuleiro t = jogo.getTabuleiro();

		preencher(t, j1, j2, new int[] { 1, 3, 5, 7, 9 }, new int[] { 2, 4, 6, 8 });

		verificar("Vitória J1: tabuleiro cheio", t.estaCheio());
		verificar("Vitória J1: 5 células contra 4", t.contarCelulas(j1) == 5 && t.contarCelulas(j2) == 4);
		verificar("Vitória J1: getVencedor devolve o J1", jogo.getVencedor() == j1);
	}

	// sem capturas os pontos ficam 5x5, mas quem jogou 5 cartas tem mais células e
	// vence
	private static void testarEmpateDePontosNaoEmpataOJogo() {
		Jogo jogo = novoJogo(config(false, false, false));
		Jogador j1 = jogo.getJogador1();
		Jogador j2 = jogo.getJogador2();
		Tabuleiro t = jogo.getTabuleiro();

		preencher(t, j1, j2, new int[] { 1, 3, 5, 7, 9 }, new int[] { 2, 4, 6, 8 });

		verificar("Empate de pontos: 5x5", j1.getPontos() == 5 && j2.getPontos() == 5);
		verificar("Empate de pontos: o jogo não termina empatado", jogo.getVencedor() != null);
		verificar("Empate de pontos: vence quem tem mais células (J1)", jogo.getVencedor() == j1);
	}

	private static void testarPartidaSimuladaVitoriaJogador2() throws Exception {
		Jogo jogo = novoJogo(config(false, false, false));
		Jogador j1 = jogo.getJogador1();
		Jogador j2 = jogo.getJogador2();
		Tabuleiro t = jogo.getTabuleiro();
		MotorDeCaptura motor = new MotorDeCaptura(config(false, false, false));

		int[] jogadas = { 1, 2, 3, 6, 5, 4, 7, 8, 9 };
		int capturasJ2 = 0;

		jogo.trocarJogador(); // sem jogador da vez ainda, passa a ser o J1
		for (int i = 0; i < jogadas.length; i++) {
			Jogador jogadorAtual = jogo.getJogadorAtual();
			Carta carta;
			if (jogadorAtual == j1) {
				carta = new Carta("Fraca" + i, 1, 1, 1, 1, "NEUTRAL");
			} else {
				carta = new Carta("Forte" + i, 9, 9, 9, 9, "NEUTRAL");
			}

			t.colocarCarta(jogadas[i], carta, jogadorAtual);
			List<Integer> viraram = motor.aplicar(t, jogadas[i], jogadorAtual);
			jogo.atualizarPontuacao(viraram.size());
			if (jogadorAtual == j2) {
				capturasJ2 += viraram.size();
			}

			if (!t.estaCheio()) {
				jogo.trocarJogador();
			}
		}

		verificar("Partida simulada: termina com as 9 células ocupadas", t.estaCheio());
		verificar("Partida simulada: J2 capturou 4 cartas", capturasJ2 == 4);
		verificar("Partida simulada: J2 8 células e J1 1", t.contarCelulas(j2) == 8 && t.contarCelulas(j1) == 1);
		verificar("Partida simulada: J2 9 pontos e J1 1", j2.getPontos() == 9 && j1.getPontos() == 1);
		verificar("Partida simulada: soma dos pontos continua 10", j1.getPontos() + j2.getPontos() == 10);
		verificar("Partida simulada: o J1 começou, mas o vencedor é o J2", jogo.getVencedor() == j2);
		verificar("Partida simulada: a única célula do J1 é a 9", t.getCelula(9).getDono() == j1);
	}

	private static ConfiguracoesRegras config(boolean same, boolean plus, boolean combo) {
		ConfiguracoesRegras config = new ConfiguracoesRegras();
		config.setSame(same);
		config.setPlus(plus);
		config.setCombo(combo);
		return config;
	}

	private static void preencher(Tabuleiro t, Jogador j1, Jogador j2, int[] casasJ1, int[] casasJ2) {
		for (int posicao : casasJ1) {
			t.colocarCarta(posicao, carta(), j1);
		}
		for (int posicao : casasJ2) {
			t.colocarCarta(posicao, carta(), j2);
		}
	}

	private static Carta carta() {
		return new Carta("C", 1, 1, 1, 1, "NEUTRAL");
	}

	private static Jogo novoJogo(ConfiguracoesRegras config) {
		List<Carta> baralho = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Carta carta = new Carta("C" + i, 1, 1, 1, 1, "NEUTRAL");
			baralho.add(carta);
		}
		return new Jogo("Jogador 1", "Jogador 2", baralho, config, new Random(1));
	}

	private static void verificar(String descricao, boolean condicao) {
		if (condicao) {
			System.out.println("[OK] " + descricao);
		} else {
			System.out.println("[FALHOU] " + descricao);
			falhas++;
		}
	}
}
