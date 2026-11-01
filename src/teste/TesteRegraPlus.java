package teste;

import java.util.ArrayList;
import java.util.List;

import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;
import regras.Regra;
import regras.RegraPlus;

public class TesteRegraPlus {
	private static int falhas = 0;

	public static void main(String[] args) throws Exception {

		testarRegraSame();

		System.out.println();
		if (falhas == 0) {
			System.out.println("Todos os testes passaram.");
		} else {
			System.out.println(falhas + " teste(s) falharam.");
		}
	}

	private static void testarRegraSame() throws Exception {
		Regra regraPlus = new RegraPlus();

		Jogador jogador1 = new Jogador("Monick");
		Jogador jogador2 = new Jogador("Clarissa");

		// 1. Normal: 3 + 4 = 7 (cima) e 2 + 5 = 7 (esquerda) capturam 2 e 4
		Tabuleiro tabuleiro1 = new Tabuleiro();
		Carta carta = new Carta("Bisão", 1, 1, 4, 1);
		Carta carta1 = new Carta("Fenix", 1, 5, 1, 1);
		Carta carta2 = new Carta("Slime", 3, 0, 0, 2);
		
		tabuleiro1.colocarCarta(2, carta, jogador2); // cima, direita, baixo, esquerda
		tabuleiro1.colocarCarta(4, carta1, jogador2);
		tabuleiro1.colocarCarta(5, carta2, jogador1); // cima, direita, baixo, esquerda

		List<Integer> esperado = new ArrayList<Integer>();
		esperado.add(2);
		esperado.add(4);
		System.out.println(esperado);
		System.out.println(regraPlus.capturas(tabuleiro1, 5, jogador1));
		
		verificar("Plus: 3 + 4 = 7 e 2 + 5 = 7 captura 2 e 4", regraPlus.capturas(tabuleiro1, 5, jogador1).equals(esperado));

	}

	private static void verificar(String descricao, boolean passou) {
		if (passou) {
			System.out.println("[OK] " + descricao);
		} else {
			System.out.println("[FALHOU] " + descricao);
			falhas++;
		}
	}
}
