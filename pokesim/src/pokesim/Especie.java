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

    public double calcularAtributo(Atributo atrib, int level) {
        switch (atrib) {
            case HPMAX:
                return ((2 * baseHp * level) / (110 + level));

            case HPATUAL:
                return ((2 * baseHp * level) / (110 + level));

            case ATK:
                return ((2 * baseAtk * level) / (105));

            case DEF:
                return ((2 * baseDef * level) / (105));

            case SPD:
                return ((2 * baseSpd * level) / (105));

            case SPE:
                return ((2 * baseSpe * level) / (105));

            default:
                return 0;
        }
    }
}
