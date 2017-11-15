package pokesim;

import java.util.List;

import static pokesim.Batalha.carregarTabelas;

public class Main {

    public static int checarArgumentos(String[] args) {
        int indiceQtdePokemon = 0;
        if (args.length > 1 && args.length >= ((Integer.parseInt(args[1]) * 6) + 2)) {
            indiceQtdePokemon = (((Integer.parseInt(args[1])) * 6) + 2);
            if (args.length == indiceQtdePokemon) {
                return 1;
            } else {
                if (args.length <= ( indiceQtdePokemon + ((Integer.parseInt(args[indiceQtdePokemon]) * 6) + 2)))
                    return 0;
                if (args.length == ( indiceQtdePokemon + ((Integer.parseInt(args[indiceQtdePokemon + 2]) * 6) + 2))) {
                    return 2;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        carregarTabelas();

        Jogador jogador0 = null;
        Jogador jogador1 = null;



        if (checarArgumentos(args) == 1) {
            if (args[0] == "0") {
                jogador0 = new Maquina();
            } else {
                jogador0 = new Humano();
            }

            int j = 2;
            while (j < args.length) {
                for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                    Pokemon pokemon = new Pokemon(Integer.parseInt(args[j]), Integer.parseInt(args[j + 1]));
                    jogador0.adicionaPokemonTime(pokemon);
                    j += 6;
                }
            }
        } else if (checarArgumentos(args) == 2) {
            System.out.println("Dois Jogadores Exatos");
//            if (args[0] == "0") {
//                jogador0 = new Maquina();
//            } else {
//                jogador0 = new Humano();
//            }
//
//            int j = 2;
//            while (j < args.length) {
//                for (int i = 0; i < Integer.parseInt(args[1]); i++) {
//                    Pokemon pokemon = new Pokemon(Integer.parseInt(args[j]), Integer.parseInt(args[j + 1]));
//                    jogador0.adicionaPokemonTime(pokemon);
//                    j += 6;
//                }
//            }
        } else {
//            Pokemon meu = new Pokemon(6, 1);
//
//            Pokemon meu2 = new Pokemon(151, 2);
//
//            meu.getInfoPokemon();
//
//            meu2.getInfoPokemon();
        }

        if (jogador0 != null){
            List<Pokemon> poketime = jogador0.getTime();
            for (Pokemon poke : poketime) {
                poke.getInfoPokemon();
            }
        }

    }
}
