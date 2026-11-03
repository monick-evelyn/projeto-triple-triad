package modelo;

public class Carta {
	private String nome;
	private String tipo;
	private int lado1; // cima
	private int lado2; // direita
	private int lado3; // baixo
	private int lado4; // esquerda

	public Carta(String nome, int lado1, int lado2, int lado3, int lado4, String tipo) {

		this.nome = nome;
		this.lado1 = validarValor(lado1);
		this.lado2 = validarValor(lado2);
		this.lado3 = validarValor(lado3);
		this.lado4 = validarValor(lado4);
		this.tipo = tipo;
	}

	public Carta(String nome, int lado1, int lado2, int lado3, int lado4) {

		this.nome = nome;
		this.lado1 = validarValor(lado1);
		this.lado2 = validarValor(lado2);
		this.lado3 = validarValor(lado3);
		this.lado4 = validarValor(lado4);
		this.tipo = "";
	}

	public int getLado(Direcao direcao) {
		switch (direcao) {
		case CIMA:
			return lado1;

		case DIREITA:
			return lado2;
		case BAIXO:
			return lado3;
		case ESQUERDA:
			return lado4;
		default:
			throw new IllegalArgumentException("ERRO: Não existe um lado para essa direção");
		}
	}

	private int validarValor(int valor) {
		if (valor < 0 || valor > 10) {
			throw new IllegalArgumentException("ERRO: O lado deve ser um número de 0 a 10.");
		}
		return valor;
	}

	public String getNome() {
		return nome;
	}

	public String getTipo() {
		return tipo;
	}

	@Override
	public String toString() {
		return "Carta: " + nome + " | Tipo: " + tipo + " | Cima: " + lado1 + " | Direita: " + lado2 + " | Baixo: "
				+ lado3 + " | Esquerda: " + lado4;
	}

}
