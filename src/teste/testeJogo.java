package teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jogo.ConfiguracoesRegras;
import jogo.Jogo;
import modelo.Carta;

public class testeJogo {
	public static void main(String[] args) throws Exception {
		List<Carta> baralho = new ArrayList<>();

		baralho.add(new Carta("Dragão", 8, 7, 5, 9));
		baralho.add(new Carta("Cavaleiro", 6, 8, 4, 7));
		baralho.add(new Carta("Mago", 5, 9, 6, 8));
		baralho.add(new Carta("Guerreiro", 7, 6, 8, 5));
		baralho.add(new Carta("Fênix", 9, 5, 7, 6));
		baralho.add(new Carta("Lobo", 4, 7, 9, 5));
		baralho.add(new Carta("Golem", 8, 4, 6, 7));
		baralho.add(new Carta("Serpente", 6, 5, 8, 9));
		baralho.add(new Carta("Anjo", 9, 8, 5, 4));
		baralho.add(new Carta("Demônio", 7, 9, 6, 5));

		ConfiguracoesRegras config = new ConfiguracoesRegras();
		Random random = new Random(42);

		Jogo jogo = new Jogo("Jogador 1", "Jogador 2", baralho, config, random);
		jogo.iniciar();
	}

}
