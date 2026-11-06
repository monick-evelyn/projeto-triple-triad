package teste;

public class TodosOsTestes {
    public static void main(String[] args) throws Exception {
        String[] semArgs = new String[0];
        System.out.println("== Tabuleiro");      TesteTabuleiro.main(semArgs);
        System.out.println("== Jogador");        TesteJogador.main(semArgs);
        System.out.println("== Captura");        TesteCaptura.main(semArgs);
        System.out.println("== Motor");          TesteMotorDeCaptura.main(semArgs);
        System.out.println("== Same");           TesteRegraSame.main(semArgs);
        System.out.println("== Plus");           TesteRegraPlus.main(semArgs);
        System.out.println("== Pontuação");      TestePontuacao.main(semArgs);
        System.out.println("== Fim de jogo");    TesteFimDeJogo.main(semArgs);
    }
}