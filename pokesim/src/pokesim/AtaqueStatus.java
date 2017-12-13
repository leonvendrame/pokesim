package pokesim;

import java.util.Random;

public class AtaqueStatus extends Ataque {
    private Status statusAtk;
    private int chance;

    AtaqueStatus(int id, String[] parametros) {
        super(id);
        if (!parametros[0].isEmpty()) {
            for (Status status : Status.values()) {
                if (parametros[0].compareToIgnoreCase(status.toString()) == 0) {
                    setStatusAtk(status);
                }
            }
        } else {
            setStatusAtk(null);
        }
        if (!parametros[1].isEmpty()){
            setChance(Integer.parseInt(parametros[1]));
        }
    }

    @Override
    public void efeito(Pokemon atacante, Pokemon defensor) {
        double dano = 0;
        Random sorteio = new Random();
        Random random = new Random();
        int chance, chanceConfusion;
        setPpAtual(getPpAtual() - 1);
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
                chance = sorteio.nextInt(101);
                if (defensor.getStatus() != Status.FAINTED && chance < getChance() && getStatusAtk() != null && defensor.getStatus() != getStatusAtk()) {
                    if (getStatusAtk() == Status.FLINCH) {
                        defensor.setFlinch(true);
                    } else if (getStatusAtk() == Status.CONFUSION) {
                        defensor.setConfusion(true);
                    } else {
                        defensor.setStatus(getStatusAtk());
                    }
                    System.out.printf("%s causou o status %s em %s.\n", atacante.getEspecie().getNome(), getStatusAtk().toString(), defensor.getEspecie().getNome());
                }
            }
        }
        return;
    }

    public void setStatusAtk(Status status) {
        this.statusAtk = status;
    }

    public Status getStatusAtk() {
        return this.statusAtk;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getChance() {
        return this.chance;
    }
}
