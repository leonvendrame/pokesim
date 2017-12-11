package pokesim;

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
        setPpAtual(getPpAtual() - 1);
        double dano = 0;
        if (!calculoAcerto(atacante, defensor)) {
            System.out.printf(Main.ANSI_RED + "O Pokemon %s errou.\n" + Main.ANSI_RESET, atacante.getEspecie().getNome());
        } else {
            dano = calculoDano(atacante, defensor);
            defensor.setHpAtual(defensor.valorAtributo(Atributo.HPATUAL) - dano);
            System.out.printf("%s causou %.2f de dano em %s\n",atacante.getEspecie().getNome(), dano, defensor.getEspecie().getNome());
        }
        if (getValor() == 2) {
            atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) + (dano * getPorcentagem()));
            System.out.printf("%s se curou em %.2f hp - (HP: %.2f | %.2f)\n", atacante.getEspecie().getNome(), dano * getPorcentagem(),
                    atacante.valorAtributo(Atributo.HPATUAL),atacante.valorAtributo(Atributo.HPMAX));
        } else {
            atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) + (atacante.valorAtributo(Atributo.HPMAX) * getPorcentagem()));
            System.out.printf("%s se curou em %.2f hp - (HP: %.2f | %.2f)\n", atacante.getEspecie().getNome(), atacante.valorAtributo(Atributo.HPMAX) * getPorcentagem(),
                    atacante.valorAtributo(Atributo.HPATUAL),atacante.valorAtributo(Atributo.HPMAX));
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
