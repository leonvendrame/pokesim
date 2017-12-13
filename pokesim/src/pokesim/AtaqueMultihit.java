package pokesim;

import java.util.Random;

public class AtaqueMultihit extends Ataque {
    private int min;
    private int max;

    AtaqueMultihit(int id, String[] parametros) {
        super(id);
        setMin(Integer.parseInt(parametros[0]));
        setMax(Integer.parseInt(parametros[1]));
    }

    @Override
    public void efeito(Pokemon atacante, Pokemon defensor) {
        double dano = 0;
        setPpAtual(getPpAtual() - 1);
        Random random = new Random();
        int chanceConfusion, nAtaques;
        System.out.printf("%s usou %s.\n", atacante.getEspecie().getNome(), getNome());
        if (!calculoAcerto(atacante, defensor)) {
            System.out.printf(Main.ANSI_RED + "%s errou.\n" + Main.ANSI_RESET, atacante.getEspecie().getNome());
        } else {
            dano = calculoDano(atacante, defensor);
            chanceConfusion = random.nextInt(101);
            if (atacante.isConfusion() && chanceConfusion <= 50) {
                atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) - dano);
                System.out.printf("%s está confuso e causou %.2f de dano em si mesmo.\n", atacante.getEspecie().getNome(), dano);
                if (atacante.getStatus() == Status.FAINTED) {
                    System.out.printf("%s foi derrotado.\n", atacante.getEspecie().getNome());
                }
            } else {
                nAtaques = random.nextInt((max - min) + 1) + min;
                for (int i = 0; i < nAtaques; i++) {
                    defensor.setHpAtual(defensor.valorAtributo(Atributo.HPATUAL) - dano);
                    System.out.printf("%s causou %.2f de dano em %s.\n", atacante.getEspecie().getNome(), dano, defensor.getEspecie().getNome());
                    dano = calculoDano(atacante, defensor);
                    if (defensor.getStatus() == Status.FAINTED) {
                        System.out.printf("%s derrotou %s do oponente.\n", atacante.getEspecie().getNome(), defensor.getEspecie().getNome());
                        break;
                    }
                }
            }
        }
        return;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
