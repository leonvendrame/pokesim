package pokesim;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Maquina extends Jogador {

    Maquina() {
        super();
    }

    @Override
    public int escolherComando() {
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
        System.out.printf(Main.ANSI_BLUE + "Escolhendo... \n" + Main.ANSI_RESET);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("\n");
        return 1;
    }

    @Override
    public int escolherAtaque() {
        System.out.println("Seus ataques dísponíveis são: ");
        for (Ataque ataque : getTime().get(0).getAtaques()) {
            System.out.printf("%d - %s - %s - (PP %.0f | %.0f)\n", getTime().get(0).getAtaques().indexOf(ataque) + 1, ataque.getNome(), ataque.getTipo(),
                    ataque.getPpAtual(), ataque.getPpMax());
        }
        System.out.printf("Entre com sua escolha: ");
        System.out.printf(Main.ANSI_BLUE + "Escolhendo... \n\n" + Main.ANSI_RESET);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int ataqueMax = getTime().get(0).getAtaques().size();
        Random random = new Random();
        return random.nextInt(ataqueMax);
    }
}
