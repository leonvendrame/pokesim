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
        System.out.println("1 - Usar ataque");
        System.out.println("2 - Trocar pokemon");
        System.out.printf("Entre com sua escolha: ");
        System.out.printf(Main.ANSI_BLUE + "Escolhendo... " + Main.ANSI_RESET);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("\n\n");
        return 1;
    }

    @Override
    public int escolherAtaque() {
        System.out.println("Seus ataques dísponíveis são: ");
        for (Ataque ataque : getTime().get(0).getAtaques()) {
            System.out.printf("%d - %-30s\n", getTime().get(0).getAtaques().indexOf(ataque) + 1, ataque.getNome());
        }
        System.out.printf("Entre com sua escolha: ");
        System.out.printf(Main.ANSI_BLUE + "Escolhendo... " + Main.ANSI_RESET);

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
