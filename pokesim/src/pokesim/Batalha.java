package pokesim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Batalha {
    private List<Jogador> jogadores;
    private static String[][] tabelaEspecie;
    private static String[][] tabelaAtaque;

    public static void carregarTabelas() {
        int countLinhasEspecie = 0;
        int countLinhaAtaques = 0;
        File arqTabEspecie = new File("src\\pokesim\\tabelaDeEspecies.txt");
        File arqTabAtaques = new File("src\\pokesim\\tabelaDeAtaques.txt");
        Scanner bufferCount = null;
        Scanner buffer = null;

        try {
            buffer = new Scanner(arqTabEspecie).useDelimiter("\\t|\\n|    ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        buffer.nextLine();

        try {
            bufferCount = new Scanner(arqTabEspecie).useDelimiter("\t");
        } catch (FileNotFoundException e) {
            e.printStackTrace();;
        }
        bufferCount.nextLine();

        while (bufferCount.hasNextLine()){
            countLinhasEspecie++;
            bufferCount.nextLine();
        }

        Batalha.tabelaEspecie = new String[countLinhasEspecie][9];

        for (int i = 0; i < countLinhasEspecie; i++) {
            for (int j = 0; j < 9; j++) {
                if (buffer.hasNext())
                    Batalha.tabelaEspecie[i][j] = buffer.next();
            }
        }

//        for (int i = 0; i < countLinhasEspecie; i++) {
//            for (int j = 0; j < 9; j++) {
//                System.out.printf("%15s", Batalha.tabelaEspecie[i][j].toString());
//            }
//            System.out.printf("\n");
//        }

        try {
            buffer = new Scanner(arqTabAtaques).useDelimiter("\\t|\\n|    ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        buffer.nextLine();

        try {
            bufferCount = new Scanner(arqTabAtaques).useDelimiter("\t");
        } catch (FileNotFoundException e) {
            e.printStackTrace();;
        }
        bufferCount.nextLine();

        while (bufferCount.hasNextLine()){
            countLinhaAtaques++;
            bufferCount.nextLine();
        }

        Batalha.tabelaAtaque = new String[countLinhaAtaques][8];

        for (int i = 0; i < countLinhaAtaques; i++) {
            for (int j = 0; j < 8; j++) {
                if (buffer.hasNext())
                    Batalha.tabelaAtaque[i][j] = buffer.next();
            }
        }

//        for (int i = 0; i < countLinhaAtaques; i++) {
//            for (int j = 0; j < 8; j++) {
//                System.out.printf("%20s", Batalha.tabelaAtaque[i][j]);
//            }
//            System.out.printf("\n");
//        }

        return;
    }

    public static String[][] getTabelaEspecie() {
        return tabelaEspecie;
    }

    public static String[][] getTabelaAtaque() {
        return tabelaAtaque;
    }

    public void inicializarJogadores() {
        jogadores = Arrays.asList(new Jogador[2]);
        return;
    }

    public void executarTurno() {
        return;
    }
}
