package pokesim;

public class Especie {
    private int     id;
    private String  nome;
    private double  baseHp;
    private double  baseAtk;
    private double  baseDef;
    private double  baseSpe;
    private double  baseSpd;
    private Tipo    tipo1;
    private Tipo    tipo2;

    public double calcularAtributo(double x) {
        return x;
    }
}
