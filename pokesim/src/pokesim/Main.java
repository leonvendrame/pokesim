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

    }
}
