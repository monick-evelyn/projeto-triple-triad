package teste;

import java.util.ArrayList;
import java.util.List;

import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;
import regras.Regra;
import regras.RegraBasica;
import regras.RegraSame;

public class TesteRegraSame {
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
		Regra regraSame = new RegraSame();

		Jogador jogador1 = new Jogador("Monick");
		Jogador jogador2 = new Jogador("Clarissa");

		// 1. Normal: Se os valores nas bordas de duas ou mais cartas adjacentes forem
		// iguais, a carta do oponente é capturada.
		Tabuleiro tabuleiro1 = new Tabuleiro();

		Carta carta1 = new Carta("Bisão", 9, 9, 10, 9); // cima, direita, baixo, esquerda
		Carta carta2 = new Carta("Fenix", 10, 0, 1, 1); // cima, direita, baixo, esquerda
		Carta carta3 = new Carta("Slime", 9, 9, 9, 0); // cima, direita, baixo, esquerda

		tabuleiro1.colocarCarta(1, carta1, jogador2);
		tabuleiro1.colocarCarta(4, carta2, jogador1);
		tabuleiro1.colocarCarta(5, carta3, jogador2);

		// System.out.println(regraSame.capturas(tabuleiro1, 4, jogador1));
		List<Integer> esperado = new ArrayList<Integer>();
		esperado.add(1);
		esperado.add(5);

		verificar("Same: 10 = 10 e 0 = 0 captura a posição 1 e 5",
				regraSame.capturas(tabuleiro1, 4, jogador1).equals(esperado));
		verificar("Same: Troca o dono da 1", tabuleiro1.getCelula(1).getDono() == jogador1);
		verificar("Same: Troca o dono da 5", tabuleiro1.getCelula(5).getDono() == jogador1);
		verificar("Same: Muda os pontos", jogador1.getPontos() == 7 && jogador2.getPontos() == 3);

		
		System.out.println();
		// 2. Uma igualdade sozinha não captura
		Tabuleiro tabuleiro2 = new Tabuleiro();

		Carta carta4 = new Carta("Mago", 1, 2, 3, 4); // cima, direita, baixo, esquerda
		Carta carta5 = new Carta("Arqueiro", 3, 0, 0, 0); // cima, direita, baixo, esquerda
		Carta carta6 = new Carta("Guerreiro", 10, 10, 10, 10); // cima, direita, baixo, esquerda
		
		tabuleiro2.colocarCarta(1, carta4, jogador2);
		tabuleiro2.colocarCarta(4, carta5, jogador1);
		tabuleiro2.colocarCarta(5, carta6, jogador2);

		// System.out.println(regraSame.capturas(tabuleiro1, 4, jogador1));
		esperado.clear();
		
		verificar("Same: 3 = 3, porém não captura",
				regraSame.capturas(tabuleiro2, 4, jogador1).equals(esperado));
		verificar("Same: Não troca o dono da 1", tabuleiro2.getCelula(1).getDono() == jogador2);
		verificar("Same: Não muda os pontos", jogador1.getPontos() == 7 && jogador2.getPontos() == 3);
	
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
