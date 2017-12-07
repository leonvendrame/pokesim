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

//        pokeBatalha.getJogador1().getTime().get(0).setStatus(Status.FAINTED);

//        pokeBatalha.getJogador1().trocarPokemon();

//        pokeBatalha.getJogador0().getTime().get(0).getAtaques().get(0).efeito(pokeBatalha.getJogador0().getTime().get(0), pokeBatalha.getJogador1().getTime().get(0));

        pokeBatalha.executarTurno();

        pokeBatalha.getResumoJogador(pokeBatalha.getJogador1());
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
}
