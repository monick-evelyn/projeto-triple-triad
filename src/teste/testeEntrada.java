package teste;

import terminal.Entrada;

public class testeEntrada {
	public static void main(String[] args) {
		int escolha = Entrada.lerInteiro("Escolha uma carta de 1 a 5:", 1, 5);
		System.out.println("Você escolheu: " + escolha);
		
		boolean resposta = Entrada.lerSimNao("Deseja continuar? (S/N)");
	    System.out.println("Resposta: " + resposta);
	    Entrada.aguardarEnter("Pressione Enter para continuar.");
	    System.out.println("Enter pressionado! O programa continuou.");
	    String nome = Entrada.lerTexto("Digite o nome do jogador:");
	    System.out.println("Nome registrado: " + nome);
	    
	}
}

