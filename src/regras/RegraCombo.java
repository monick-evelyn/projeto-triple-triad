package regras;

import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;

import modelo.Jogador;
import modelo.Tabuleiro;
import modelo.Celula;

public class RegraCombo {
	private final RegraBasica basica = new RegraBasica();

	public String getNome() {
		return "Combo";
	}

	public List<Integer> cascata(Tabuleiro t, List<Integer> capturadas, Jogador jogador) throws Exception {
		ArrayDeque<Integer> fila = new ArrayDeque<>(capturadas);
		List<Integer> novasCapturas = new ArrayList<>();
		while (!fila.isEmpty()) {
			int posicao = fila.removeFirst();
			List<Integer> vizinhas = basica.capturas(t, posicao, jogador);
			for (int vizinha : vizinhas) {
				Celula celula = t.getCelula(vizinha);
				if (celula.getDono() != jogador) {
					celula.trocarDono(jogador);
					novasCapturas.add(vizinha);
					fila.addLast(vizinha);
				}
			}
		}
		return novasCapturas;
	}

}
