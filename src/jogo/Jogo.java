package jogo;

import java.util.List;
import java.util.Random;

import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;
import regras.MotorDeCaptura;
import terminal.Entrada;
import terminal.Tela;

public class Jogo {
	private Tabuleiro tabuleiro;
	private Jogador jogador1;
	private Jogador jogador2;
	private Jogador jogadorAtual;
	private ConfiguracoesRegras config;
	private MotorDeCaptura motor;
	private List<Carta> baralho;
	private Random random;

	public Jogo(String nome1, String nome2, List<Carta> baralho, ConfiguracoesRegras config, Random random) {
		this.tabuleiro = new Tabuleiro();
		this.jogador1 = new Jogador(nome1);
		this.jogador2 = new Jogador(nome2);
		this.config = config;
		this.random = random;
		this.baralho = baralho;
		this.motor = new MotorDeCaptura(config);
	}

	public Jogador getJogador1() {
		return jogador1;
	}

	public Jogador getJogador2() {
		return jogador2;
	}

	public Jogador getJogadorAtual() {
		return jogadorAtual;
	}

	public Jogador getVencedor() {
		int cartasJ1 = tabuleiro.contarCelulas(jogador1);
		int cartasJ2 = tabuleiro.contarCelulas(jogador2);
		if (cartasJ1 > cartasJ2) {
			return jogador1;
		} else if (cartasJ1 < cartasJ2) {
			return jogador2;
		}
		return null;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	private void sortearPrimeiro() {
		int sorteado = random.nextInt(2);
		if (sorteado == 0) {
			jogadorAtual = jogador1;
		} else {
			jogadorAtual = jogador2;
		}
		System.out.println("O jogador que começa é: " + jogadorAtual.getNome());
	}

	public void distribuirCartas() {
		Jogador.distribuirCartas(baralho, jogador1, jogador2);
	}

	public void trocarJogador() {
		if (jogadorAtual == jogador1) {
			jogadorAtual = jogador2;
		} else {
			jogadorAtual = jogador1;
		}
	}

	private Jogador oponente() {
		if (jogadorAtual == jogador1) {
			return jogador2;
		} else {
			return jogador1;
		}
	}

	private Carta escolherCarta() {
		Tela.desenharMaoOponente(oponente(), config.isThreeOpen());
		Tela.desenharMao(jogadorAtual);
		int indice = Entrada.lerInteiro("Qual carta deseja escolher", 1, jogadorAtual.getMao().size());
		return jogadorAtual.jogarCarta(indice - 1);
	}

	private int escolherCelula() {
		int escolhaCelula = Entrada.lerInteiro("Qual célula deseja escolher", 1, 9);
		while (!tabuleiro.getCelula(escolhaCelula).estaVazia()) {
			System.out.println("A célula está ocupada, escolha outra");
			escolhaCelula = Entrada.lerInteiro("Qual célula deseja escolher", 1, 9);
		}
		return escolhaCelula;
	}

	public void atualizarPontuacao(int capturas) throws IllegalAccessException {
		for (int i = 0; i < capturas; i++) {
			jogadorAtual.ganharPonto();
			oponente().perderPonto();
		}
		if (jogador1.getPontos() + jogador2.getPontos() != 2 * Jogador.PONTOS_INICIAIS) {
			throw new IllegalAccessException("Pontuação inválida: a soma dos pontos deve ser 10.");
		}
	}

	private void jogarTurno() throws Exception {
		Tela.desenharTabuleiro(tabuleiro, jogador1, jogador2);

		Carta carta = escolherCarta();
		int posicao = escolherCelula();

		tabuleiro.colocarCarta(posicao, carta, jogadorAtual);

		List<Integer> capturadas = motor.aplicar(tabuleiro, posicao, jogadorAtual);
		atualizarPontuacao(capturadas.size());
		if (!capturadas.isEmpty()) {
			Tela.mostrarJogada(jogadorAtual.getNome(), capturadas.size(), motor.getRegrasAtivadas(),
					motor.getCapturasEmCadeia());
		}
		Tela.desenharPlacar(jogador1, jogador2);
	}

	public void iniciar() throws Exception {
		distribuirCartas();
		if (config.isThreeOpen()) {
			jogador1.abrirCartas(3, random);
			jogador2.abrirCartas(3, random);
		}
		sortearPrimeiro();
		while (!tabuleiro.estaCheio()) {
			jogarTurno();
			if (!tabuleiro.estaCheio()) {
				trocarJogador();
				Tela.telaDeTroca(jogadorAtual);
			}
		}
		finalizar();
	}

	public void finalizar() {
		Tela.desenharTabuleiro(tabuleiro, jogador1, jogador2);
		Tela.desenharPlacar(jogador1, jogador2);
		Jogador vencedor = getVencedor();
		if (vencedor == null) {
			System.out.println("Empate!");
		} else {
			System.out.println("Parabénss " + vencedor.getNome() + " você venceu!");
		}

	}

}
