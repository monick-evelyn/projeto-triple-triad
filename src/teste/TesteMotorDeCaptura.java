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
import regras.Regra;

public class TesteMotorDeCaptura {

	private static int falhas = 0;

	public static void main(String[] args) throws Exception {
		testarBasica();
		testarVizinhas();
		testarDuasRegras();
		testarCombo();
		testarSame();
		testarPlus();
		testarPontuacaoCombo();

		System.out.println();
		if (falhas == 0) {
			System.out.println("Todos os testes passaram.");
		} else {
			System.out.println(falhas + " teste(s) falharam.");
		}
	}

	private static ConfiguracoesRegras configuracao(boolean combo) {
		ConfiguracoesRegras config = new ConfiguracoesRegras();
		config.setCombo(combo);
		return config;
	}

	// Mesmo cenário da captura básica do TesteTabuleiro
	private static void testarBasica() throws Exception {
		Jogador azul = new Jogador("Azul");
		Jogador verde = new Jogador("Verde");
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(4, new Carta("Azul", 9, 2, 3, 9, "NEUTRAL"), azul);
		t.colocarCarta(5, new Carta("Verde", 3, 1, 4, 6, "NEUTRAL"), verde);

		List<Integer> viraram = new MotorDeCaptura(configuracao(false)).aplicar(t, 5, verde);

		verificar("Básica: 6 > 2 captura a posição 4", viraram.equals(List.of(4)));
		verificar("Básica: posição 4 passa a ser do Verde", t.getCelula(4).getDono() == verde);
		verificar("Básica: O motor não mexe nos pontos", verde.getPontos() == 5 && azul.getPontos() == 5);
	}

	private static void testarVizinhas() throws Exception {
		Jogador a = new Jogador("A");
		Jogador b = new Jogador("B");
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(4, new Carta("B1", 1, 1, 1, 1, "NEUTRAL"), b);
		t.colocarCarta(5, new Carta("B2", 9, 9, 9, 9, "NEUTRAL"), b);

		List<Integer> viraram = new MotorDeCaptura(configuracao(true)).aplicar(t, 5, b);

		verificar("Vizinhas vazias não geram erro e mesmo dono não é capturado", viraram.isEmpty());
		verificar("Mesmo dono: pontos não mudam", a.getPontos() == 5 && b.getPontos() == 5);
	}

	private static void testarDuasRegras() throws Exception {
		Jogador azul = new Jogador("Azul");
		Jogador verde = new Jogador("Verde");
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(4, new Carta("Azul", 9, 2, 3, 9, "NEUTRAL"), azul);
		t.colocarCarta(5, new Carta("Verde", 3, 1, 4, 6, "NEUTRAL"), verde);

		// Regra de teste que captura a mesma carta que a básica
		ConfiguracoesRegras config = new ConfiguracoesRegras() {
			@Override
			public List<Regra> regrasAtivas() {
				List<Regra> regras = new ArrayList<>();
				regras.add(new Regra() {
					@Override
					public List<Integer> capturas(Tabuleiro tabuleiro, int posicao, Jogador jogador) {
						return List.of(4);
					}

					@Override
					public String getNome() {
						return "Repetida";
					}
				});
				return regras;
			}
		};
		config.setCombo(false);

		MotorDeCaptura motor = new MotorDeCaptura(config);
		List<Integer> viraram = motor.aplicar(t, 5, verde);

		verificar("Duas regras: carta conta uma vez só", viraram.equals(List.of(4)));
		verificar("Duas regras: O motor não mexe nos pontos", verde.getPontos() == 5 && azul.getPontos() == 5);
		verificar("Duas regras: regra ativada é registrada", motor.getRegrasAtivadas().equals(List.of("Repetida")));
	}

	private static void testarCombo() throws Exception {
		// Sem combo: só a captura direta
		Jogador a1 = new Jogador("A");
		Jogador b1 = new Jogador("B");
		Tabuleiro t1 = montarCadeia(a1, b1);
		MotorDeCaptura semCombo = new MotorDeCaptura(configuracao(false));
		verificar("Sem combo: só a posição 4 vira", semCombo.aplicar(t1, 5, b1).equals(List.of(4)));
		verificar("Sem combo: posição 1 continua do A", t1.getCelula(1).getDono() == a1);

		// Com combo: a carta capturada em 4 captura a posição 1
		Jogador a2 = new Jogador("A");
		Jogador b2 = new Jogador("B");
		Tabuleiro t2 = montarCadeia(a2, b2);
		MotorDeCaptura comCombo = new MotorDeCaptura(configuracao(true));
		verificar("Combo: posições 4 e 1 viram", comCombo.aplicar(t2, 5, b2).equals(List.of(4, 1)));
		verificar("Combo: 1 captura em cadeia", comCombo.getCapturasEmCadeia() == 1);
		verificar("Combo: O motor não mexe nos pontos", b2.getPontos() == 5 && a2.getPontos() == 5);

		// Dois caminhos da cadeia chegam na posição 1: conta uma vez só
		Jogador a3 = new Jogador("A");
		Jogador b3 = new Jogador("B");
		Tabuleiro t3 = new Tabuleiro();
		t3.colocarCarta(1, new Carta("Alvo", 1, 1, 1, 1, "NEUTRAL"), a3);
		t3.colocarCarta(2, new Carta("Cima", 1, 1, 1, 9, "NEUTRAL"), a3);
		t3.colocarCarta(4, new Carta("Esq", 9, 1, 1, 1, "NEUTRAL"), a3);
		t3.colocarCarta(5, new Carta("Centro", 5, 1, 1, 5, "NEUTRAL"), b3);
		MotorDeCaptura doisCaminhos = new MotorDeCaptura(configuracao(true));
		verificar("Dois caminhos: 3 cartas viram", doisCaminhos.aplicar(t3, 5, b3).size() == 3);
		verificar("Dois caminhos: O motor não mexe nos pontos", b3.getPontos() == 5 && a3.getPontos() == 5);

		// Cenário testado: 3 cartas do oponente em linha viram em cadeia
		Jogador a4 = new Jogador("A");
		Jogador b4 = new Jogador("B");
		Tabuleiro t4 = new Tabuleiro();
		t4.colocarCarta(1, new Carta("Alvo 2", 1, 1, 1, 1, "NEUTRAL"), a4);
		t4.colocarCarta(2, new Carta("Elo 2", 1, 1, 1, 9, "NEUTRAL"), a4);
		t4.colocarCarta(5, new Carta("Elo 1", 5, 1, 1, 1, "NEUTRAL"), a4);
		t4.colocarCarta(6, new Carta("Atacante", 1, 1, 6, 7, "NEUTRAL"), b4);
		MotorDeCaptura tresCartas = new MotorDeCaptura(configuracao(true));
		List<Integer> resultado = tresCartas.aplicar(t4, 6, b4);
		verificar("Combo: três cartas viram em sequÊncia", resultado.equals(List.of(5, 2, 1)));
		verificar("Combo: duas capturas acontecerem em cadeia", tresCartas.getCapturasEmCadeia() == 2);
		verificar("Combo: trÊs cartas passam a ser do jogador B",
				t4.getCelula(5).getDono() == b4 && t4.getCelula(2).getDono() == b4 && t4.getCelula(1).getDono() == b4);
		verificar("Combo: não existem posições duplicadas", resultado.size() == resultado.stream().distinct().count());
	}

	private static void testarSame() throws Exception {
		Jogador jogador1 = new Jogador("Monick");
		Jogador jogador2 = new Jogador("Clarissa");
		Tabuleiro tabuleiro = new Tabuleiro();
		tabuleiro.colocarCarta(1, new Carta("A", 9, 9, 10, 9, "NEUTRAL"), jogador2); // cima, direita, baixo, esquerda
		tabuleiro.colocarCarta(5, new Carta("B", 9, 9, 9, 0, "NEUTRAL"), jogador2);
		tabuleiro.colocarCarta(4, new Carta("P", 10, 0, 1, 1, "NEUTRAL"), jogador1);

		ConfiguracoesRegras config = configuracao(false);
		config.setPlus(false); // isola o Same
		MotorDeCaptura motor = new MotorDeCaptura(config);
		List<Integer> viraram = motor.aplicar(tabuleiro, 4, jogador1);

		List<Integer> esperado = new ArrayList<Integer>();
		esperado.add(1);
		esperado.add(5);

		verificar("Motor + Same: retorna 1 e 5", viraram.equals(esperado));
		verificar("Motor + Same: 1 passa a ser do jogador 1", tabuleiro.getCelula(1).getDono() == jogador1);
		verificar("Motor + Same: 5 passa a ser do jogador 1", tabuleiro.getCelula(5).getDono() == jogador1);
		verificar("Motor + Same: O motor não mexe nos pontos", jogador1.getPontos() == 5 && jogador2.getPontos() == 5);
		verificar("Motor + Same: registra 'Same!'", motor.getRegrasAtivadas().contains("Same!"));
	}

	private static void testarPlus() throws Exception {
		Jogador jogador1 = new Jogador("J1");
		Jogador jogador2 = new Jogador("J2");
		Tabuleiro tabuleiro = new Tabuleiro();
		tabuleiro.colocarCarta(2, new Carta("A", 1, 1, 4, 1, "NEUTRAL"), jogador2);
		tabuleiro.colocarCarta(4, new Carta("B", 1, 5, 1, 1, "NEUTRAL"), jogador2);
		tabuleiro.colocarCarta(1, new Carta("C", 1, 0, 1, 1, "NEUTRAL"), jogador2);
		tabuleiro.colocarCarta(5, new Carta("P", 3, 0, 0, 2, "NEUTRAL"), jogador1);

		ConfiguracoesRegras config = configuracao(true);
		config.setSame(false); // isola o Plus
		MotorDeCaptura motor = new MotorDeCaptura(config);
		List<Integer> viraram = motor.aplicar(tabuleiro, 5, jogador1);

		List<Integer> esperado = new ArrayList<Integer>();
		esperado.add(2);
		esperado.add(4);
		esperado.add(1);

		verificar("Motor + Plus: retorna 2, 4 e a 1 por Combo", viraram.equals(esperado));
		verificar("Motor + Plus: 2 passa a ser do J1", tabuleiro.getCelula(2).getDono() == jogador1);
		verificar("Motor + Plus: 4 passa a ser do J1", tabuleiro.getCelula(4).getDono() == jogador1);
		verificar("Motor + Plus: Combo captura a 1", tabuleiro.getCelula(1).getDono() == jogador1);
		verificar("Motor + Plus: 1 captura em cadeia", motor.getCapturasEmCadeia() == 1);
		verificar("Motor + Plus: registra 'Plus!'", motor.getRegrasAtivadas().contains("Plus!"));
		verificar("Motor + Plus: O motor não mexe nos pontos", jogador1.getPontos() == 5 && jogador2.getPontos() == 5);
	}

	private static Tabuleiro montarCadeia(Jogador a, Jogador b) {
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(1, new Carta("Alvo", 1, 1, 1, 1, "NEUTRAL"), a);
		t.colocarCarta(4, new Carta("Elo", 9, 1, 1, 1, "NEUTRAL"), a);
		t.colocarCarta(5, new Carta("Atacante", 1, 1, 1, 5, "NEUTRAL"), b);
		return t;
	}

	private static void verificar(String descricao, boolean passou) {
		if (passou) {
			System.out.println("[OK] " + descricao);
		} else {
			System.out.println("[FALHOU] " + descricao);
			falhas++;
		}
	}

	private static void testarPontuacaoCombo() throws Exception {
		ConfiguracoesRegras config = new ConfiguracoesRegras();
		Jogo jogo = new Jogo("Clarissa", "Monick", new ArrayList<Carta>(), config, new Random(42));
		Jogador Clarissa = jogo.getJogador1();
		Jogador Monick = jogo.getJogador2();
		jogo.trocarJogador();
		jogo.atualizarPontuacao(3);
		verificar("Pontuação: Clarissa recebe 3 pontos", Clarissa.getPontos() == 8);
		verificar("Pontuação: Monick perde 3 pontos", Monick.getPontos() == 2);
		verificar("Pontuação: continua sendo 10", Clarissa.getPontos() + Monick.getPontos() == 10);
	}

}
