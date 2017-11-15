package pokesim;

import javax.management.timer.Timer;
import java.io.*;
import java.util.Scanner;

import static pokesim.Batalha.carregarTabelas;

public class Main {

    public static void main(String[] args) {
        carregarTabelas();

        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.printf("%s\n", args[i]);
            }
        } else {


            Pokemon meu = new Pokemon(6, 1);

            meu.infoPokemon();
        }

    }
}
