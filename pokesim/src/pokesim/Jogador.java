package pokesim;

import java.util.List;
import java.util.Scanner;

public abstract class Jogador {
    private List<Pokemon> time;

    public abstract void escolherComando();

    public void trocarPokemon() {
        for (Pokemon pokemon : this.time)
            if (pokemon.getStatus() != Status.FAINTED) {
                System.out.printf("%s - %s (HP: %s | %s)", this.time.indexOf(pokemon), pokemon.getEspecie().toString(),
                        pokemon.getHpAtual(), pokemon.getHpMax());
            }

        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        if (this.time.get(opcao).getStatus() != Status.FAINTED) {
            Pokemon troca = this.time.get(0);
            this.time.set(0, this.time.get(opcao));
            this.time.set(opcao, troca);
        } else {
            System.out.println("Você não pode trocar um pokemon com status FAINTED");
        }

        return;
    }

    public void usarAtaque() {
        return;
    }
}
