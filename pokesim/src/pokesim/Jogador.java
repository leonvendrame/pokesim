package pokesim;

import jdk.nashorn.internal.scripts.JO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Jogador {
    private List<Pokemon> time;

    Jogador() {
        this.time = new ArrayList<>();
    }

    public abstract void escolherComando();

    public void trocarPokemon() {
        System.out.println("Lista de opções dísponíveis:\n");
        for (Pokemon pokemon : getTime()) {
            if (pokemon.getStatus() != Status.FAINTED) {
                System.out.printf(Main.ANSI_GREEN + "%s - %s (HP: %s | %s)\n" + Main.ANSI_RESET, getTime().indexOf(pokemon), pokemon.getEspecie().getNome().toString(),
                                        pokemon.valorAtributo(Atributo.HPATUAL), pokemon.valorAtributo(Atributo.HPMAX));
            } else {
                System.out.printf(Main.ANSI_RED + "✖ %s - FAINTED (HP: %s | %s) ✖\n" + Main.ANSI_RESET, pokemon.getEspecie().getNome().toString(),
                        pokemon.valorAtributo(Atributo.HPATUAL), pokemon.valorAtributo(Atributo.HPMAX));
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.printf("\nEscolha sua opção: ");
        int opcao = scanner.nextInt();

        while (opcao >= getTime().size() || opcao < 0 || getTime().get(opcao).getStatus() == Status.FAINTED) {
            if (opcao >= getTime().size() || opcao < 0) {
                System.out.println("Erro, a opção desejada não está dísponível");
            } else {
                System.out.println("Você não pode trocar um pokemon com status FAINTED");
            }
            System.out.printf("Escolha sua opção: ");
            opcao = scanner.nextInt();
        }
        if (getTime().get(opcao).getStatus() != Status.FAINTED) {
            Pokemon troca = getTime().get(0);
            getTime().set(0, getTime().get(opcao));
            getTime().set(opcao, troca);
        }

        return;
    }

    public List<Pokemon> getTime() {
        return this.time;
    }

    public void adicionarPokemon(Pokemon pokemon) {
        if (this.getTime().size() < 6) {
            this.getTime().add(pokemon);
        }
    }

    public void usarAtaque() {
        return;
    }
}
