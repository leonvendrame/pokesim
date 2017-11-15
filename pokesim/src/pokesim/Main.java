package pokesim;

import javax.imageio.ImageIO;
import javax.management.timer.Timer;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Scanner;

import static pokesim.Batalha.carregarTabelas;

public class Main {

    public static void main(String[] args) {
        carregarTabelas();

        if (args.length > 6 && args.length < 14) {
            Jogador jogador0;
            Jogador jogador1;

            if (args[0] == "0") {
                jogador0 = new Maquina();
            } else {
                jogador0 = new Humano();
            }

            int j = 2;
            while (j < args.length - 1) {
                for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                    Pokemon pokemon = new Pokemon(Integer.parseInt(args[j]), Integer.parseInt(args[j + 1]));
                    jogador0.adicionaPokemonTime(pokemon);
                    j += 6;
                }
            }

            if (jogador0 != null){
                List<Pokemon> poketime = jogador0.getTime();
                for (Pokemon poke : poketime) {
                    poke.getInfoPokemon();
                }
            }

        } else {
//            Pokemon meu = new Pokemon(6, 1);
//
//            Pokemon meu2 = new Pokemon(151, 2);
//
//            meu.getInfoPokemon();
//
//            meu2.getInfoPokemon();
        }



    }
}
