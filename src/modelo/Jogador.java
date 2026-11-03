package modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Jogador {
	private String nome;
	private List<Carta> mao;
	private int pontos;
	public static final int PONTOS_INICIAIS = 5;
	public static final int TAMANHO_MAO = 5;

	public Jogador(String nome) {
		this.nome = nome;
		this.mao = new ArrayList<Carta>();
		this.pontos = PONTOS_INICIAIS;
	}

	public String getNome() {
		return nome;
	}

	public List<Carta> getMao() {
		return Collections.unmodifiableList(new ArrayList<>(mao));
	}

	public int getPontos() {
		return pontos;
	}

	public static void distribuirCartas(List<Carta> baralho, Jogador jogador1, Jogador jogador2) {

		if (baralho == null || baralho.size() < 10) {
			throw new IllegalArgumentException("Erro: Quantidade de cartas insuficientes");
		}

		List<Carta> cartasEmbaralhadas = new ArrayList<>(baralho);
		Collections.shuffle(cartasEmbaralhadas, new Random(42));

		jogador1.mao.clear();
		jogador2.mao.clear();

		for (int i = 0; i < TAMANHO_MAO; i++) {
			jogador1.mao.add(cartasEmbaralhadas.get(i));
		}

		for (int i = TAMANHO_MAO; i < 10; i++) {
			jogador2.mao.add(cartasEmbaralhadas.get(i));
		}
	}

	public Carta jogarCarta(int indice) {
		if (indice < 0 || indice >= mao.size()) {
			System.out.println("O indice é invalido");
			return null;
		}
		Carta carta = mao.remove(indice);
		return carta;
	}

	public void ganharPonto() {
		pontos++;
	}

	public void perderPonto() {
		pontos--;
	}

	public void receberMao(List<Carta> cartas) {
		if (cartas == null) {
			throw new IllegalArgumentException("ERRO: A lista não pode ser nula");
		}
		if (cartas.size() != TAMANHO_MAO) {
			throw new IllegalArgumentException("ERRO: A mao deve ter 5 cartas");
		}
		this.mao = new ArrayList<>(cartas);
	}

	public int tamanhoMao() {
		return this.mao.size();
	}
}
