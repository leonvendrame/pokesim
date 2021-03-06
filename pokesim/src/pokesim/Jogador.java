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

    public abstract int escolherComando();

    public int trocarPokemon() {
        System.out.println("Lista de opções de troca dísponíveis:");
        for (Pokemon pokemon : getTime()) {
            if (pokemon != getTime().get(0)) {
                if (pokemon.getStatus() != Status.FAINTED) {
                    System.out.printf(Main.ANSI_GREEN + "%s - %s (HP: %s | %s)\n" + Main.ANSI_RESET, getTime().indexOf(pokemon), pokemon.getEspecie().getNome().toString(),
                            pokemon.valorAtributo(Atributo.HPATUAL), pokemon.valorAtributo(Atributo.HPMAX));
                } else {
                    System.out.printf(Main.ANSI_RED + "✖ %s - FAINTED (HP: %s | %s) ✖\n" + Main.ANSI_RESET, pokemon.getEspecie().getNome().toString(),
                            pokemon.valorAtributo(Atributo.HPATUAL), pokemon.valorAtributo(Atributo.HPMAX));
                }
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.printf("Escolha sua opção: ");
        int opcao = scanner.nextInt();

        while (opcao >= getTime().size() || opcao < 1 || getTime().get(opcao).getStatus() == Status.FAINTED) {
            if (opcao >= getTime().size() || opcao < 1) {
                System.out.println(Main.ANSI_RED + "Erro, a opção desejada não está dísponível" + Main.ANSI_RESET);
            } else {
                System.out.println(Main.ANSI_RED + "Você não pode trocar um pokemon com status FAINTED" + Main.ANSI_RESET);
            }
            System.out.printf("Escolha sua opção: ");
            opcao = scanner.nextInt();
        }

        System.out.printf("\n");

        return opcao;
    }

    public void efetivarTroca(int opcao) {
        if (getTime().get(opcao).getStatus() != Status.FAINTED) {
            Pokemon troca = getTime().get(0);
            getTime().set(0, getTime().get(opcao));
            getTime().set(opcao, troca);
            System.out.printf(Main.ANSI_GREEN + "Troca efeutada com sucesso.\n" + Main.ANSI_RESET + "Seu pokemon atual é %s.\n\n", getTime().get(0).getEspecie().getNome());
        }
    }

    public int escolherAtaque() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        if (todosSemPp()) {
            System.out.println(Main.ANSI_RED + "Você não possui mais ataques disponíveis.\n" + Main.ANSI_RESET);
            return -1;
        }

        System.out.println("Seus ataques dísponíveis são: ");
        for (Ataque ataque : getTime().get(0).getAtaques()) {
            if (ataque.getPpAtual() > 1) {
                System.out.printf("%d - %s - %s - (PP %.0f | %.0f)\n", getTime().get(0).getAtaques().indexOf(ataque) + 1, ataque.getNome(), ataque.getTipo(),
                        ataque.getPpAtual(), ataque.getPpMax());
            } else {
                System.out.printf(Main.ANSI_RED + "✖ - %s - %s - (PP %.0f | %.0f)\n" + Main.ANSI_RESET, ataque.getNome(), ataque.getTipo(),ataque.getPpAtual(), ataque.getPpMax());
            }
        }
        System.out.printf("Entre com sua escolha: ");
        opcao = scanner.nextInt();
        opcao--;
        while (opcao < 0 || opcao > getTime().get(0).getAtaques().size() - 1  || getTime().get(0).getAtaques().get(opcao).getPpAtual() == 0) {
            System.out.println(Main.ANSI_RED + "Opção Inválida, por favor escolha novamente." + Main.ANSI_RESET);
            System.out.printf("Entre com sua escolha: ");
            opcao = scanner.nextInt();
            opcao--;
        }
        while (getTime().get(0).getAtaques().get(opcao).getPpAtual() < 1) {
            System.out.println(Main.ANSI_RED + "Esse ataque não pode ser mais utilizado, por favor escolha outro." + Main.ANSI_RESET);
            System.out.printf("Entre com sua escolha: ");
            opcao = scanner.nextInt();
            opcao--;
        }
        System.out.printf("\n");

        return opcao;
    }

    public List<Pokemon> getTime() {
        return this.time;
    }

    public void adicionarPokemon(Pokemon pokemon) {
        if (this.getTime().size() < 6) {
            this.getTime().add(pokemon);
        }
    }

    public void usarAtaque(int ataqueEscolha, Jogador oponente) {
        if (ataqueEscolha == -1 ) {
            return;
        }

        if (getTime().get(0).getAtaques().get(ataqueEscolha).getPpAtual() < 1) {
            System.out.printf(Main.ANSI_RED + "O ataque escolhido não pode ser utilizado.\n\n" + Main.ANSI_RESET);
            return;
        }

        Pokemon atacante;
        Pokemon defensor;

        atacante = getTime().get(0);
        defensor = oponente.getTime().get(0);

        atacante.getAtaques().get(ataqueEscolha).efeito(atacante, defensor);
        System.out.printf("\n");
        return;
    }

    public void buning() {
        getTime().get(0).setHpAtual(getTime().get(0).valorAtributo(Atributo.HPATUAL) - (0.0625 * getTime().get(0).valorAtributo(Atributo.HPMAX)));
        System.out.printf("%s tomou %.2f de dano por estar queimando.\n", getTime().get(0).getEspecie().getNome(), 0.0625 * getTime().get(0).valorAtributo(Atributo.HPMAX));
    }

    public void poisoned() {
        getTime().get(0).setHpAtual(getTime().get(0).valorAtributo(Atributo.HPATUAL) - (0.0625 * getTime().get(0).valorAtributo(Atributo.HPMAX)));
        System.out.printf("%s tomou %.2f de dano por estar envenenado.\n", getTime().get(0).getEspecie().getNome(), 0.0625 * getTime().get(0).valorAtributo(Atributo.HPMAX));
    }

    public boolean todosFainted() {
        boolean ok = true;
        for (Pokemon pokemon : getTime()) {
            if (pokemon != getTime().get(0)) {
                if (pokemon.getStatus() != Status.FAINTED) {
                    ok = false;
                }
            }
        }
        return ok;
    }

    public boolean todosSemPp() {
        boolean ok = true;
        for (Ataque ataque : getTime().get(0).getAtaques()) {
            if (ataque.getPpAtual() != 0) {
                ok = false;
            }
        }
        return ok;
    }
}
