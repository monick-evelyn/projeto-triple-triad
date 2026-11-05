package teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jogo.ConfiguracoesRegras;
import jogo.Jogo;
import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;

public class TesteFimDeJogo {
	private static int falhas = 0;

	public static void main(String[] args) throws Exception {
		testarVitoriaJogador1SemCapturas();

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
