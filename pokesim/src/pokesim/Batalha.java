package pokesim;

import jdk.nashorn.internal.scripts.JO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Batalha {
    private Jogador jogador0 = null;
    private Jogador jogador1 = null;
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
            bufferCount = new Scanner(arqTabEspecie);
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
            bufferCount = new Scanner(arqTabAtaques);
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

    public int checarArgumentos(int[] args) {
        /* Verifica a quantidade de argumentos;
           Caso o usuário entre com argumentos suficientes para 1 time, ele inicializará este time;
           Caso o usuário entre com argumentos suficientes para 2 times, ele inicializará estes 2 times;
           Caso o usuário entre com mais argumentos do que o necessário e os mesmos são suficientes para 1 time, os
           argumentos a mais serão desconsiderados, ainda inicializando o time;
           Caso o usuário entre com mais argumentos do que o necessário e os mesmos são suficientes para 2 times, os
           argumentos a mais serão desconsiderados, ainda inicializando os times;
         */

        if (args.length < 2 || args[1] == 0) {
            return 0;
        }

        int qtdeArgsJog = ((args[1] * 6) + 2);

        if (args.length >= qtdeArgsJog) {
            if (args.length == qtdeArgsJog || args.length == (qtdeArgsJog + 1)) {
                return 1;
            } else {
                if (args[qtdeArgsJog + 1] == 0 || args.length < qtdeArgsJog + (((args[qtdeArgsJog + 1]) * 6) + 2)) {
                    return 1;
                } else if (args.length >= qtdeArgsJog + (((args[qtdeArgsJog + 1]) * 6) + 2)) {
                    return 2;
                }
            }
        }
        return 0;
    }

    public boolean validaCamposArg(int[] args) {
        /*  Verifica se os campos são válidos, seja algum deles igual a 0 então os argumentos não são válidos
            Só não se aplica ao jogador que pode ser maquina ou humano;
         */
        boolean valido = true;
        int segJogador = ((args[1] * 6) + 2), i = 2;

        if (args[1] == 0) {
            valido = false;
        }

         while (valido && i < segJogador) {
            if (args[i] == 0 || args[i + 1] == 0){
                valido = false;
            }
            i += 6;
        }

        if (args.length > segJogador + 1 && args.length < (segJogador + ((args[segJogador + 1] * 6) + 2))) {
            if (args[segJogador + 1] == 0) {
                    valido = false;
            }

            i += 2;

            while (valido && i < args.length) {
                if (args[i] == 0 || args[i + 1] == 0) {
                    valido = false;
                }
                i += 6;
            }
        }

        return valido;
    }

    public boolean verificaTamanhoArg(int[] args) {
        if (args.length < 8) {
            return false;
        }
        if (args.length < ((args[1] * 6) + 2)){
            return false;
        }
        return true;
    }

    public void inicializarJogadores(int[] args) {
        int nJogadores = 0;

        if (verificaTamanhoArg(args)){
            if (validaCamposArg(args)) {
                nJogadores = checarArgumentos(args);
            } else {
                System.out.printf("\nSeus argumentos são inválidos\n");
                System.out.printf("O numero de pokemons deve corresponder às suas inicializações.\n");
                System.out.printf("O níveis e IDs de pokemons e ataques não podem ser iguais a 0.\n");
            }
        }

        if (nJogadores == 2) {
            int argsSegundoJogador = ((args[1] * 6) + 2);

            //JOGADOR 1

            if (args[0] == 0) {
                this.jogador0 = new Maquina();
            } else {
                this.jogador0 = new Humano();
            }

            int j = 2;

            for (int i = 0; i < args[1]; i++) {
                Pokemon addPokemon = new Pokemon(args[j], args[j + 1]);
                this.jogador0.adicionaPokemonTime(addPokemon);
                j += 6;
            }

            this.getResumoJogador(jogador0);

            // JOGADOR 2

            if (args[argsSegundoJogador] == 0) {
                this.jogador1 = new Maquina();
            } else {
                this.jogador1 = new Humano();
            }

            j = argsSegundoJogador + 2;

            for (int i = 0; i < args[argsSegundoJogador + 1]; i++) {
                Pokemon addPokemon = new Pokemon(args[j], args[j + 1]);
                this.jogador1.adicionaPokemonTime(addPokemon);
                j += 6;
            }

            this.getResumoJogador(jogador1);

            return;
        }

        if (nJogadores == 1) {
            if (args[0] == 0) {
                this.jogador0 = new Maquina();
            } else {
                this.jogador0 = new Humano();
            }

            int j = 2;
            for (int i = 0; i < args[1]; i++) {
                Pokemon addPokemon = new Pokemon(args[j], args[j+1]);
                this.jogador0.adicionaPokemonTime(addPokemon);
                j += 6;
            }

            this.getResumoJogador(jogador0);

            return;
        }

        if (nJogadores == 0) {
            System.out.printf("Inicializando Jogadores Manualmente\n\n");

            Scanner opcao = new Scanner(System.in);
            int entrada, auxId, auxLvl;

            Jogador jogadorIt;

            for (int i = 0; i < 2; i++) {
                System.out.printf("\t\t\t Jogador %d\n", i + 1);
                System.out.printf("%25s\n", "Jogador do Tipo");
                System.out.printf("0 - Máquina\n1 - Humano\nEntre com sua opção: ");
                if (opcao.nextInt() == 0) {
                    jogadorIt = new Maquina();
                    System.out.printf("Jogador %d: %s\n", i + 1, jogadorIt.getClass().getTypeName() == "pokesim.Humano" ? "Humano" : "Máquina");
                } else {
                    jogadorIt = new Humano();
                    System.out.printf("Jogador %d: %s\n", i + 1, jogadorIt.getClass().getTypeName() == "pokesim.Humano" ? "Humano" : "Máquina");
                }
                System.out.printf("Quantos pokemons você terá em seu time: ");
                entrada = opcao.nextInt();
                while (entrada > 6 || entrada < 1) {
                    System.out.println("Você não pode ter mais que 6 pokemons e deve ter pelo menos 1 pokemon");
                    System.out.printf("Insira novamente a quantidade de pokemons em seu time: ");
                    entrada = opcao.nextInt();
                }
                System.out.println("Se você deseja ver a lista com todos os pokemons e status dísponíveis?");
                System.out.printf("0 - Não\n1 - Sim\nEscolha sua opção: ");
                if (opcao.nextInt() == 1) {
                    System.out.println("teste");
                    System.out.printf("%15s%15s%15s%15s%15s%15s%15s%15s%15s\n", "ID", "Espécie", "Tipo 1", "Tipo2", "BaseHP", "BaseATK", "BaseDEF", "BaseSPE", "BaseSPD");
                    for (int x = 0; x < tabelaEspecie.length; x++){
                        for (int y = 0; y < 9; y++){
                            System.out.printf("%15s", Batalha.tabelaEspecie[x][y]);
                        }
                        System.out.printf("\n");
                    }
                }
                System.out.println("Entre com o ID e o level de cada pokemon separando por espaço. (Ex: 1 10 = ID 1 Level 10)");
                for (int j = 0; j < entrada; j++) {
                    System.out.printf("Pokemon %d: ", j + 1);
                    auxId = opcao.nextInt();
                    auxLvl = opcao.nextInt();
                    Pokemon addPokemon = new Pokemon(auxId, auxLvl);
                    jogadorIt.adicionaPokemonTime(addPokemon);
                }
                if (i == 0){
                    this.jogador0 = jogadorIt;
                } else {
                    this.jogador1 = jogadorIt;
                }
                System.out.printf("\n");
            }
            this.getResumoJogador(jogador0);
            this.getResumoJogador(jogador1);
        }
        return;
    }

    public Jogador getJogador0() {
        return jogador0;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public void getResumoJogador(Jogador jogador) {
        System.out.printf("\t\tTime - Jogador %d - %s\n", this.jogador0 == jogador ? 1 : 2 , jogador.getClass().getTypeName() == "pokesim.Humano" ? "Humano" : "Máquina" );
        for (Pokemon pokemon : jogador.getTime()) {
            System.out.printf("%-14s LVL %4s\n", pokemon.getEspecie().getNome(), pokemon.getLevel());
        }
        System.out.printf("\n");
    }

    public void executarTurno() {
        return;
    }
}
