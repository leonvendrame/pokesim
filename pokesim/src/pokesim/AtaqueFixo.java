package pokesim;

import java.util.Random;

public class AtaqueFixo extends Ataque {
    private int val;

    AtaqueFixo(int id, String[] parametros) {
        super(id);
        if (parametros[0].compareToIgnoreCase("lvl") == 0) {
            setVal(-1);
        } else {
            setVal(Integer.parseInt(parametros[0]));
        }
    }

    @Override
    public void efeito(Pokemon atacante, Pokemon defensor) {
        double dano = 0;
        setPpAtual(getPpAtual() - 1);
        Random random = new Random();
        int chanceConfusion;
        System.out.printf("%s usou %s.\n", atacante.getEspecie().getNome(), getNome());
        if (!calculoAcerto(atacante, defensor)) {
            System.out.printf(Main.ANSI_RED + "%s errou.\n" + Main.ANSI_RESET, atacante.getEspecie().getNome());
        } else {
            if (val == -1) {
                dano = atacante.getLevel();
            } else {
                dano = val;
            }
            chanceConfusion = random.nextInt(101);
            if (atacante.isConfusion() && chanceConfusion <= 50) {
                atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) - dano);
                System.out.printf("%s está confuso e causou %.2f de dano em si mesmo.\n", atacante.getEspecie().getNome(), dano);
                if (atacante.getStatus() == Status.FAINTED) {
                    System.out.printf("%s foi derrotado.\n", atacante.getEspecie().getNome());
                }
            } else {
                defensor.setHpAtual(defensor.valorAtributo(Atributo.HPATUAL) - dano);
                System.out.printf("%s causou %.2f de dano em %s.\n", atacante.getEspecie().getNome(), dano, defensor.getEspecie().getNome());
                if (defensor.getStatus() == Status.FAINTED) {
                    System.out.printf("%s derrotou %s do oponente.\n", atacante.getEspecie().getNome(), defensor.getEspecie().getNome());
                }
            }
        }
        return;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
