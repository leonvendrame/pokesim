package pokesim;

public class AtaqueHP extends Ataque {
    private int valor;
    private int porcentagem;

    AtaqueHP(int id, String[] parametros) {
        super(id);
        if (parametros[0].compareToIgnoreCase("max_hp") == 0) {
            setValor(-2);
        } else if (parametros[0].compareToIgnoreCase("dano") == 0) {
            setValor(-1);
        }
        setPorcentagem((int) (Double.parseDouble(parametros[1]) * 100));
    }

    @Override
    public void efeito(Pokemon atacante, Pokemon defensor) {

        if (getValor() == -1) {
            atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) + this.valor);
        } else {
            atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) + this.valor);
        }
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

    public void setPorcentagem(int porcentagem) {
        this.porcentagem = porcentagem;
    }
}
