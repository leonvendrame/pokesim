package pokesim;

public class AtaqueHP extends Ataque {
    public int valor;
    public int porcentagem;

    AtaqueHP(int id, String[] parametros) {
        super(id);
    }

    public int getValor() {
        return valor;
    }

    public int getPorcentagem() {
        return porcentagem;
    }

    @Override
    public void efeito() {
        super.efeito();
    }
}
