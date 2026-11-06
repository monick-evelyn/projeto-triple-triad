package principal;

import java.util.List;
import java.util.Random;

import jogo.ConfiguracoesRegras;
import jogo.Jogo;
import modelo.Carta;
import terminal.Entrada;
import terminal.LeitorCSV;
import terminal.Tela;

public class Main {
	public static void main(String[] args) throws Exception {
		int opcao;
		do {
			Tela.mostrarMenu();
			opcao = Entrada.lerInteiro("Escolha uma opção: ", 1, 3);
			switch (opcao) {
			case 1:
				iniciarJogo();
				opcao = 3; 
				encerrar();
				break;
			case 2:
				ConfiguracoesRegras regras = new ConfiguracoesRegras();
				Tela.mostrarRegras(regras);
				Entrada.aguardarEnter("Pressione Enter para voltar ao menu...");
				break;
			case 3:
				System.out.println("Saindo...Obrigado por jogar, volte sempre!");
				break;
			default:
				System.out.println("Opção inválida, escolha 1, 2 ou 3.");
				break;
			}
		} while (opcao != 3);
	}

	private static void iniciarJogo() throws Exception {
		System.out.println("======================================== NOVO JOGO ========================================");
		String nome1 = Entrada.lerTexto("Digite o nome do primeiro jogador: ");
		String nome2 = Entrada.lerTexto("Digite o nome do segundo jogador: ");
		List<Carta> baralho = LeitorCSV.ler("cardsApresentacao.csv");
		ConfiguracoesRegras config = new ConfiguracoesRegras();
		Random random = new Random();
		
		boolean jogarDeNovo = true;
		while (jogarDeNovo) {
			Jogo jogo = new Jogo(nome1, nome2, baralho, config, random); // partida nova: tabuleiro vazio e 5 x 5
			jogo.iniciar();
			jogarDeNovo = Entrada.lerSimNao("Deseja jogar novamente (S/N)? ");
		}
	}
	
	private static void encerrar() {
		System.out.println("Saindo do jogo...Volte sempre!");
	}
}
