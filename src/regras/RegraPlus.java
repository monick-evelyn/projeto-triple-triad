package regras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Carta;
import modelo.Celula;
import modelo.Direcao;
import modelo.Jogador;
import modelo.Tabuleiro;

public class RegraPlus implements Regra {

	@Override
	public List<Integer> capturas(Tabuleiro tabuleiro, int posicao, Jogador jogador) throws Exception {
		List<Integer> celulasCapturadas = new ArrayList<Integer>();

		Celula celulaAtual = tabuleiro.getCelula(posicao);

		if (celulaAtual.estaVazia()) {
			throw new IllegalStateException("A célula " + posicao + " está vazia.");
		}

		Carta carta = celulaAtual.getCarta();
		Jogador dono = celulaAtual.getDono();

		List<Integer> posicoesVizinhas = new ArrayList<Integer>();
		List<Integer> somasRealizadas = new ArrayList<>();
		Map<Integer, Integer> qtdPorSoma = new HashMap<Integer, Integer>();

		for (Direcao direcao : Direcao.values()) {
			int posicaoVizinha = direcao.getPosicaoVizinha(posicao);

			if (posicaoVizinha != Direcao.FORA) {
				Celula celulaVizinha = tabuleiro.getCelula(posicaoVizinha);

				if (!celulaVizinha.estaVazia() && celulaVizinha.getDono() != dono) {
					int ladoAtual = carta.getLado(direcao);
					int ladoVizinho = celulaVizinha.getCarta().getLado(direcao.oposta());
					int soma = ladoAtual + ladoVizinho;

					posicoesVizinhas.add(posicaoVizinha);
					somasRealizadas.add(soma);
					qtdPorSoma.put(soma, qtdPorSoma.getOrDefault(soma, 0) + 1);
				}
			}
		}
		for (int i = 0; i < posicoesVizinhas.size(); i++) {
			if (qtdPorSoma.get(somasRealizadas.get(i)) >= 2) {
				celulasCapturadas.add(posicoesVizinhas.get(i));
			}

		}

		if (celulasCapturadas.size() < 2) {
			celulasCapturadas.clear();
		}
		return celulasCapturadas;
	}

	@Override
	public String getNome() {
		return "Plus!";
	}
}
