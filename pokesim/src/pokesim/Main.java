package pokesim;

import com.sun.deploy.util.SystemUtils;
import sun.rmi.server.InactiveGroupException;

import java.io.IOException;
import java.util.List;

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

//        for (Pokemon pokemon : pokeBatalha.getJogador0().getTime()){
//            pokemon.getInfoPokemon();
//        }
//
//        for (Pokemon pokemon : pokeBatalha.getJogador1().getTime()){
//            pokemon.getInfoPokemon();
//        }

    }
}
