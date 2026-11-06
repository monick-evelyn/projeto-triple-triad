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

public class TestePontuacao {

	private static int falhas = 0;

	public static void main(String[] args) throws Exception {
		testarInicio();
		testarAtualizarPontuacao();
		testarSemCaptura();
		testarSame();
		testarPlus();
		testarCombo();

		System.out.println();
		if (falhas == 0) {
			System.out.println("Todos os testes passaram.");
		} else {
			System.out.println(falhas + " teste(s) falharam.");
		}
	}

	private static void testarInicio() {
		Jogo jogo = novoJogo(new ConfiguracoesRegras());
		Jogador a = jogo.getJogador1();
		Jogador b = jogo.getJogador2();
		verificar("Início: os dois começam com 5", a.getPontos() == 5 && b.getPontos() == 5);
		verificar("Início: soma 10", a.getPontos() + b.getPontos() == 10);
	}

	private static void testarAtualizarPontuacao() throws IllegalAccessException {
		Jogo jogo = novoJogo(new ConfiguracoesRegras());
		Jogador jogador1 = jogo.getJogador1();
		Jogador jogador2 = jogo.getJogador2();

		jogo.trocarJogador(); // sem jogador da vez ainda, passa a ser o jogador 1
		verificar("Vez do jogador 1", jogo.getJogadorAtual() == jogador1);

		jogo.atualizarPontuacao(2);
		verificar("2 capturas: J1 7 e J2 3", jogador1.getPontos() == 7 && jogador2.getPontos() == 3);

		jogo.trocarJogador();
		jogo.atualizarPontuacao(1);
		verificar("J2 recaptura 1: J1 6 e J2 4", jogador1.getPontos() == 6 && jogador2.getPontos() == 4);
		verificar("Soma continua 10", jogador1.getPontos() + jogador2.getPontos() == 10);
	}

	private static void testarSemCaptura() throws IllegalAccessException {
		Jogo jogo = novoJogo(new ConfiguracoesRegras());
		jogo.trocarJogador();
		jogo.atualizarPontuacao(0);
		verificar("Sem captura: pontos não mudam",
				jogo.getJogador1().getPontos() == 5 && jogo.getJogador2().getPontos() == 5);
	}

	// Same: J1 joga na 4 e captura 1 e 5 (cartas do J2)
	private static void testarSame() throws Exception {
		ConfiguracoesRegras config = config(true, false, false);
		Jogo jogo = novoJogo(config);
		Jogador j1 = jogo.getJogador1();
		Jogador j2 = jogo.getJogador2();
		Tabuleiro t = jogo.getTabuleiro();
		t.colocarCarta(1, new Carta("A", 9, 9, 10, 9, "NEUTRAL"), j2);
		t.colocarCarta(5, new Carta("B", 9, 9, 9, 0, "NEUTRAL"), j2);
		t.colocarCarta(4, new Carta("P", 10, 0, 1, 1, "NEUTRAL"), j1);

		jogo.trocarJogador(); // vez do J1
		List<Integer> viraram = new MotorDeCaptura(config).aplicar(t, 4, j1);
		jogo.atualizarPontuacao(viraram.size());
		verificar("Same: +2 para J1 e -2 para J2", j1.getPontos() == 7 && j2.getPontos() == 3);
	}

	// Plus: 3 + 4 = 7 e 2 + 5 = 7, J1 captura 2 e 4
	private static void testarPlus() throws Exception {
		ConfiguracoesRegras config = config(false, true, false);
		Jogo jogo = novoJogo(config);
		Jogador j1 = jogo.getJogador1();
		Jogador j2 = jogo.getJogador2();
		Tabuleiro t = jogo.getTabuleiro();
		t.colocarCarta(2, new Carta("Cima", 1, 1, 4, 1, "NEUTRAL"), j2);
		t.colocarCarta(4, new Carta("Esq", 1, 5, 1, 1, "NEUTRAL"), j2);
		t.colocarCarta(5, new Carta("Centro", 3, 1, 1, 2, "NEUTRAL"), j1);

		jogo.trocarJogador(); // vez do J1
		List<Integer> viraram = new MotorDeCaptura(config).aplicar(t, 5, j1);
		jogo.atualizarPontuacao(viraram.size());
		verificar("Plus: J1 7 e J2 3", j1.getPontos() == 7 && j2.getPontos() == 3);
	}

	// Combo: J2 joga na 5, captura a 4 pela básica e a carta da 4 captura a 1 em cadeia
	private static void testarCombo() throws Exception {
		ConfiguracoesRegras config = config(false, false, true);
		Jogo jogo = novoJogo(config);
		Jogador j1 = jogo.getJogador1();
		Jogador j2 = jogo.getJogador2();
		Tabuleiro t = jogo.getTabuleiro();
		t.colocarCarta(1, new Carta("Alvo", 1, 1, 1, 1, "NEUTRAL"), j1);
		t.colocarCarta(4, new Carta("Elo", 9, 1, 1, 1, "NEUTRAL"), j1);
		t.colocarCarta(5, new Carta("Atacante", 1, 1, 1, 5, "NEUTRAL"), j2);

		jogo.trocarJogador();
		jogo.trocarJogador(); // vez do J2
		List<Integer> viraram = new MotorDeCaptura(config).aplicar(t, 5, j2);
		jogo.atualizarPontuacao(viraram.size());
		verificar("Combo: a captura em cadeia também pontua (J2 7, J1 3)",
				j2.getPontos() == 7 && j1.getPontos() == 3);
	}

	private static ConfiguracoesRegras config(boolean same, boolean plus, boolean combo) {
		ConfiguracoesRegras config = new ConfiguracoesRegras();
		config.setSame(same);
		config.setPlus(plus);
		config.setCombo(combo);
		return config;
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