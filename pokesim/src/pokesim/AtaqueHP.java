package pokesim;

public class AtaqueHP extends Ataque {
    private int valor;
    private double porcentagem;

    AtaqueHP(int id, Pokemon pokemon, String[] parametros) {
        super(id, pokemon);
        if (parametros[0].compareToIgnoreCase("max_hp") == 0) {
            setValor((int) pokemon.valorAtributo(Atributo.HPMAX));
        } else if (parametros[0].compareToIgnoreCase("dano") == 0) {
            setValor(-1);
        }
        setPorcentagem(Double.parseDouble(parametros[1]));
    }

    @Override
    public void efeito() {
        super.efeito();

        if (getValor() == -1) {
            //seta o dano
        } else {
            this.getPokemon().setHpAtual(this.getPokemon().valorAtributo(Atributo.HPATUAL) + this.valor);
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

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }
}
