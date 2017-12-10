package pokesim;

import java.util.Random;
import java.util.Scanner;

public class Maquina extends Jogador {

    Maquina() {
        super();
    }

    @Override
    public int escolherComando() {
        return 1;
    }

    @Override
    public int escolherAtaque() {
        int ataqueMax = getTime().get(0).getAtaques().size();
        Random random = new Random();
        return random.nextInt(ataqueMax);
    }
}
