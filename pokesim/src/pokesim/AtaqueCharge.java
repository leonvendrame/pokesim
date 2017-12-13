package pokesim;

import java.util.Random;

public class AtaqueCharge extends Ataque {
    private int count;

    AtaqueCharge(int id) {
        super(id);
        setCount(1);
    }

    @Override
    public void efeito(Pokemon atacante, Pokemon defensor) {
        double dano = 0;
        setPpAtual(getPpAtual() - 1);
        Random random = new Random();
        int chanceConfusion;
        if (getCount() == 1) {
            System.out.printf("%s carregou o ataque %s e poderá usá-lo na próxima rodada.\n", atacante.getEspecie().getNome(), getNome());
            setCount(0);
        } else {
            setCount(1);
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
                    defensor.setHpAtual(defensor.valorAtributo(Atributo.HPATUAL) - dano);
                    System.out.printf("%s causou %.2f de dano em %s.\n", atacante.getEspecie().getNome(), dano, defensor.getEspecie().getNome());
                    if (defensor.getStatus() == Status.FAINTED) {
                        System.out.printf("%s derrotou %s do oponente.\n", atacante.getEspecie().getNome(), defensor.getEspecie().getNome());
                    }
                }
            }
        }
        return;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
