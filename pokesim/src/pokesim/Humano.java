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
        System.out.println("1 - Usar ataque");
        System.out.println("2 - Trocar pokemon");
        System.out.printf("Entre com sua escolha: ");
        escolha = scanner.nextInt();
        while (escolha != 1 && escolha != 2) {
            System.out.println(Main.ANSI_RED + "Escolha inválida." + Main.ANSI_RESET);
            System.out.printf("Entre novamente com sua escolha: ");
            escolha = scanner.nextInt();
        }
        System.out.printf("\n");
        return escolha;
    }
}
