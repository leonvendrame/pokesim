package pokesim;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    Pokemon(int id, int level) {
        setLevel(level);
        this.especie = new Especie(id);
        setHpMax(this.especie.calcularAtributo(Atributo.HPMAX, level));
        setHpAtual(this.especie.calcularAtributo(Atributo.HPATUAL, level));
        setAtk(this.especie.calcularAtributo(Atributo.ATK, level));
        setDef(this.especie.calcularAtributo(Atributo.DEF, level));
        setSpe(this.especie.calcularAtributo(Atributo.SPE, level));
        setSpd(this.especie.calcularAtributo(Atributo.SPD, level));
        setModifierAccuracy(0);
        setModifierEvasion(0);
        setModifierAtk(0);
        setModifierDef(0);
        setModifierSpe(0);
        setModifierSpd(0);
        setConfusion(false);
        setFlinch(false);
        setStatus(Status.OK);
        this.ataques = new ArrayList<>();
    }

    public double valorAtributo(Atributo atributo) {
        switch (atributo) {
            case ATK:
                return ((this.atk) * (Math.max(2, 2 + modifierAtk) / Math.max(2, 2 - modifierAtk)));

            case DEF:
                return ((this.def) * (Math.max(2, 2 + modifierDef) / Math.max(2, 2 - modifierDef)));

            case SPE:
                return ((this.spe) * (Math.max(2, 2 + modifierSpe) / Math.max(2, 2 - modifierSpe)));

            case SPD:
                return ((this.spd) * (Math.max(2, 2 + modifierSpd) / Math.max(2, 2 - modifierSpd)));

            case HPMAX:
                return this.hpMax;

            case HPATUAL:
                return this.hpAtual;

            default:
                return 0;
        }
    }

    public Status getStatus() {
        return status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHpAtual(double hpAtual) {
        this.hpAtual = hpAtual;
    }

    public void setHpMax(double hpMax) {
        this.hpMax = hpMax;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public void setSpe(double spe) {
        this.spe = spe;
    }

    public void setSpd(double spd) {
        this.spd = spd;
    }

    public void setModifierAccuracy(int modifierAccuracy) {
        this.modifierAccuracy = modifierAccuracy;
    }

    public void setModifierEvasion(int modifierEvasion) {
        this.modifierEvasion = modifierEvasion;
    }

    public void setModifierAtk(int modifierAtk) {
        this.modifierAtk = modifierAtk;
    }

    public void setModifierDef(int modifierDef) {
        this.modifierDef = modifierDef;
    }

    public void setModifierSpe(int modifierSpe) {
        this.modifierSpe = modifierSpe;
    }

    public void setModifierSpd(int modifierSpd) {
        this.modifierSpd = modifierSpd;
    }

    public boolean isConfusion() {
        return confusion;
    }

    public void setConfusion(boolean confusion) {
        this.confusion = confusion;
    }

    public boolean isFlinch() {
        return flinch;
    }

    public void setFlinch(boolean flinch) {
        this.flinch = flinch;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Especie getEspecie() {
        return especie;
    }

    public List<Ataque> getAtaques() {
        return this.ataques;
    }

    public void novoAtaque(int id) {
        if (id > 0) {
            int idReal = id - 1;
            String[] parametros = null;
            String[][] tabAtaques = Batalha.getTabelaAtaque();
            String opcao = tabAtaques[idReal][6];
            Ataque novo = null;

            if (opcao.compareToIgnoreCase("hp") == 0) {
                parametros = tabAtaques[idReal][7].split(", |\\r");
                novo = new AtaqueHP(id);
            } else if (opcao.compareToIgnoreCase("comum") == 0) {
                novo = new Ataque(id);
            } else if (opcao.compareToIgnoreCase("multihit") == 0) {
                novo = new AtaqueMultihit(id);
            } else if (opcao.compareToIgnoreCase("modifier") == 0) {
                novo = new AtaqueModifier(id);
            } else if (opcao.compareToIgnoreCase("status") == 0) {
                novo = new AtaqueStatus(id);
            } else if (opcao.compareToIgnoreCase("fixo") == 0) {
                novo = new AtaqueFixo(id);
            } else if (opcao.compareToIgnoreCase("charge") == 0) {
                novo = new AtaqueCharge(id);
            }
            if (novo != null) {
                adicionarAtaque(novo);
            }
        }
        return;
    }

    private void adicionarAtaque(Ataque ataque) {
        if (this.getAtaques().size() < 4) {
            this.getAtaques().add(ataque);
        }
    }

    public void getInfoPokemon() {
        System.out.println(this.getEspecie().getNome());
        if (this.getEspecie().getTipo1() != null)
            System.out.println(this.getEspecie().getTipo1().toString());
        if (this.getEspecie().getTipo2() != null)
            System.out.println(this.getEspecie().getTipo2().toString());
        System.out.printf("LEVEL: \t\t%d\n", getLevel());
        System.out.printf("MAX HP: \t%.2f\n", this.valorAtributo(Atributo.HPMAX));
        System.out.printf("HP ATUAL: \t%.2f\n", this.valorAtributo(Atributo.HPATUAL));
        System.out.printf("ATK: \t\t%.2f\n", this.valorAtributo(Atributo.ATK));
        System.out.printf("DEF: \t\t%.2f\n", this.valorAtributo(Atributo.DEF));
        System.out.printf("SPE: \t\t%.2f\n", this.valorAtributo(Atributo.SPE));
        System.out.printf("SPD: \t\t%.2f\n\n", this.valorAtributo(Atributo.SPD));
    }
}
