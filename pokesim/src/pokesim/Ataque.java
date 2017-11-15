package pokesim;

public class Ataque {
    private int     id;
    private String  nome;
    private double  ppMax;
    private double  ppAtual;
    private double  power;
    private double  accuracy;
    private Tipo    tipo;

    public void efeito() {
        return;
    }

    public boolean calculoCritico() {
        return false;
    }

    public boolean calculoAcerto() {
        return false;
    }

    public double calculoDano(double x) {
        return x;
    }
}
