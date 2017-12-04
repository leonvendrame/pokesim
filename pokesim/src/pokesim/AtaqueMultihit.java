package pokesim;

public class AtaqueMultihit extends Ataque {
    private int min;
    private int max;

    AtaqueMultihit(int id, Pokemon pokemon, String[] parametros) {
        super(id, pokemon);
        setMin(Integer.parseInt(parametros[0]));
        setMax(Integer.parseInt(parametros[1]));
    }

    @Override
    public void efeito() {
        super.efeito();
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
