package pokesim;

import java.util.Random;

public class AtaqueModifier extends Ataque {
    private Atributo mod;
    private int n;
    private int chance;

    AtaqueModifier(int id, String[] parametros) {
        super(id);
        for (Atributo atributo : Atributo.values()) {
            if (parametros[0].compareToIgnoreCase(atributo.toString()) == 0){
                setMod(atributo);
            }
        }
        setN(Integer.parseInt(parametros[1]));
        setChance(Integer.parseInt(parametros[2]));
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
            if (ocorre()) {
                if (getN() >= 0) {
                    switch (getMod()) {
                        case EVASION:
                            atacante.setModifierEvasion(getN());
                            System.out.printf("%s alterou seu modificador de %s em +%d\n", atacante.getEspecie().getNome(), Atributo.EVASION.toString(), getN());
                            break;
                        case ACCURACY:
                            atacante.setModifierAccuracy(getN());
                            System.out.printf("%s alterou seu modificador de %s em +%d\n", atacante.getEspecie().getNome(), Atributo.ACCURACY.toString(), getN());
                            break;
                        case SPD:
                            atacante.setModifierSpd(getN());
                            System.out.printf("%s alterou seu modificador de %s em +%d\n", atacante.getEspecie().getNome(), Atributo.SPD.toString(), getN());
                            break;
                        case SPE:
                            atacante.setModifierSpe(getN());
                            System.out.printf("%s alterou seu modificador de %s em +%d\n", atacante.getEspecie().getNome(), Atributo.SPE.toString(), getN());
                            break;
                        case DEF:
                            atacante.setModifierDef(getN());
                            System.out.printf("%s alterou seu modificador de %s em +%d\n", atacante.getEspecie().getNome(), Atributo.DEF.toString(), getN());
                            break;
                        case ATK:
                            atacante.setModifierAtk(getN());
                            System.out.printf("%s alterou seu modificador de %s em +%d\n", atacante.getEspecie().getNome(), Atributo.ATK.toString(), getN());
                            break;
                        default:
                            break;
                    }
                } else if (defensor.getStatus() != Status.FAINTED) {
                    switch (getMod()) {
                        case EVASION:
                            defensor.setModifierEvasion(getN());
                            System.out.printf("%s alterou o modificador de %s de %s em %d\n", atacante.getEspecie().getNome(), Atributo.EVASION.toString(), defensor.getEspecie().getNome(), getN());
                            break;
                        case ACCURACY:
                            defensor.setModifierAccuracy(getN());
                            System.out.printf("%s alterou o modificador de %s de %s em %d\n", atacante.getEspecie().getNome(), Atributo.ACCURACY.toString(), defensor.getEspecie().getNome(), getN());
                            break;
                        case SPD:
                            defensor.setModifierSpd(getN());
                            System.out.printf("%s alterou o modificador de %s de %s em %d\n", atacante.getEspecie().getNome(), Atributo.SPD.toString(), defensor.getEspecie().getNome(), getN());
                            break;
                        case SPE:
                            defensor.setModifierSpe(getN());
                            System.out.printf("%s alterou o modificador de %s de %s em %d\n", atacante.getEspecie().getNome(), Atributo.SPE.toString(), defensor.getEspecie().getNome(), getN());
                            break;
                        case DEF:
                            defensor.setModifierDef(getN());
                            System.out.printf("%s alterou o modificador de %s de %s em %d\n", atacante.getEspecie().getNome(), Atributo.DEF.toString(), defensor.getEspecie().getNome(), getN());
                            break;
                        case ATK:
                            defensor.setModifierAtk(getN());
                            System.out.printf("%s alterou o modificador de %s de %s em %d\n", atacante.getEspecie().getNome(), Atributo.ATK.toString(), defensor.getEspecie().getNome(), getN());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return;
    }

    public void setMod(Atributo mod) {
        this.mod = mod;
    }

    public Atributo getMod() {
        return this.mod;
    }

    public int getN() {
        return this.n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getChance() {
        return this.chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public boolean ocorre() {
        Random random = new Random();
        int sorteio = random.nextInt(101);
        if (sorteio <= getChance()) {
            return true;
        }
        return false;
    }
}
