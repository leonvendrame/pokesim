package pokesim;

import javax.management.timer.Timer;
import java.io.*;
import java.util.Scanner;

import static pokesim.Batalha.carregarTabelas;

public class Main {

    public static void main(String[] args) {

        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.printf("%s\n", args[i]);
            }
        } else {
            carregarTabelas();

            Pokemon meu = new Pokemon(6, 20);

            System.out.println(meu.getEspecie().getNome());
            System.out.printf("MAX HP: \t%.2f\n", meu.valorAtributo(Atributo.HPMAX));
            System.out.printf("HP ATUAL: \t%.2f\n", meu.valorAtributo(Atributo.HPATUAL));
            System.out.printf("ATJ: \t\t%.2f\n", meu.valorAtributo(Atributo.ATK));
            System.out.printf("DEF: \t\t%.2f\n", meu.valorAtributo(Atributo.DEF));
            System.out.printf("SPE: \t\t%.2f\n", meu.valorAtributo(Atributo.SPE));
            System.out.printf("SPD: \t\t%.2f\n", meu.valorAtributo(Atributo.SPD));

        }

    }
}
