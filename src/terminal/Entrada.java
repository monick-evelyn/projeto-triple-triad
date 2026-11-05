package terminal;

import java.util.Scanner;

public class Entrada {
	private static final Scanner leitor = new Scanner(System.in);

	public static int lerInteiro(String pergunta, int min, int max) {
		while (true) {
			System.out.print(pergunta);
			String resposta = leitor.nextLine();

			try {
				int numero = Integer.parseInt(resposta);
				if (numero >= min && numero <= max) {
					return numero;
				}
				System.out.print("Número inválido, digite novamente! ");
			} catch (NumberFormatException e) {
				System.out.print("Entrada inválida! Digite um número inteiro. ");
			}
		}
	}

	public static boolean lerSimNao(String pergunta) {
		while (true) {
			System.out.print(pergunta);
			String resposta = leitor.nextLine().trim().toLowerCase();

			if (resposta.equalsIgnoreCase("Sim") || resposta.equalsIgnoreCase("S")) {
				return true;
			} else if (resposta.equalsIgnoreCase("Não") || resposta.equalsIgnoreCase("N")
					|| resposta.equalsIgnoreCase("Nao")) {
				return false;
			} else {
				System.out.print("Resposta inválida, digite novamente: ");
			}
		}
	}

	public static String lerTexto(String pergunta) {
		while (true) {
			System.out.print(pergunta);
			String resposta = leitor.nextLine().trim();

			if (!resposta.isEmpty()) {
				return resposta;
			}
			System.out.print("A resposta está inválida. Tente novamente: ");
		}

	}

	public static void aguardarEnter(String pergunta) {
		System.out.print(pergunta);
		leitor.nextLine();
	}
}
