package pokesim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Batalha {
    private List<Jogador> jogadores;
    private static String[][] tabelaEspecie;

    public static void carregarTabelas() {
        int countLinhas = 0;
        File arqTabEspecie = new File("src\\pokesim\\Table3.txt");
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
            countLinhas++;
            bufferCount.nextLine();
        }

        Batalha.tabelaEspecie = new String[countLinhas][9];

        for (int i = 0; i < countLinhas; i++) {
            for (int j = 0; j < 9; j++) {
                if (buffer.hasNext())
                    Batalha.tabelaEspecie[i][j] = buffer.next();
            }
        }

        return;
    }

    public static String[][] getTabelaPokemon() {
        return tabelaEspecie;
    }

    public void inicializarJogadores() {
        jogadores = Arrays.asList(new Jogador[2]);
        return;
    }

    public void executarTurno() {
        return;
    }
}
