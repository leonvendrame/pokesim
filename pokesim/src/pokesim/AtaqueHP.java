package pokesim;

import java.util.Random;

public class AtaqueHP extends Ataque {
    private int valor;
    private double porcentagem;

    AtaqueHP(int id, String[] parametros) {
        super(id);
        if (parametros[0].compareToIgnoreCase("max_hp") == 0) {
            setValor(1);
        } else if (parametros[0].compareToIgnoreCase("dano") == 0) {
            setValor(2);
        }
        setPorcentagem(Double.parseDouble(parametros[1]));
    }

    @Override
    public void efeito(Pokemon atacante, Pokemon defensor) {
        double dano = 0;
        Random random = new Random();
        int chanceConfusion;
        setPpAtual(getPpAtual() - 1);
        System.out.printf("%s usou %s.\n", atacante.getEspecie().getNome(), getNome());
        if (!calculoAcerto(atacante, defensor)) {
            System.out.printf(Main.ANSI_RED + "%s errou.\n" + Main.ANSI_RESET, atacante.getEspecie().getNome());
        } else {
            dano = calculoDano(atacante, defensor);
            chanceConfusion = random.nextInt(101);
            if (atacante.isConfusion() && chanceConfusion <= 50) {
                atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) - dano);
                System.out.printf("%s está confuso e causou %.2f de dano em si mesmo.\n",atacante.getEspecie().getNome(), dano);
                if (atacante.getStatus() == Status.FAINTED) {
                    System.out.printf("%s foi derrotado.\n", atacante.getEspecie().getNome());
                }
            } else {
                defensor.setHpAtual(defensor.valorAtributo(Atributo.HPATUAL) - dano);
                System.out.printf("%s causou %.2f de dano em %s.\n", atacante.getEspecie().getNome(), dano, defensor.getEspecie().getNome());
                if (defensor.getStatus() == Status.FAINTED) {
                    System.out.printf("%s derrotou %s do oponente.\n", atacante.getEspecie().getNome(), defensor.getEspecie().getNome());
                }
                if (getValor() == 2) {
                    atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) + (dano * getPorcentagem()));
                    System.out.printf("%s se curou em %.2f hp - (HP: %.2f | %.2f).\n", atacante.getEspecie().getNome(), dano * getPorcentagem(),
                            atacante.valorAtributo(Atributo.HPATUAL), atacante.valorAtributo(Atributo.HPMAX));
                } else {
                    atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) + (atacante.valorAtributo(Atributo.HPMAX) * getPorcentagem()));
                    System.out.printf("%s se curou em %.2f hp - (HP: %.2f | %.2f).\n", atacante.getEspecie().getNome(), atacante.valorAtributo(Atributo.HPMAX) * getPorcentagem(),
                            atacante.valorAtributo(Atributo.HPATUAL), atacante.valorAtributo(Atributo.HPMAX));
                }
            }
        }
        return;
    }

    public int getValor() {
        return valor;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }
}
