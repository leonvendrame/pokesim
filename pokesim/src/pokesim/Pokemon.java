package pokesim;

import java.util.List;

public class Pokemon {
    private int     level;
    private double  hpAtual;
    private double  hpMax;
    private double  atk;
    private double  def;
    private double  spe;
    private double  spd;
    private int     modifierAccuracy;
    private int     modifierEvasion;
    private int     modifierAtk;
    private int     modifierDef;
    private int     modifierSpe;
    private int     modifierSpd;
    private boolean confusion;
    private boolean flinch;
    private Status  status;
    private Especie especie;
    private List<Ataque> ataques;

    public double valorAtributo(double x) {
        return x;
    }

}
