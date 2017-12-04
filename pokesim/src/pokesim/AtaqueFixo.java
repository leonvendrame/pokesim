package pokesim;

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

    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
