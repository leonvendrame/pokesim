package pokesim;

import java.util.Scanner;

public class Humano extends Jogador {

    Humano() {
        super();
    }

    @Override
    public int escolherComando() {
        int escolha;
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Pokemon atual: %s LVL: %d (HP: %.2f | %.2f)\n", getTime().get(0).getEspecie().getNome(), getTime().get(0).getLevel(), getTime().get(0).valorAtributo(Atributo.HPATUAL), getTime().get(0).valorAtributo(Atributo.HPMAX));
        System.out.printf("Lista de opções dísponíveis: \n");
        if (!todosSemPp()) {
            System.out.println("1 - Usar ataque");
        } else {
            System.out.println(Main.ANSI_RED + "✖ - Usar ataque - Você não possui mais ataques para usar" + Main.ANSI_RESET);
        }
        if (getTime().size() != 1 && !todosFainted()) {
            System.out.println("2 - Trocar pokemon");
        } else {
            System.out.println(Main.ANSI_RED + "✖ - Trocar pokemon - Você não possui mais pokemons para trocar" + Main.ANSI_RESET);
        }
        if (todosFainted() && todosSemPp()) {
            System.out.println(Main.ANSI_RED + "Você não possui mais escolhas válidas.\n" + Main.ANSI_RESET);
            return -1;
        }
        System.out.printf("Entre com sua escolha: ");
        escolha = scanner.nextInt();

        while ((escolha != 1 && escolha != 2) || (getTime().size() == 1 && escolha == 2) || (todosFainted() && escolha == 2) || (todosSemPp() && escolha == 1)) {
            System.out.println(Main.ANSI_RED + "Escolha inválida." + Main.ANSI_RESET);
            System.out.printf("Entre novamente com sua escolha: ");
            escolha = scanner.nextInt();
        }
        System.out.printf("\n");
        return escolha;
    }
}
