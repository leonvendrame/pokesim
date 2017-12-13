package pokesim;

import jdk.nashorn.internal.scripts.JO;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Batalha {
    private Jogador jogador0 = null;
    private Jogador jogador1 = null;
    private static String[][] tabelaEspecie;
    private static String[][] tabelaAtaque;
    private static String[][] tabelaDano;
    private static int[][] tabelaAE;
    private int vencedorId;

    Batalha() {
        setVencedorId(-1);
    }

    public static void carregarTabelas() {
        int countLinhasEspecie = 0;
        int countLinhaAtaques = 0;
        File arqTabEspecie;
        File arqTabAtaques;
        Scanner bufferCount = null;
        Scanner buffer = null;

        if (System.getProperty("os.name").compareToIgnoreCase("linux") == 0) {
            arqTabEspecie = new File("src/pokesim/tabelaDeEspecies.txt");
            arqTabAtaques = new File("src/pokesim/tabelaDeAtaques.txt");
        } else {
            arqTabEspecie = new File("src\\pokesim\\tabelaDeEspecies.txt");
            arqTabAtaques = new File("src\\pokesim\\tabelaDeAtaques.txt");
        }

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

        carregaTabelaDano();
        carregaTabelaAE();

        return;
    }

    public static String[][] getTabelaEspecie() {
        return tabelaEspecie;
    }

    public static String[][] getTabelaAtaque() {
        return tabelaAtaque;
    }

    public static String[][] getTabelaDano() {
        return tabelaDano;
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
            Só não se aplica ao jogador que pode ser maquina ou humano, ou caso de não ter algum ataque;
         */
        boolean valido = true;
        int segJogador = ((args[1] * 6) + 2), i = 2;

        if (args[1] == 0) {
            valido = false;
        }

         while (valido && i < segJogador) {
            if (args[i] == 0 || args[i] > tabelaEspecie.length || args[i + 1] == 0){
                valido = false;
            }
            if (args[i + 2] == 0 && args[i + 3] == 0 && args[i + 4] == 0 & args[i + 5] == 0) {
                valido = false;
            }
            i += 6;
        }

        if (args.length > segJogador + 1 && args.length <= (segJogador + ((args[segJogador + 1] * 6) + 2))) {
            if (args[segJogador + 1] == 0) {
                    valido = false;
            }

            segJogador += 2;

            while (valido && segJogador < args.length) {
                if (args[segJogador] == 0 || args[segJogador] > tabelaEspecie.length || args[segJogador + 1] == 0) {
                    valido = false;
                }
                if (args[segJogador + 2] == 0 && args[segJogador + 3] == 0 && args[segJogador + 4] == 0 & args[segJogador + 5] == 0) {
                    valido = false;
                }
                segJogador += 6;
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
                System.out.printf("Seus argumentos são inválidos\n");
                System.out.printf("O numero de pokemons deve corresponder às suas inicializações.\n");
                System.out.printf("O níveis e IDs de pokemons e ataques não podem ser iguais a 0.\n");
                System.out.printf("Os IDs dos pokemons devem estar dísponíveis na tabela.\n");
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
                addPokemon.novoAtaque(args[j + 2]);
                addPokemon.novoAtaque(args[j + 3]);
                addPokemon.novoAtaque(args[j + 4]);
                addPokemon.novoAtaque(args[j + 5]);
                this.jogador0.adicionarPokemon(addPokemon);
                j += 6;
            }

            // JOGADOR 2

            if (args[argsSegundoJogador] == 0) {
                this.jogador1 = new Maquina();
            } else {
                this.jogador1 = new Humano();
            }

            j = argsSegundoJogador + 2;

            for (int i = 0; i < args[argsSegundoJogador + 1]; i++) {
                Pokemon addPokemon = new Pokemon(args[j], args[j + 1]);
                addPokemon.novoAtaque(args[j + 2]);
                addPokemon.novoAtaque(args[j + 3]);
                addPokemon.novoAtaque(args[j + 4]);
                addPokemon.novoAtaque(args[j + 5]);
                this.jogador1.adicionarPokemon(addPokemon);
                j += 6;
            }

//            System.out.println("-------------------------------------------");
            this.getResumoJogador(jogador0);
//            System.out.println("-------------------------------------------");
            this.getResumoJogador(jogador1);
//            System.out.println("-------------------------------------------");

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
                addPokemon.novoAtaque(args[j + 2]);
                addPokemon.novoAtaque(args[j + 3]);
                addPokemon.novoAtaque(args[j + 4]);
                addPokemon.novoAtaque(args[j + 5]);
                this.jogador0.adicionarPokemon(addPokemon);
                j += 6;
            }

//            System.out.println("-------------------------------------------");
            this.getResumoJogador(jogador0);
//            System.out.println("-------------------------------------------");

            this.jogador1 = inializaJogManualmente(2);

            System.out.printf("\n");

//            System.out.println("-------------------------------------------");
            this.getResumoJogador(jogador0);
//            System.out.println("-------------------------------------------");
            this.getResumoJogador(jogador1);
//            System.out.println("-------------------------------------------");

            return;
        }

        if (nJogadores == 0) {
            System.out.printf("Inicializando Jogadores Manualmente\n\n");

            this.jogador0 = inializaJogManualmente(1);
            System.out.printf("\n");
            this.jogador1 = inializaJogManualmente(2);

//            System.out.println("-------------------------------------------");
            this.getResumoJogador(jogador0);
//            System.out.println("-------------------------------------------");
            this.getResumoJogador(jogador1);
//            System.out.println("-------------------------------------------");
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
        System.out.println("-------------------------------------------");
        System.out.printf(Main.ANSI_BLUE + "\t\tTime - Jogador %d - %s\n\n" + Main.ANSI_RESET, this.jogador0 == jogador ? 1 : 2 , jogador.getClass().getTypeName() == "pokesim.Humano" ? "Humano" : "Máquina" );
        for (Pokemon pokemon : jogador.getTime()) {
            System.out.printf("%-14s LVL %4s %15s\n", pokemon.getEspecie().getNome(), pokemon.getLevel(), pokemon.getStatus().toString());
            List<Ataque> ataquesIt = pokemon.getAtaques();
            for (Ataque atk : ataquesIt) {
                System.out.printf("ATK %d - %s - %s - %s\n", ataquesIt.indexOf(atk) + 1, atk.getNome(), atk.getTipo(), atk.getClass().toString().substring(14));
            }
            System.out.printf("\n");
        }
        System.out.println("-------------------------------------------");
        return;
    }

    public void printarTabelaEspecie() {
        System.out.printf("%15s%15s%15s%15s%15s%15s%15s%15s%15s\n", "ID", "Espécie", "Tipo 1", "Tipo2", "BaseHP", "BaseATK", "BaseDEF", "BaseSPE", "BaseSPD");
        for (int x = 0; x < tabelaEspecie.length; x++){
            for (int y = 0; y < 9; y++){
                System.out.printf("%15s", Batalha.tabelaEspecie[x][y]);
            }
            System.out.printf("\n");
        }
        return;
    }

    public void printarTabelaAtaque() {
        System.out.printf("%20s%20s%20s%20s%20s%20s%20s%20s\n", "ID", "Ataque", "Tipo", "PP", "Power", "Acurracy", "Classe", "Parametros");
        for (int x = 0; x < tabelaAtaque.length; x++){
            for (int y = 0; y < 8; y++){
                System.out.printf("%20s", Batalha.tabelaAtaque[x][y]);
            }
            System.out.printf("\n");
        }
        return;
    }

    public boolean continuarJogo() {
        boolean pokemonJog0 = false, pokemonJog1 = false;
        for (Pokemon pokemon : this.jogador0.getTime()) {
            if (pokemon.getStatus() != Status.FAINTED) {
                pokemonJog0 = true;
            }
        }
        for (Pokemon pokemon : this.jogador1.getTime()) {
            if (pokemon.getStatus() != Status.FAINTED) {
                pokemonJog1 = true;
            }
        }

        if (!pokemonJog0 && pokemonJog1) {
            setVencedorId(2);
        } else if (pokemonJog0 && !pokemonJog1) {
            setVencedorId(1);
        }

        return (pokemonJog0 && pokemonJog1);
    }

    public void executarTurno() {
        int escolhaJogador0, escolhaJogador1;
        int atkJogador0 = -1, atkJogador1 = -1;
        int trocaJogador0 = -1, trocaJogador1 = -1;
        int chanceCuraStatus;
        Random curaStatus = new Random();

        System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
        escolhaJogador0 = getJogador0().escolherComando();
        if (escolhaJogador0 == 1) {
            System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
            atkJogador0 = getJogador0().escolherAtaque();
        } else if (escolhaJogador0 == 2){
            System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
            trocaJogador0 = getJogador0().trocarPokemon();
        }

        System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
        escolhaJogador1 = getJogador1().escolherComando();

        if (escolhaJogador1 == 1) {
            System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
            atkJogador1 = getJogador1().escolherAtaque();
        } else if (escolhaJogador1 == 2){
            System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
            trocaJogador1 = getJogador1().trocarPokemon();
        }

        System.out.println(Main.ANSI_YELLOW + "\n============= Executando Turno =============\n" + Main.ANSI_RESET);

        if (escolhaJogador0 == 2 && trocaJogador0 != -1) {
            System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
            getJogador0().efetivarTroca(trocaJogador0);
        } else if (escolhaJogador1 == 2 && trocaJogador1 != -1) {
            System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
            getJogador1().efetivarTroca(trocaJogador1);
        }

        if (getJogador0().getTime().get(0).valorAtributo(Atributo.SPD) >= getJogador1().getTime().get(0).valorAtributo(Atributo.SPD)) {
            if (escolhaJogador0 == 1 && atkJogador0 != -1) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                getJogador0().usarAtaque(atkJogador0, getJogador1());
            }
            if (escolhaJogador1 == 1 && atkJogador1 != -1 && getJogador1().getTime().get(0).getStatus() != Status.FAINTED) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
                getJogador1().usarAtaque(atkJogador1, getJogador0());
            } else if (getJogador1().getTime().get(0).getStatus() == Status.FAINTED) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
                trocaFainted(getJogador1());
            }
            if (getJogador0().getTime().get(0).getStatus() == Status.FAINTED) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                trocaFainted(getJogador0());
            }
        } else if (getJogador0().getTime().get(0).valorAtributo(Atributo.SPD) < getJogador1().getTime().get(0).valorAtributo(Atributo.SPD)) {
            if (escolhaJogador1 == 1 && atkJogador1 != -1) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
                getJogador1().usarAtaque(atkJogador1, getJogador0());
            }
            if (escolhaJogador0 == 1 && atkJogador0 != -1 && getJogador0().getTime().get(0).getStatus() != Status.FAINTED) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                getJogador0().usarAtaque(atkJogador0, getJogador1());
            } else if (getJogador0().getTime().get(0).getStatus() == Status.FAINTED) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                trocaFainted(getJogador0());
            }
            if (getJogador1().getTime().get(0).getStatus() == Status.FAINTED) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                trocaFainted(getJogador1());
            }
        } else {
            Random random = new Random();
            if (random.nextInt(2) == 1) {
                if (escolhaJogador1 == 1 && atkJogador1 != -1) {
                    System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
                    getJogador1().usarAtaque(atkJogador1, getJogador0());
                }
                if (escolhaJogador0 == 1 && atkJogador0 != -1 && getJogador0().getTime().get(0).getStatus() != Status.FAINTED) {
                    System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                    getJogador0().usarAtaque(atkJogador0, getJogador1());
                } else if (getJogador0().getTime().get(0).getStatus() == Status.FAINTED) {
                    System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                    trocaFainted(getJogador0());
                }
                if (getJogador1().getTime().get(0).getStatus() == Status.FAINTED) {
                    System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                    trocaFainted(getJogador1());
                }
            } else {
                if (escolhaJogador0 == 1 && atkJogador0 != -1) {
                    System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                    getJogador0().usarAtaque(atkJogador0, getJogador1());
                }
                if (escolhaJogador1 == 1 && atkJogador1 != -1 && getJogador1().getTime().get(0).getStatus() != Status.FAINTED) {
                    System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
                    getJogador1().usarAtaque(atkJogador1, getJogador0());
                } else if (getJogador1().getTime().get(0).getStatus() == Status.FAINTED) {
                    System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
                    trocaFainted(getJogador1());
                }
                if (getJogador0().getTime().get(0).getStatus() == Status.FAINTED) {
                    System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                    trocaFainted(getJogador0());
                }
            }
            System.out.printf("\n");
        }

        if (getJogador0().getTime().get(0).getStatus() == Status.BURN || getJogador0().getTime().get(0).getStatus() == Status.POISON ||
                getJogador1().getTime().get(0).getStatus() == Status.BURN || getJogador1().getTime().get(0).getStatus() == Status.POISON) {
//            System.out.printf(Main.ANSI_YELLOW + "--------------------------------------------\n\n" + Main.ANSI_RESET);
            if (getJogador0().getTime().get(0).getStatus() == Status.BURN) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                getJogador0().buning();
                if (getJogador0().getTime().get(0).getStatus() == Status.FAINTED) {
                    System.out.printf("%s foi derrotado.\n", getJogador0().getTime().get(0).getEspecie().getNome());
                    trocaFainted(getJogador0());
                }
                System.out.printf("\n");
            } else if (getJogador0().getTime().get(0).getStatus() == Status.POISON) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 1 - %s\n\n" + Main.ANSI_RESET, getJogador0().getClass().toString().substring(14));
                getJogador0().poisoned();
                if (getJogador0().getTime().get(0).getStatus() == Status.FAINTED) {
                    System.out.printf("%s foi derrotado.\n", getJogador0().getTime().get(0).getEspecie().getNome());
                    trocaFainted(getJogador0());
                }
                System.out.printf("\n");
            }
            if (getJogador1().getTime().get(0).getStatus() == Status.BURN) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
                getJogador1().buning();
                if (getJogador1().getTime().get(0).getStatus() == Status.FAINTED) {
                    System.out.printf("%s foi derrotado.\n", getJogador1().getTime().get(0).getEspecie().getNome());
                    trocaFainted(getJogador1());
                }
                System.out.printf("\n");
            } else if (getJogador1().getTime().get(0).getStatus() == Status.POISON) {
                System.out.printf(Main.ANSI_BLUE + "\t\t\tJogador 2 - %s\n\n" + Main.ANSI_RESET, getJogador1().getClass().toString().substring(14));
                getJogador1().poisoned();
                if (getJogador1().getTime().get(0).getStatus() == Status.FAINTED) {
                    System.out.printf("%s foi derrotado.\n", getJogador1().getTime().get(0).getEspecie().getNome());
                    trocaFainted(getJogador1());
                }
                System.out.printf("\n");
            }
        }
        System.out.println(Main.ANSI_YELLOW + "============================================\n" + Main.ANSI_RESET);

        //Cura Status
        if (getJogador0().getTime().get(0).isFlinch()) {
            getJogador0().getTime().get(0).setFlinch(false);
        }
        if (getJogador1().getTime().get(0).isFlinch()) {
            getJogador1().getTime().get(0).setFlinch(false);
        }

        if (getJogador0().getTime().get(0).isConfusion()) {
            chanceCuraStatus = curaStatus.nextInt(101);
            if (chanceCuraStatus <= 20) {
                getJogador0().getTime().get(0).setConfusion(false);
            }
        }
        if (getJogador1().getTime().get(0).isConfusion()) {
            chanceCuraStatus = curaStatus.nextInt(101);
            if (chanceCuraStatus <= 20) {
                getJogador1().getTime().get(0).setConfusion(false);
            }
        }

        if (getJogador0().getTime().get(0).getStatus() == Status.SLEEP) {
            chanceCuraStatus = curaStatus.nextInt(101);
            if (chanceCuraStatus <= 20) {
                getJogador0().getTime().get(0).setStatus(Status.OK);
            }
        }

        if (getJogador1().getTime().get(0).getStatus() == Status.SLEEP) {
            chanceCuraStatus = curaStatus.nextInt(101);
            if (chanceCuraStatus <= 20) {
                getJogador1().getTime().get(0).setStatus(Status.OK);
            }
        }

        if (getJogador0().getTime().get(0).getStatus() == Status.FROZEN) {
            chanceCuraStatus = curaStatus.nextInt(101);
            if (chanceCuraStatus <= 10) {
                getJogador0().getTime().get(0).setStatus(Status.OK);
            }
        }

        if (getJogador1().getTime().get(0).getStatus() == Status.FROZEN) {
            chanceCuraStatus = curaStatus.nextInt(101);
            if (chanceCuraStatus <= 10) {
                getJogador1().getTime().get(0).setStatus(Status.OK);
            }
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
    }

    public void trocaFainted(Jogador jogador) {
//        System.out.printf("%s foi derrotado\n", jogador.getTime().get(0).getEspecie().getNome());
        if (jogador.getTime().size() > 1) {
            if (jogador.getTime().get(1).getStatus() != Status.FAINTED) {
                System.out.printf("Seu novo pokemon é: %s\n\n", jogador.getTime().get(1).getEspecie().getNome());
            }
            Pokemon troca = jogador.getTime().get(0);
            int i = 0;
            while (i + 1 < jogador.getTime().size()) {
                jogador.getTime().set(i, jogador.getTime().get(++i));
            }
            jogador.getTime().set(i, troca);
        }
    }

    public static void carregaTabelaDano() {
        int countLinhas = 0;
        File arqTabDano;
        Scanner bufferCount = null;
        Scanner buffer = null;

        if (System.getProperty("os.name").compareToIgnoreCase("linux") == 0) {
            arqTabDano = new File("src/pokesim/tabelaDano.txt");
        } else {
            arqTabDano = new File("src\\pokesim\\tabelaDano.txt");
        }

        try {
            buffer = new Scanner(arqTabDano).useDelimiter("\\t|\\n|    ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            bufferCount = new Scanner(arqTabDano);
        } catch (FileNotFoundException e) {
            e.printStackTrace();;
        }

        while (bufferCount.hasNextLine()){
            countLinhas++;
            bufferCount.nextLine();
        }

        Batalha.tabelaDano = new String[countLinhas][16];

        for (int i = 0; i < countLinhas; i++) {
            for (int j = 0; j < 16; j++) {
                if (buffer.hasNext())
                    Batalha.tabelaDano[i][j] = buffer.next();
            }
        }

//        for (int i = 0; i < countLinhas; i++) {
//            for (int j = 0; j < 16; j++) {
//                System.out.printf("%15s", Batalha.tabelaDano[i][j]);
//            }
//            System.out.printf("\n");
//        }
    }

    public static void carregaTabelaAE() {
        tabelaAE = new int[13][2];

        int j = -6;
        for (int i = 0; i < 13; i++) {
            tabelaAE[i][0] = j;
            j++;
        }

        tabelaAE[0][1] = 33;
        tabelaAE[1][1] = 37;
        tabelaAE[2][1] = 43;
        tabelaAE[3][1] = 50;
        tabelaAE[4][1] = 60;
        tabelaAE[5][1] = 75;
        tabelaAE[6][1] = 100;
        tabelaAE[7][1] = 133;
        tabelaAE[8][1] = 166;
        tabelaAE[9][1] = 200;
        tabelaAE[10][1] = 233;
        tabelaAE[11][1] = 266;
        tabelaAE[12][1] = 300;
    }

    public static int[][] getTabelaAE() {
        return tabelaAE;
    }

    public int getVencedorId() {
        return this.vencedorId;
    }

    public void setVencedorId(int vencedorId) {
        this.vencedorId = vencedorId;
    }

    public Jogador inializaJogManualmente(int id) {
        Scanner opcao = new Scanner(System.in);
        int entrada, auxId, auxLvl;
        Jogador jogadorIt;

        System.out.printf(Main.ANSI_BLUE + "\t\t\t Jogador %d\n" + Main.ANSI_RESET, id);
        System.out.printf("1 - Máquina\n2 - Humano\nEscolha o tipo do jogador: ");
        entrada = opcao.nextInt();
        while (entrada != 1 && entrada != 2) {
            System.out.printf(Main.ANSI_RED + "Valor inválido.\n" + Main.ANSI_RESET);
            System.out.printf("Esoolha novamente: ");
            entrada = opcao.nextInt();
        }
        if (entrada == 1) {
            jogadorIt = new Maquina();
        } else {
            jogadorIt = new Humano();
        }
        System.out.printf("Quantos pokemons o time terá: ");
        entrada = opcao.nextInt();
        while (entrada > 6 || entrada < 1) {
            System.out.println(Main.ANSI_RED + "Você não pode ter mais que 6 pokemons e deve ter pelo menos 1 pokemon." + Main.ANSI_RESET);
            System.out.printf("Insira novamente a quantidade de pokemons em seu time: ");
            entrada = opcao.nextInt();
        }
        System.out.println("Se você deseja ver a lista com todos os pokemons e status dísponíveis?");
        System.out.printf("1 - Não\n2 - Sim\nEscolha sua opção: ");
        if (opcao.nextInt() == 2) {
            printarTabelaEspecie();
        }
        for (int k = 0; k < entrada; k++) {
            System.out.println("Entre com o ID e o level do pokemon separando por espaço. (Ex: 1 10 = ID 1 Level 10)");
            System.out.printf("Pokemon %d: ", k + 1);
            auxId = opcao.nextInt();
            auxLvl = opcao.nextInt();
            while (auxId > tabelaEspecie.length || auxId < 1 || auxLvl < 1) {
                if (auxId > tabelaEspecie.length || auxId < 1) {
                    System.out.println(Main.ANSI_RED + "O ID escolhido é inválido." + Main.ANSI_RESET);
                }
                if (auxLvl < 1) {
                    System.out.println(Main.ANSI_RED + "O LVL escolhido é inválido." + Main.ANSI_RESET);
                }
                System.out.println("Deseja visualizar a tabela de espécies disponíveis?");
                System.out.printf("1 - Não\n2 - Sim\nEscolha sua opção: ");
                if (opcao.nextInt() == 2) {
                    printarTabelaEspecie();
                }
                System.out.println("Entre com o ID e o level do pokemon separando por espaço. (Ex: 1 10 = ID 1 Level 10)");
                System.out.printf("Pokemon %d: ", k + 1);
                auxId = opcao.nextInt();
                auxLvl = opcao.nextInt();
            }
            Pokemon addPokemon = new Pokemon(auxId, auxLvl);

            System.out.printf("Esocolha a quantidade de ataques deste pokemon: ");
            int entradaQtde = opcao.nextInt();
            while (entradaQtde < 1 || entradaQtde > 4) {
                System.out.println(Main.ANSI_RED + "Você não pode ter mais que 4 ataques por pokemon e deve ter pelo menso 1 ataque." + Main.ANSI_RESET);
                System.out.printf("Insira novamente a quantidade de ataques deste pokemon: ");
                entradaQtde = opcao.nextInt();
            }
            System.out.println("Se você deseja ver a lista com todos os ataques dísponíveis?");
            System.out.printf("1 - Não\n2 - Sim\nEscolha sua opção: ");
            if (opcao.nextInt() == 2) {
                printarTabelaAtaque();
            }
            int idAtaque;
            System.out.println("Entre com o ID dos ataques:");
            for (int n = 0; n < entradaQtde; n++) {
                System.out.printf("Ataque %d: ", n + 1);
                idAtaque = opcao.nextInt();
                while (idAtaque > tabelaAtaque.length || idAtaque < 1) {
                    System.out.println(Main.ANSI_RED + "O ID escolhido é inválido." + Main.ANSI_RESET);
                    System.out.println("Deseja visualizar a tabela de ataques disponíveis?");
                    System.out.printf("1 - Não\n2 - Sim\nEscolha sua opção: ");
                    if (opcao.nextInt() == 2) {
                        printarTabelaAtaque();
                    }
                    System.out.println("Entre com o ID dos ataques:");
                    System.out.printf("Ataque %d: ", n + 1);
                    idAtaque = opcao.nextInt();
                }
                addPokemon.novoAtaque(idAtaque);
            }
            jogadorIt.adicionarPokemon(addPokemon);
        }
        return jogadorIt;
    }
}
