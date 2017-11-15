package pokesim;

import java.util.List;

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

    Especie(int id) {
        int idReal = id - 1;
        String[][] pokemon = Batalha.getTabelaPokemon();
        setId(id);
        setNome(pokemon[idReal][1]);
        setBaseHp(Double.parseDouble(pokemon[idReal][4]));
        setBaseAtk(Double.parseDouble(pokemon[idReal][5]));
        setBaseDef(Double.parseDouble(pokemon[idReal][6]));
        setBaseSpe(Double.parseDouble(pokemon[idReal][7]));
        setBaseSpd(Double.parseDouble(pokemon[idReal][8]));
        if (!pokemon[idReal][2].isEmpty()) {
            for (Tipo tipo : Tipo.values())
                if (pokemon[idReal][2].compareTo(tipo.toString()) == 0) {
                    setTipo1(tipo);
                }
        } else {
            setTipo1(null);
        }
        if (!pokemon[idReal][3].isEmpty()) {
            for (Tipo tipo : Tipo.values())
                if (pokemon[idReal][3].compareTo(tipo.toString()) == 0) {
                    setTipo2(tipo);
                }
        } else {
            setTipo2(null);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setBaseHp(double baseHp) {
        this.baseHp = baseHp;
    }

    public void setBaseAtk(double baseAtk) {
        this.baseAtk = baseAtk;
    }

    public void setBaseDef(double baseDef) {
        this.baseDef = baseDef;
    }

    public void setBaseSpe(double baseSpe) {
        this.baseSpe = baseSpe;
    }

    public void setBaseSpd(double baseSpd) {
        this.baseSpd = baseSpd;
    }

    public void setTipo1(Tipo tipo1) {
        this.tipo1 = tipo1;
    }

    public void setTipo2(Tipo tipo2) {
        this.tipo2 = tipo2;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }


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
