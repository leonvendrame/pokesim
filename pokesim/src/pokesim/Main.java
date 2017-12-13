package pokesim;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.Random;

import static pokesim.Batalha.carregarTabelas;

public class Main {

    public static void main(String[] args) {
        int argsInt[] = new int[args.length];

        carregarTabelas();

        for (int i = 0; i < args.length; i++) {
            argsInt[i] = Integer.parseInt(args[i]);
//            System.out.printf("%d ", argsInt[i]);
        }

        Batalha pokeBatalha = new Batalha();

        pokeBatalha.inicializarJogadores(argsInt);

        pokeBatalha.getJogador0().getTime().get(0).setStatus(Status.BURN);
        pokeBatalha.getJogador1().getTime().get(0).setStatus(Status.POISON);

        while (pokeBatalha.continuarJogo()) {
            pokeBatalha.executarTurno();
        }

        if (pokeBatalha.getVencedorId() == -1) {
            System.out.println(ANSI_BLUE + "O jogo terminou empatado!" + ANSI_RESET);
        } else {
            System.out.printf(ANSI_BLUE + "\t\tO vencedor foi o Jogador %d!\n\n" + ANSI_RESET, pokeBatalha.getVencedorId());
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
}
