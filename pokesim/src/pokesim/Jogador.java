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
        for (Pokemon pokemon : this.time) {
            if (pokemon.getStatus() != Status.FAINTED) {
                System.out.printf("%s - %s (HP: %s | %s)\n", this.time.indexOf(pokemon), pokemon.getEspecie().getNome().toString(),
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
        if (this.time.get(opcao).getStatus() != Status.FAINTED) {
            Pokemon troca = this.time.get(0);
            this.time.set(0, this.time.get(opcao));
            this.time.set(opcao, troca);
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
