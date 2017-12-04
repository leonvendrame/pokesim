package pokesim;

public class AtaqueFixo extends Ataque {
    private int val;

    AtaqueFixo(int id, Pokemon pokemon, String[] parametros) {
        super(id, pokemon);
        if (parametros[0].compareToIgnoreCase("lvl") == 0) {
            setVal(this.getPokemon().getLevel());
        } else {
            setVal(Integer.parseInt(parametros[0]));
        }
    }

    @Override
    public void efeito() {
        super.efeito();
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
