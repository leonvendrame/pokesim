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
                return ((this.atk) * (Math.max(2, 2 + getModifierAtk()) / Math.max(2, 2 - getModifierAtk())));

            case DEF:
                return ((this.def) * (Math.max(2, 2 + getModifierDef()) / Math.max(2, 2 - getModifierDef())));

            case SPE:
                return ((this.spe) * (Math.max(2, 2 + getModifierSpe()) / Math.max(2, 2 - getModifierSpe())));

            case SPD:
                return ((this.spd) * (Math.max(2, 2 + getModifierSpd()) / Math.max(2, 2 - getModifierSpd())));

            case HPMAX:
                return this.hpMax;

            case HPATUAL:
                return this.hpAtual;

            default:
                return 0;
        }
    }

    public Status getStatus() {
        return this.status;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHpAtual(double hpAtual) {
        if (hpAtual <= 0) {
            this.hpAtual = 0;
            this.setStatus(Status.FAINTED);
        } else {
            if (hpAtual > valorAtributo(Atributo.HPMAX)) {
                setHpAtual(valorAtributo(Atributo.HPMAX));
            } else {
                this.hpAtual = hpAtual;
            }
        }
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
        if (modifierAccuracy < -6){
            this.modifierAccuracy = -6;
        } else if (modifierAccuracy > 6) {
            this.modifierAccuracy = 6;
        } else {
            this.modifierAccuracy = modifierAccuracy;
        }
    }

    public void setModifierEvasion(int modifierEvasion) {
        if (modifierEvasion < -6){
            this.modifierEvasion = -6;
        } else if (modifierEvasion > 6) {
            this.modifierEvasion = 6;
        } else {
            this.modifierEvasion = modifierEvasion;
        }
    }

    public void setModifierAtk(int modifierAtk) {
        if (modifierAtk < -6){
            this.modifierAtk = -6;
        } else if (modifierAtk > 6) {
            this.modifierAtk = 6;
        } else {
            this.modifierAtk = modifierAtk;
        }
    }

    public void setModifierDef(int modifierDef) {
        if (modifierDef < -6){
            this.modifierDef = -6;
        } else if (modifierDef > 6) {
            this.modifierDef = 6;
        } else {
            this.modifierDef = modifierDef;
        }
    }

    public void setModifierSpe(int modifierSpe) {
        if (modifierSpe < -6){
            this.modifierSpe = -6;
        } else if (modifierSpe > 6) {
            this.modifierSpe = 6;
        } else {
            this.modifierSpe = modifierSpe;
        }
    }

    public void setModifierSpd(int modifierSpd) {
        if (modifierSpd < -6){
            this.modifierSpd = -6;
        } else if (modifierSpd > 6) {
            this.modifierSpd = 6;
        } else {
            this.modifierSpd = modifierSpd;
        }
    }

    public boolean isConfusion() {
        return this.confusion;
    }

    public void setConfusion(boolean confusion) {
        this.confusion = confusion;
    }

    public boolean isFlinch() {
        return this.flinch;
    }

    public void setFlinch(boolean flinch) {
        this.flinch = flinch;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Especie getEspecie() {
        return this.especie;
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
            parametros = tabAtaques[idReal][7].split(", |\\r");

            if (opcao.compareToIgnoreCase("comum") == 0) {
                novo = new Ataque(id);
            } else if (opcao.compareToIgnoreCase("hp") == 0) {
                novo = new AtaqueHP(id, parametros);
            } else if (opcao.compareToIgnoreCase("multihit") == 0) {
                novo = new AtaqueMultihit(id, parametros);
            } else if (opcao.compareToIgnoreCase("modifier") == 0) {
                novo = new AtaqueModifier(id, parametros);
            } else if (opcao.compareToIgnoreCase("status") == 0) {
                novo = new AtaqueStatus(id,parametros);
            } else if (opcao.compareToIgnoreCase("fixo") == 0) {
                novo = new AtaqueFixo(id, parametros);
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
        return;
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

    public int getModifierAccuracy() {
        return this.modifierAccuracy;
    }

    public int getModifierEvasion() {
        return this.modifierEvasion;
    }

    public int getModifierAtk() {
        return this.modifierAtk;
    }

    public int getModifierDef() {
        return this.modifierDef;
    }

    public int getModifierSpe() {
        return this.modifierSpe;
    }

    public int getModifierSpd() {
        return this.modifierSpd;
    }
}
